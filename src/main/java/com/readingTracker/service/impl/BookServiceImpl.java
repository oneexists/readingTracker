package com.readingTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.repository.BookRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.exceptions.AppUserNotFoundException;
import com.readingTracker.service.exceptions.BookNotFoundException;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	private final BookRepository repository;
	private final AppUserService appUserService;

	@Autowired
	public BookServiceImpl(BookRepository repository, AppUserService appUserService) {
		super();
		this.repository = repository;
		this.appUserService = appUserService;
	}

	@Override
	public Book saveBook(Book book) {
		return repository.save(book);
	}

	@Override
	public Book findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	@Override
	public List<Book> findByUsername(String username) {
		AppUser user = appUserService.findByUsername(username)
				.orElseThrow(() -> new AppUserNotFoundException(username));
		return repository.findByUser(user);
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public Book updateBook(Book book) {
		return repository.findById(book.getId()).map(updateBook -> {
			updateBook.setTitle(book.getTitle());
			updateBook.setAuthor(book.getAuthor());
			updateBook.setLanguage(book.getLanguage());
			updateBook.setPages(book.getPages());
			updateBook.setLogs(book.getLogs());
			updateBook.setUser(book.getUser());
			return repository.saveAndFlush(updateBook);
		}).orElseGet(() -> repository.save(book));
	}

	@Override
	public void deleteBook(Long id) {
		repository.deleteById(id);
	}

}
