package com.readingTracker.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
	List<Log> findByBook(Book book);

	List<Log> findByUser(AppUser user);
}
