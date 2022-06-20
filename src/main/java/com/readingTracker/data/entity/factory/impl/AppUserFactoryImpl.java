package com.readingTracker.data.entity.factory.impl;

import java.time.LocalDate;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Role;
import com.readingTracker.data.entity.factory.AppUserFactory;

public class AppUserFactoryImpl implements AppUserFactory {
	@Override
	public AppUser create(String name, String username, String password, LocalDate dateOfBirth, Role userRoles) {
		return new AppUser(name, username, password, dateOfBirth, userRoles);
	}
}
