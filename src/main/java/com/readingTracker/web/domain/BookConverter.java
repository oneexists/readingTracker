package com.readingTracker.web.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Book;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.AuthorService;
import com.readingTracker.web.dto.BookDTO;

@Component
public class BookConverter {
	private final AuthorService authorService;
	private final AppUserService appUserService;

	@Autowired
	public BookConverter(AuthorService authorService, AppUserService appUserService) {
		this.authorService = authorService;
		this.appUserService = appUserService;
	}

	public BookDTO bookToClient(Book book) {
		return new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getLanguage(),
				book.getPages(), book.getUser().getUsername());
	}

	public Book clientToBook(BookDTO bookDTO) {
		return new Book(bookDTO.getId(), bookDTO.getTitle(), authorService.findByName(bookDTO.getAuthor()),
				bookDTO.getLanguage(), bookDTO.getPages(), appUserService.findByUsername(bookDTO.getUsername()).get());
	}
}