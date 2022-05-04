package com.readingTracker.security.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingTracker.security.JwtUtil;
import com.readingTracker.security.SecurityConstants;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	
	@Autowired
	public CustomAuthorizationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// filter public endpoints
		if (Arrays.asList(SecurityConstants.PUBLIC_URLS).contains(request.getServletPath())) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			// verify authorization header
			if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				// verify token and client
				String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
				String username = jwtUtil.getSubject(token);
				try {
					String[] claims = jwtUtil.getClaims(token);
					Collection<SimpleGrantedAuthority> authorities = jwtUtil.getAuthorities(claims);
					SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(username, null, authorities));
					// forward
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			} else {
				filterChain.doFilter(request, response);
				return;
			}

		}
		
	}

}
