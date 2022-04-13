package com.readingTracker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
