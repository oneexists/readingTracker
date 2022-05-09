package com.readingTracker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Author findByName(String name);
}
