package com.readingTracker.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Book implements Serializable {
	private static final long serialVersionUID = 202205002L;
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@ManyToOne
	private Author author;
	private String language;
	private int pages;
	@OneToMany(mappedBy = "book")
	private Set<Log> logs = new HashSet<>();
	@ManyToOne
	private AppUser user;

	public Book() {
		super();
	}

	public Book(AppUser user) {
		this.user = user;
	}

	public Book(String title, Author author, String language, int pages, AppUser user) {
		super();
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.user = user;
	}

	public Book(Long id, String title, Author author, String language, int pages, AppUser user) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.user = user;
	}

	public Book(Long id, String title, Author author, String language, int pages, Set<Log> logs, AppUser user) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.logs = logs;
		this.user = user;
	}

	public Author getAuthor() {
		return author;
	}

	public Long getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public int getPages() {
		return pages;
	}

	public String getTitle() {
		return title;
	}

	public AppUser getUser() {
		return user;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", language=" + language + ", pages="
				+ pages + ", logs=" + logs + ", user=" + user.getName() + "]";
	}

}
