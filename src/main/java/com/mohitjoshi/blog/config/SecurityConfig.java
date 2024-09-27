package com.mohitjoshi.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mohitjoshi.blog.security.CustomUserDetailService;
import com.mohitjoshi.blog.security.JwtAuthenticationEntryPoint;
import com.mohitjoshi.blog.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public static final String[] PUBLIC_URLS = {
			"/v3/api-docs","/api/auth/**","/v2/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**","/api/contactUs/"
	};
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf((csrf) -> csrf.disable())
		.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests.requestMatchers(PUBLIC_URLS).permitAll().requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())
		.exceptionHandling((exceptionHandling) ->
			exceptionHandling.accessDeniedPage("/errors/access-denied").authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.sessionManagement((sessionManagement) ->
			sessionManagement.sessionConcurrency((sessionConcurrency) ->
					sessionConcurrency.maximumSessions(1).expiredUrl("/login?expired")
				).sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authenticationProvider(daoAuthenticationProvider());
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	 @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
         return authenticationConfiguration.getAuthenticationManager();
     }
	
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		 
		 provider.setUserDetailsService(customUserDetailService);
		 provider.setPasswordEncoder(encoder());
		 return provider;
	 }
	 
	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public FilterRegistrationBean coresFilter() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.setMaxAge(3600L);
		
		
		
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-110);
		return bean;
	}
}
