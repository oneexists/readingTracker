package com.readingTracker.data.entity.factory;

import java.time.LocalDate;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Role;

public interface AppUserFactory {
	AppUser create(String name, String username, String password, LocalDate dateOfBirth, Role userRoles);
}
