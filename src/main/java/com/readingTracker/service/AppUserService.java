package com.readingTracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.web.dto.AppUserRegistrationDto;

public interface AppUserService extends UserDetailsService {
	AppUser saveUser(AppUser user);

	UserDetails loadUserByUsername(String username);

	AppUser getUser(Long id);

	List<AppUser> getAllUsers();

	AppUser updateUser(AppUser appUser);

	void delete(Long id);

	AppUser registerUser(AppUserRegistrationDto appUserDto);

	Optional<AppUser> findByUsername(String username);

}
