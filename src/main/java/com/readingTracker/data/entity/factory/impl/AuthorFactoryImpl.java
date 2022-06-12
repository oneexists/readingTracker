package com.readingTracker.data.entity.factory.impl;

import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.factory.AuthorFactory;

public class AuthorFactoryImpl implements AuthorFactory {

	@Override
	public Author create(String name) {
		return new Author(name);
	}

	@Override
	public Author create(Long id, String name) {
		return new Author(id, name);
	}

}
