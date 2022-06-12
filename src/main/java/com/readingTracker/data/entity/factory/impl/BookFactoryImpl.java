package com.readingTracker.data.entity.factory.impl;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.factory.BookFactory;

public class BookFactoryImpl implements BookFactory {

	@Override
	public Book create(Long bookId, String title, Author author, String language, int pages, AppUser user) {
		return new Book(bookId, title, author, language, pages, user);
	}

}
