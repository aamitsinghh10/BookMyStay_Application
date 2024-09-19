package com.bookMyStay.config;

import java.io.IOException;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Retrieve the JWT token from the Authorization header
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

		// If a token is present and starts with "Bearer ", process it
		if (jwt != null && jwt.startsWith("Bearer ")) {
			try {
				// Extract the actual token by removing the "Bearer " part
				jwt = jwt.substring(7);

				// Create the signing key using the JWT secret
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

				// Parse the JWT token and extract claims
				Claims claims = Jwts.parser()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();

				// Extract username and authorities from the token claims
				String username = claims.get("username", String.class);
				String authorities = claims.get("authorities", String.class);

				// Create an authentication object using the parsed username and authorities
				Authentication auth = new UsernamePasswordAuthenticationToken(
						username,
						null,
						AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
				);

				// Set the authentication in the SecurityContext
				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				// Handle exceptions such as token parsing issues or invalid tokens
				throw new BadCredentialsException("Invalid Token received.");
			}
		}

		// Continue the filter chain
		filterChain.doFilter(request, response);
	}

	// Exclude specific login APIs from JWT token validation
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		return path.equals("/bookMyStay/admins/login") ||
				path.equals("/bookMyStay/customers/login") ||
				path.equals("/bookMyStay/hotels/login");
	}
}
