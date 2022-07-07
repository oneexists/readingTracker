package com.readingTracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.web.dto.AppUserRegistrationDto;

/**
 * @author skylar
 *
 */
public interface AppUserService extends UserDetailsService {

	UserDetails loadUserByUsername(String username);

	/**
	 * Find app user by id
	 * 
	 * @param id the id of the app user
	 * @return appUser the app user with the same id
	 */
	AppUser getUser(Long id);

	/**
	 * Find all app users
	 * 
	 * @return appUsers the list of app users
	 */
	List<AppUser> getAllUsers();

	/**
	 * Update an app user
	 * 
	 * @param appUser the new app user
	 * @return appUser the updated app user
	 */
	AppUser updateUser(AppUser appUser);

	/**
	 * Delete app user by id
	 * 
	 * @param id the id of the app user
	 */
	void delete(Long id);

	/**
	 * Register a new app user
	 * 
	 * @param appUserDto the app user to register
	 * @return appUser the registered app user
	 */
	AppUser registerUser(AppUserRegistrationDto appUserDto);

	/**
	 * Find app user by username
	 * 
	 * @param username the username to search
	 * @return appUser the app user using Optional container
	 */
	Optional<AppUser> findByUsername(String username);

}
