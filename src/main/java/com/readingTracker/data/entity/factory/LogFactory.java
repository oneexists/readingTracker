package com.readingTracker.data.entity.factory;

import java.time.LocalDate;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;

public interface LogFactory {
	Log create(Book book, ReadingStatus status, LocalDate start, LocalDate finish);

	Log create(Long logId, Book book, ReadingStatus status, LocalDate start, LocalDate finish);
}
