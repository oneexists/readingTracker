package com.readingTracker.web.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

public class BookDTO {
	private Long id;
	@NotBlank(message = "Title cannot be empty.")
	private String title;
	@NotBlank(message = "Author cannot be empty.")
	private String author;
	private String language;
	private int pages;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate finish;
	private String username;

	public BookDTO() {
		super();
	}

	public BookDTO(String username) {
		this.username = username;
	}

	public BookDTO(Long id, String title, String author, String language, int pages, String username) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.username = username;
	}

	public BookDTO(Long id, String title, String author, String language, int pages, LocalDate start, LocalDate finish,
			String username) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.start = start;
		this.finish = finish;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getFinish() {
		return finish;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
