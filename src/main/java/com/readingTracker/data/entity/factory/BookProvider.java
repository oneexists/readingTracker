package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.factory.impl.BookFactoryImpl;

public class BookProvider {
	public static BookFactory getFactory() {
		return new BookFactoryImpl();
	}
}
