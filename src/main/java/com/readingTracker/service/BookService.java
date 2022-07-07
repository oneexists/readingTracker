package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Book;

/**
 * @author skylar
 *
 */
public interface BookService {
	/**
	 * Save a new book
	 * 
	 * @param book the book to save
	 * @return book the saved book
	 */
	Book saveBook(Book book);

	/**
	 * Find book by id
	 * 
	 * @param id the id of the book
	 * @return book the book with the same id
	 */
	Book findById(Long id);

	/**
	 * Find all books
	 * 
	 * @return books the list of books
	 */
	List<Book> getAllBooks();

	/**
	 * Update a book
	 * 
	 * @param book the new book
	 * @return book the updated book
	 */
	Book updateBook(Book book);

	/**
	 * Delete book by id
	 * 
	 * @param id the id of the book
	 */
	void deleteBook(Long id);

}
