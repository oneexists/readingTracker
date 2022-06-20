package com.readingTracker.service.impl;

import static java.util.Arrays.stream;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Role;
import com.readingTracker.data.entity.factory.AppUserProvider;
import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.exceptions.AppUserNotFoundException;
import com.readingTracker.web.dto.AppUserRegistrationDto;

@Service
@Transactional
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
		AppUser appUser = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		String[] claims = appUser.getUserRole().getAuthorities();
		Set<SimpleGrantedAuthority> authorities = stream(claims).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}

	@Override
	public Optional<AppUser> findByUsername(String username) {
		return appUserRepository.findByUsername(username);
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
		return appUserRepository.findById(appUser.getUserId()).map(user -> {
			user.setName(appUser.getName());
			user.setUsername(appUser.getUsername());
			user.setPassword(appUser.getPassword());
			user.setDateOfBirth(appUser.getDateOfBirth());
			return appUserRepository.saveAndFlush(user);
		}).orElseGet(() -> {
			appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
			return appUserRepository.save(appUser);
		});
	}

	@Override
	public void delete(Long id) {
		appUserRepository.deleteById(id);
	}

	@Override
	public AppUser registerUser(AppUserRegistrationDto appUserDto) {
		return appUserRepository.save(AppUserProvider.getFactory().create(appUserDto.getName(),
				appUserDto.getUsername(), passwordEncoder.encode(appUserDto.getPassword()),
				LocalDate.parse(appUserDto.getDateOfBirth()), Role.ROLE_USER));
	}
}
