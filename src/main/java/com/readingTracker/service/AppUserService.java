package com.readingTracker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.readingTracker.data.entity.AppUser;

public interface AppUserService extends UserDetailsService {
	AppUser saveUser(AppUser user);
	UserDetails loadUserByUsername(String username);

}
