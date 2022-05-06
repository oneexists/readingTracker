package com.readingTracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.readingTracker.service.BookService;
import com.readingTracker.web.dto.AppUserRegistrationDto;

@Controller
public class PageController {
	private final BookService bookService;

	@Autowired
	public PageController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/user/login")
	public String login(Model model) {
		model.addAttribute(new AppUserRegistrationDto());
		return "login";
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "index";
	}

}
