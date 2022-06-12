package com.readingTracker.data.entity.factory.impl;

import java.time.LocalDate;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.data.entity.factory.LogFactory;

public class LogFactoryImpl implements LogFactory {

	@Override
	public Log create(Book book, ReadingStatus status, LocalDate start, LocalDate finish) {
		return new Log(book, status, start, finish);
	}

	@Override
	public Log create(Long logId, Book book, ReadingStatus status, LocalDate start, LocalDate finish) {
		return new Log(logId, book, status, start, finish);
	}

}
