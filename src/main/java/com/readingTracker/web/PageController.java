package com.readingTracker.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.readingTracker.service.BookService;
import com.readingTracker.web.dto.AppUserRegistrationDto;
import com.readingTracker.web.dto.BookDTO;

@Controller
public class PageController {
	private final String ADD_PAGE = "add-book";
	private final String INDEX_PAGE = "index";
	private final String EDIT_PAGE = "edit-book";
	private final String LOGIN_PAGE = "login";
	private final String ADD_LOG_PAGE = "add-log";
	private final String VIEW_BOOK_PAGE = "view-book";
	private final BookService bookService;

	@Autowired
	public PageController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/user/login")
	public String login(Model model) {
		model.addAttribute(new AppUserRegistrationDto());
		return LOGIN_PAGE;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return INDEX_PAGE;
	}

	@GetMapping("addLog/{id}")
	public String addLog(@PathVariable("id") Long id, Model model) throws NotFoundException {
		model.addAttribute("book", bookService.findById(id));
		return ADD_LOG_PAGE;
	}

	@GetMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookService.findById(id));
		return EDIT_PAGE;
	}

	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.deleteBook(id);
		model.addAttribute("books", bookService.getAllBooks());
		return home(model);
	}

	@PostMapping("save")
	public String save(@Valid @ModelAttribute("book") final BookDTO bookDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return ADD_PAGE;
		}
		// TODO save bookDTO
		return home(model);
	}

	@GetMapping("/view/{id}")
	public String viewBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookService.findById(id));
		return VIEW_BOOK_PAGE;
	}
}
