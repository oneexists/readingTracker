package com.readingTracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import static java.util.Arrays.stream;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.service.AppUserService;

@Service @Transactional
public class AppUserServiceImpl implements AppUserService {
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return appUserRepository.save(user);			
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByUsername(username);
		if (appUser == null) {
			throw new UsernameNotFoundException("User not found in database.");
		}
		String[] claims = appUser.getUserRole().getAuthorities();
		Set<SimpleGrantedAuthority> authorities = stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}
}
