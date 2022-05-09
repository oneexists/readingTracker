package com.readingTracker.web.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Book;
import com.readingTracker.service.AuthorService;
import com.readingTracker.web.dto.BookDTO;

@Component
public class BookConverter {
	private final AuthorService authorService;

	@Autowired
	public BookConverter(AuthorService authorService) {
		this.authorService = authorService;
	}

	public BookDTO bookToClient(Book book) {
		return new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getLanguage(),
				book.getPages());
	}

	public Book clientToBook(BookDTO bookDTO) {
		return new Book(bookDTO.getId(), bookDTO.getTitle(), authorService.findByName(bookDTO.getAuthor()),
				bookDTO.getLanguage(), bookDTO.getPages());
	}
}