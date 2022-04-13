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
	private static final long serialVersionUID = 202204001L;
	@Id @GeneratedValue
	private Long id;
	private String title;
	private String author;
	private String language;
	private int pages;
	@OneToMany(mappedBy = "book")
	private Set<Log> logs = new HashSet<>();
	@ManyToOne
	private ReadingList readingList;
	
	public Book() {
		super();
	}
	
	public Book(String title, String author, String language, int pages) {
		super();
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public int getPages() {
		return pages;
	}
	
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", language=" + language + ", pages="
				+ pages + "]";
	}
	
}
