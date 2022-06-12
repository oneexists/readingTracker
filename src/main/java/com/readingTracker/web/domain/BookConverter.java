package com.readingTracker.web.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.factory.BookProvider;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.AuthorService;
import com.readingTracker.web.dto.BookDto;

@Component
public class BookConverter {
	private final AuthorService authorService;
	private final AppUserService appUserService;

	@Autowired
	public BookConverter(AuthorService authorService, AppUserService appUserService) {
		this.authorService = authorService;
		this.appUserService = appUserService;
	}

	public BookDto bookToClient(Book book) {
		return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getLanguage(),
				book.getPages(), book.getUser().getUsername());
	}

	public Book clientToBook(BookDto bookDTO) {
		return BookProvider.getFactory().create(bookDTO.getId(), bookDTO.getTitle(),
				authorService.findByName(bookDTO.getAuthor()), bookDTO.getLanguage(), bookDTO.getPages(),
				appUserService.findByUsername(bookDTO.getUsername()).get());
	}

}