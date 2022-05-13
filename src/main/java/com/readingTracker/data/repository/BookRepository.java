package com.readingTracker.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByUser(AppUser user);

}
