package com.Booking.Booking.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Booking.Booking.contant.AppRole;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((authorize) -> {
			authorize.requestMatchers("/account/login","/room/file/**").permitAll();
//			authorize.requestMatchers("/admin/history/Statistical/excel/**").permitAll();

			authorize.requestMatchers("/events/list","/roomtypes/list","/notifications/list","/notifications/{id}").hasAnyAuthority(AppRole.ADMIN, AppRole.TEACHER);
			authorize.requestMatchers("/events/**", "/roomtypes/**", "/notifications/**", "/users/**").hasAnyAuthority(AppRole.ADMIN);

//			authorize.requestMatchers("/detail/getdetail/**", "detail/acceptbookingroom/**",
//					"detail/refusebookingroom/**", "detail/checkresetroom/**",
//					"/room/addnewroom", "room/editroom/**",
//					"room/deleteroom/**").hasAnyAuthority(AppRole.ADMIN);
//			authorize.requestMatchers("detail/calendarbooking/**").hasAnyAuthority(AppRole.TEACHER);
//            authorize.requestMatchers("/detail/searchdetail", "detail/bookingroom", "detail/cancelbookingroom/**"
//            		, "/detail/getalldetail", "/detail/updatebookingroomwhenwaitingaccept"
//            		, "/room/getroom/**", "room/searchroom",
//            		"/room/getallroom").hasAnyAuthority(AppRole.ADMIN, AppRole.TEACHER);


			authorize.requestMatchers("/admin/BookingHistory/**").hasAnyAuthority(AppRole.ADMIN);
			authorize.requestMatchers("/teacher/BookingHistory/**").hasAnyAuthority(AppRole.TEACHER);
			authorize.requestMatchers("/admin/history/Statistical/**").hasAnyAuthority(AppRole.ADMIN);

			authorize.requestMatchers("/room/admin/**", "/detail/admin/**").hasAuthority(AppRole.ADMIN);
			authorize.requestMatchers("/detail/teacher/**", "/room/teacher/**").hasAuthority(AppRole.TEACHER);
			authorize.requestMatchers("/room/both/**", "/detail/both/**").hasAnyAuthority(AppRole.ADMIN, AppRole.TEACHER);


					authorize.anyRequest().authenticated();
		}).authenticationProvider(authenticationProvider())
//				.exceptionHandling(
//						exception -> exception.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(
//								authenticationEntryPoint)
//						)
				.csrf(csrf -> csrf.disable()).cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Cache-Control", "Origin"));
		configuration.setAllowCredentials(false);
		configuration.setMaxAge(31536000L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return authenticationProvider;
	}
}

/*
 * - Chú thích @Configuration chỉ ra rằng lớp này xác định cấu hình cho ngữ cảnh
 * ứng dụng Spring.
 * 
 * - Chú thích @AllArgsConstructor lấy từ thư viện Lombok và nó tạo ra một hàm
 * tạo với tất cả các trường được chú thích bằng @NonNull.
 * 
 * - Phương thức passEncode() là một Bean tạo ra một phiên bản
 * BCryptPasswordEncoding để mã hóa mật khẩu.
 * 
 * - Phương thức securityFilterChain() là một Bean xác định chuỗi bộ lọc bảo
 * mật. Tham số HttpSecurity được sử dụng để định cấu hình cài đặt bảo mật cho
 * ứng dụng. Trong trường hợp này, phương thức này sẽ vô hiệu hóa tính năng bảo
 * vệ CSRF và cho phép các yêu cầu dựa trên phương thức HTTP và URL của chúng.
 * 
 * Phương thức AuthenticationManager() là một Bean cung cấp
 * AuthenticationManager. Nó truy xuất trình quản lý xác thực từ phiên bản
 * AuthenticationConfiguration.
 */
