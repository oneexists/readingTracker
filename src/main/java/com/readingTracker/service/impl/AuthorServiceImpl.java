package com.readingTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingTracker.data.entity.Author;
import com.readingTracker.data.repository.AuthorRepository;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.exceptions.AuthorNotFoundException;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
	private final AuthorRepository repository;

	@Autowired
	public AuthorServiceImpl(AuthorRepository repository) {
		this.repository = repository;
	}

	@Override
	public Author saveAuthor(Author author) {
		return repository.save(author);
	}

	@Override
	public Author findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
	}

	@Override
	public List<Author> getAllAuthors() {
		return repository.findAll();
	}

	@Override
	public Author findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Author updateAuthor(Author author) {
		return repository.findById(author.getId()).map(updateAuthor -> {
			updateAuthor.setName(author.getName());
			updateAuthor.setBooks(author.getBooks());
			return repository.saveAndFlush(updateAuthor);
		}).orElseGet(() -> {
			return repository.save(author);
		});
	}

	@Override
	public void deleteAuthor(Long id) {
		repository.deleteById(id);
	}

}
