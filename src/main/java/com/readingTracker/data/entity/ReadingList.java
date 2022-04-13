package com.readingTracker.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ReadingList {
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private AppUser appUser;
	@OneToMany(mappedBy = "readingList")
	private Set<Book> books = new HashSet<Book>();
	@OneToMany(mappedBy = "readingList")
	private Set<Log> logs = new HashSet<Log>();
	
	public ReadingList() {
		super();
	}
	
	public ReadingList(AppUser user) {
		super();
		this.appUser = user;
	}
	
	public ReadingList(Long id, AppUser user, Set<Book> books, Set<Log> logs) {
		super();
		this.id = id;
		this.appUser = user;
		this.books = books;
		this.logs = logs;
	}
	
	public AppUser getUser() {
		return appUser;
	}
	
	public void setUser(AppUser user) {
		this.appUser = user;
	}
	
	public Set<Book> getBooks() {
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public Set<Log> getLogs() {
		return logs;
	}
	
	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}
	
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "ReadingList [id=" + id + ", user=" + appUser + ", books=" + books + ", logs=" + logs + "]";
	}
	
}
