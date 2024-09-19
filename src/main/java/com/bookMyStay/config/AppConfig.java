package com.bookMyStay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable()) // Disable CSRF
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, "/bookMyStay/customers/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/admins/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/hotels/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/customers/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/admins/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/hotels/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/bookMyStay/rooms/add").hasRole("HOTEL")
						.requestMatchers(HttpMethod.PUT, "/bookMyStay/customers/update").hasRole("CUSTOMER")
						.requestMatchers(HttpMethod.GET, "/bookMyStay/customers/getToBeDeletedAccounts").hasRole("ADMIN")
						.anyRequest().authenticated() // Other requests must be authenticated
				)
				.addFilterBefore(new JwtTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtTokenGeneratorFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
