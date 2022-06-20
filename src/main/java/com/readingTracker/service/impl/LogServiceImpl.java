package com.readingTracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.repository.LogRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.service.exceptions.AppUserNotFoundException;
import com.readingTracker.service.exceptions.LogNotFoundException;

@Service
@Transactional
public class LogServiceImpl implements LogService {
	private final LogRepository repository;
	private final AppUserService appUserService;
	private final BookService bookService;

	@Autowired
	public LogServiceImpl(LogRepository repository, AppUserService appUserService, BookService bookService) {
		this.repository = repository;
		this.appUserService = appUserService;
		this.bookService = bookService;
	}

	@Override
	public Log saveLog(Log log) {
		return repository.save(log);
	}

	@Override
	public Log findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new LogNotFoundException(id));
	}

	@Override
	public List<Log> findByUsername(String username) {
		AppUser user = appUserService.findByUsername(username)
				.orElseThrow(() -> new AppUserNotFoundException(username));
		List<Book> books = bookService.findByUsername(user.getUsername());
		List<Log> logs = new ArrayList<>();
		books.forEach(book -> logs.addAll(book.getLogs()));
		return logs;
	}

	@Override
	public List<Log> getAllLogs() {
		return repository.findAll();
	}

	@Override
	public Log updateLog(Log log) {
		return repository.findById(log.getId()).map(updateLog -> {
			updateLog.setBook(log.getBook());
			updateLog.setStart(log.getStart());
			updateLog.setFinish(log.getFinish());
			return repository.saveAndFlush(updateLog);
		}).orElseGet(() -> repository.save(log));
	}

	@Override
	public void deleteLog(Long id) {
		repository.deleteById(id);
	}

}
