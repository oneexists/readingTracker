package com.readingTracker.web.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class BookDto {
	private Long id;
	@NotBlank(message = "Title cannot be empty.")
	private String title;
	@NotBlank(message = "Author cannot be empty.")
	private String author;
	@NotBlank(message = "Language cannot be empty.")
	@Size(min = 2)
	private String language;
	private int pages;
	private String coverImage;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	private LocalDate start;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	private LocalDate finish;
	private String username;

	public BookDto() {
		super();
	}

	public BookDto(String username) {
		this.username = username;
	}

	public BookDto(Long id, String title, String author, String language, int pages, String username) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.language = language;
		this.pages = pages;
		this.username = username;
	}

	public BookDto(Long id, String title, String author, String language, int pages, LocalDate start, LocalDate finish,
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

	public String getAuthor() {
		return author;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public LocalDate getFinish() {
		return finish;
	}

	public Long getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public int getPages() {
		return pages;
	}

	public LocalDate getStart() {
		return start;
	}

	public String getTitle() {
		return title;
	}

	public String getUsername() {
		return username;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
