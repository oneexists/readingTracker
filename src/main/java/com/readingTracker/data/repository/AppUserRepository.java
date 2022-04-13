package com.readingTracker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	AppUser findByUsername(String username);
	boolean existsByUsername(String username);
}
