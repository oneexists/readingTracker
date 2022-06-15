package com.readingTracker.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.factory.AuthorProvider;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.web.domain.BookConverter;
import com.readingTracker.web.dto.BookDto;

@Controller
@RequestMapping("/books")
public class BookController {
	private final String ADD_BOOK_PAGE = "books/add-book";
	private final String EDIT_BOOK_PAGE = "books/edit-book";
	private final String VIEW_BOOK_PAGE = "books/view-book";

	private final BookConverter bookConverter;
	private final BookService bookService;
	private final AuthorService authorService;
	private final LogService logService;

	@Autowired
	public BookController(BookConverter bookConverter, BookService bookService, AuthorService authorService,
			LogService logService) {
		this.bookConverter = bookConverter;
		this.bookService = bookService;
		this.authorService = authorService;
		this.logService = logService;
	}

	@ModelAttribute("/book")
	public BookDto book() {
		return new BookDto();
	}

	@PostMapping("/save")
	public String addBook(@Valid @ModelAttribute("book") final BookDto bookDto, BindingResult result,
			Authentication authentication, Model model) {
		if (result.hasErrors()) {
			return ADD_BOOK_PAGE;
		}
		if (authorService.findByName(bookDto.getAuthor()) == null) {
			authorService.saveAuthor(AuthorProvider.getFactory().create(bookDto.getAuthor()));
		}
		Book newBook = bookService.saveBook(bookConverter.clientToBook(bookDto));
		return "redirect:/books/" + newBook.getId();
	}

	@GetMapping("/edit{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookConverter.bookToClient(bookService.findById(id)));
		return EDIT_BOOK_PAGE;
	}

	@GetMapping("add")
	public String addBook(Authentication authentication, Model model) {
		model.addAttribute("book", new BookDto(authentication.getName()));
		return ADD_BOOK_PAGE;
	}

	@GetMapping("/delete{id}")
	public String deleteLog(@PathVariable("id") Long id, Authentication authentication, Model model) {
		bookService.findById(id).getLogs().forEach(log -> logService.deleteLog(log.getId()));
		bookService.deleteBook(id);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String viewBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
		model.addAttribute("logs", book.getLogs());
		return VIEW_BOOK_PAGE;
	}
}
