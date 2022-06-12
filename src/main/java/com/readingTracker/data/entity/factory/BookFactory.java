package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.Book;

public interface BookFactory {
	public Book create(Long bookId, String title, Author author, String language, int pages, AppUser user);
}
