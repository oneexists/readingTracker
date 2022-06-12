package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.factory.impl.AuthorFactoryImpl;

public class AuthorProvider {
	public static AuthorFactory getFactory() {
		return new AuthorFactoryImpl();
	}
}
