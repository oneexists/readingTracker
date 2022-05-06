package com.readingTracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.readingTracker.security.filters.CustomAuthenticationFilter;
import com.readingTracker.security.filters.CustomAuthorizationFilter;
import com.readingTracker.service.AppUserService;

@EnableWebSecurity
public class SecurityConfig {
	private final AppUserService appUserService;
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public SecurityConfig(AppUserService appUserService, UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
		this.appUserService = appUserService;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Configuration
	@Order(1)
	public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
					authenticationManagerBean(), jwtUtil);
			customAuthenticationFilter.setFilterProcessesUrl("/api/login");
			http.csrf().disable();

			// add filters
			http.addFilter(customAuthenticationFilter);
			http.addFilterBefore(new CustomAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
					.antMatcher("/api/**").authorizeRequests().anyRequest()
					.hasAnyAuthority(Authorities.USER_AUTHORITIES);
		}
	}

	@Configuration
	public class FormLoginConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/user/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
					.failureUrl("/user/login?error").and().logout().invalidateHttpSession(true)
					.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/user/login?logout").and().authorizeRequests()
					.antMatchers(SecurityConstants.PUBLIC_URLS).permitAll().and().authorizeRequests().anyRequest()
					.authenticated();

		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(appUserService).passwordEncoder(bCryptPasswordEncoder);
		}
	}

}
