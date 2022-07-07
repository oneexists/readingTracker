package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Author;

/**
 * @author skylar
 *
 */
public interface AuthorService {
	/**
	 * Find author by id
	 * 
	 * @param id the id of the author
	 * @return author the author with the same id
	 */
	Author findById(Long id);

	/**
	 * Find all authors
	 * 
	 * @return authors the list of authors
	 */
	List<Author> getAllAuthors();

	/**
	 * Update an author
	 * 
	 * @param author the new author
	 * @return author the updated author
	 */
	Author updateAuthor(Author author);

	/**
	 * Delete the author by id
	 * 
	 * @param id the id of the author
	 */
	void deleteAuthor(Long id);

	/**
	 * Save a new author
	 * 
	 * @param author the author to save
	 * @return author the saved author
	 */
	Author saveAuthor(Author author);

	/**
	 * Find author by name
	 * 
	 * @param name the name of the author
	 * @return author the author with the same name
	 */
	Author findByName(String name);
}
