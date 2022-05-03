package com.readingTracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import static java.util.Arrays.stream;

import java.util.List;

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
import com.readingTracker.service.exceptions.AppUserNotFoundException;

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
	
	@Override
	public AppUser getUser(Long id) {
		return appUserRepository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
	}

	@Override
	public List<AppUser> getAllUsers() {
		return appUserRepository.findAll();
	}

	@Override
	public AppUser updateUser(AppUser appUser) {
		return appUserRepository.findById(appUser.getUserId())
				.map(user -> {
					user.setName(appUser.getName());
					user.setUsername(appUser.getUsername());
					user.setPassword(appUser.getPassword());
					user.setDateOfBirth(appUser.getDateOfBirth());
					return appUserRepository.saveAndFlush(user);
				})
				.orElseGet(() -> {
					appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
					return appUserRepository.save(appUser);
				});
	}

	@Override
	public void delete(Long id) {
		appUserRepository.deleteById(id);
	}
}
