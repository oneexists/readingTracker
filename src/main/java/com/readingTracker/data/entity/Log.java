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
	private AppUser user;
	
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Log(Long id, Book book, LocalDate start, LocalDate finish, AppUser user) {
		super();
		this.id = id;
		this.book = book;
		this.start = start;
		this.finish = finish;
		this.user = user;
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

	public void setBook(Book book) {
		this.book = book;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", book=" + book + ", start=" + start + ", finish=" + finish + ", user=" + user.getName() + "]";
	}


}
