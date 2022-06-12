package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.Author;

public interface AuthorFactory {
	Author create(String name);

	Author create(Long id, String name);
}
