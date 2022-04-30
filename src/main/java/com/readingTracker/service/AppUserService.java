package com.readingTracker.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.readingTracker.data.entity.AppUser;

public interface AppUserService extends UserDetailsService {
	AppUser saveUser(AppUser user);
	UserDetails loadUserByUsername(String username);
	AppUser getUser(Long id);
	List<AppUser> getAllUsers();
	AppUser updateUser(AppUser appUser);
	void delete(Long id);

}
