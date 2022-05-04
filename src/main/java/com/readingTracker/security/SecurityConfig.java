package com.readingTracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.readingTracker.security.filters.CustomAuthenticationFilter;
import com.readingTracker.security.filters.CustomAuthorizationFilter;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), jwtUtil);
		customAuthenticationFilter.setFilterProcessesUrl("/login");
		http.csrf().disable();
		// set endpoints
		http.authorizeRequests().antMatchers(SecurityConstants.PUBLIC_URLS).permitAll();
		http.authorizeRequests().antMatchers(SecurityConstants.USER_URLS).hasAnyAuthority(Authorities.USER_AUTHORITIES);
		http.authorizeRequests().antMatchers(SecurityConstants.MANAGER_URLS).hasAnyAuthority(Authorities.MANAGER_AUTHORITIES);
		http.authorizeRequests().anyRequest().authenticated()
		// set login and logout
		.and().formLogin().defaultSuccessUrl("/home", true)
		.and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID");
		// add filters
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
