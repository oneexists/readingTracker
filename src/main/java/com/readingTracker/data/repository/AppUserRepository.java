package com.readingTracker.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);

	boolean existsByUsername(String username);
}
