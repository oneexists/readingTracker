package com.readingTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.repository.BookRepository;
import com.readingTracker.service.BookService;

@Service @Transactional
public class BookServiceImpl implements BookService {
	private final BookRepository repository;
	
	@Autowired
	public BookServiceImpl(BookRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Book saveBook(Book book) {
		return repository.save(book);
	}

	@Override
	public Book findById(Long id) {
		return repository.getById(id);
	}

	@Override
	public List<Book> allBooks() {
		return repository.findAll();
	}

}
