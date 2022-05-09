package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Author;

public interface AuthorService {
	Author findById(Long id);

	List<Author> getAllAuthors();

	Author updateAuthor(Author author);

	void deleteAuthor(Long id);

	Author saveAuthor(Author author);

	Author findByName(String name);
}
