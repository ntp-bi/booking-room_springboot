package com.Booking.Booking.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Booking.Booking.util.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	private UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		// nếu endpoint không được set permitAll và Authorization không được gửi lên thì
		// trả về lỗi 401
//		if (authHeader == null && !isPermitAllRequest(request)) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.getWriter().write("Who are you?");
//			return;
//		}

		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtTokenProvider.getUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtTokenProvider.validateToken(token)) {
				// nếu token hợp lệ thì set thông tin user cho Security Context
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

	private boolean isPermitAllRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/account/login");
	}
}
/*
 * Dưới đây là bảng phân tích các phần chính của đoạn mã trên: - Lớp này mở rộng
 * OncePerRequestFilter của Spring framework, đảm bảo rằng bộ lọc chỉ được áp
 * dụng một lần cho mỗi yêu cầu.
 * 
 * - Hàm tạo có hai phần phụ thuộc: JwtTokenProvider và UserDetailsService, được
 * đưa vào thông qua cơ chế chèn phụ thuộc hàm tạo của Spring.
 * 
 * - Phương thức doFilterInternal là logic chính của bộ lọc. Nó trích xuất mã
 * thông báo JWT từ tiêu đề Ủy quyền bằng phương thức getTokenFromRequest, xác
 * thực mã thông báo bằng lớp JwtTokenProvider và đặt thông tin xác thực trong
 * SecurityContextHolder.
 * 
 * - Phương thức getTokenFromRequest phân tích tiêu đề Ủy quyền và trả về phần
 * mã thông báo.
 * 
 * - SecurityContextHolder được sử dụng để lưu trữ thông tin xác thực cho yêu
 * cầu hiện tại. Trong trường hợp này, bộ lọc sẽ đặt
 * UsernamePasswordAuthenticationToken với UserDetails và quyền được liên kết
 * với mã thông báo.
 */