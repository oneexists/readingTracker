package com.readingTracker.data.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Log implements Serializable {
	private static final long serialVersionUID = 202204001L;
	@Id @GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name="logs")
	private Book book;
	private LocalDate start;
	private LocalDate finish;
	@ManyToOne
	private ReadingList readingList;
	
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Log(Book book, LocalDate start, LocalDate finish, ReadingList readingList) {
		super();
		this.book = book;
		this.start = start;
		this.finish = finish;
		this.readingList = readingList;
	}


	public Book getBook() {
		return book;
	}

	public LocalDate getFinish() {
		return finish;
	}

	public Long getId() {
		return id;
	}

	public ReadingList getReadingList() {
		return readingList;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public void setReadingList(ReadingList readingList) {
		this.readingList = readingList;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", book=" + book + ", start=" + start + ", finish=" + finish + ", readingList="
				+ readingList + "]";
	}
}
