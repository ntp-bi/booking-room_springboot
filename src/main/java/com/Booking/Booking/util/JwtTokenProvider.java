package com.Booking.Booking.util;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	/*
	 * Phương thức generateToken(Xác thực xác thực) tạo ra một JWT mới dựa trên đối
	 * tượng Xác thực được cung cấp, chứa thông tin về người dùng đang được xác
	 * thực. Nó sử dụng phương thức Jwts.builder() để tạo một đối tượng JwtBuilder
	 * mới, đặt chủ đề (tức là tên người dùng) của JWT, ngày phát hành và ngày hết
	 * hạn, đồng thời ký tên vào JWT bằng phương thức key(). Cuối cùng, nó trả về
	 * JWT dưới dạng một chuỗi.
	 */

	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		// Get roles of authenticated user
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

//		String token = Jwts.builder().setSubject(username).claim("roles", roles).setIssuedAt(new Date())
//				.setExpiration(expireDate).signWith(key()).compact();
		String token = Jwts.builder().subject(username).claim("roles", roles).issuedAt(new Date())
				.expiration(expireDate).signWith(key()).compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	/*
	 * getUsername(String token) trích xuất tên người dùng từ JWT được cung cấp. Nó
	 * sử dụng phương thức Jwts.parserBuilder() để tạo một đối tượng
	 * JwtParserBuilder mới, đặt khóa ký bằng phương thức key() và phân tích cú pháp
	 * JWT bằng phương thức ParseClaimsJws(). Sau đó, nó lấy chủ đề (tức là tên
	 * người dùng) từ đối tượng Claims của JWT và trả về dưới dạng một chuỗi.
	 */

	// get username from Jwt token
//    public String getUsername(String token){
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        String username = claims.getSubject();
//        return username;
//    } 0.11.5

	public String getUsername(String token) {

		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	// get roles from Jwt token
//	public List<String> getRoles(String token) {
//		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
//		List<String> roles = (List<String>) claims.get("roles");
//		return roles;
//	}

	public List<String> getRoles(String token) {
		Claims claims = Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload();
		List<String> roles = (List<String>) claims.get("roles");
		return roles;
	}

	/*
	 * Phương thức validToken(String token) xác thực JWT được cung cấp. Nó sử dụng
	 * phương thức Jwts.parserBuilder() để tạo một đối tượng JwtParserBuilder mới,
	 * đặt khóa ký bằng phương thức key() và phân tích JWT bằng phương thức
	 * parser(). Nếu JWT hợp lệ, phương thức trả về true. Nếu JWT không hợp lệ hoặc
	 * đã hết hạn, phương thức sẽ ghi lại thông báo lỗi bằng cách sử dụng đối tượng
	 * logger và trả về sai.
	 */

	// validate Jwt token
	public boolean validateToken(String token) {
		try {
//            Jwts.parserBuilder()
//                    .setSigningKey(key())
//                    .build()
//                    .parse(token);
//            return true;

			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
