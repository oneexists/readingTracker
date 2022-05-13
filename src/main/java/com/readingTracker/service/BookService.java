package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Book;

public interface BookService {
	Book saveBook(Book book);

	Book findById(Long id);

	List<Book> getAllBooks();

	Book updateBook(Book book);

	void deleteBook(Long id);

	List<Book> findByUsername(String username);
}
