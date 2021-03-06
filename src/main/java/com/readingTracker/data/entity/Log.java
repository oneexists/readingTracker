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
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name = "logs")
	private Book book;
	private ReadingStatus status;
	private LocalDate start;
	private LocalDate finish;

	public Log() {
		super();
	}

	public Log(Book book, ReadingStatus status, LocalDate start, LocalDate finish) {
		super();
		this.book = book;
		this.status = status;
		this.start = start;
		this.finish = finish;
	}

	public Log(Long id, Book book, ReadingStatus status, LocalDate start, LocalDate finish) {
		super();
		this.id = id;
		this.book = book;
		this.status = status;
		this.start = start;
		this.finish = finish;
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

	public LocalDate getStart() {
		return start;
	}

	public ReadingStatus getStatus() {
		return status;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public void setStatus(ReadingStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", book=" + book.getTitle() + ", status=" + status + ", start=" + start + ", finish="
				+ finish + "]";
	}

}
