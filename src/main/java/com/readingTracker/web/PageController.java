package com.readingTracker.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.data.entity.factory.AuthorProvider;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.web.domain.BookConverter;
import com.readingTracker.web.dto.AppUserRegistrationDto;
import com.readingTracker.web.dto.BookDto;

@Controller
public class PageController {
	private final String ADD_BOOK_PAGE = "books/add-book";
	private final String INDEX_PAGE = "index";
	private final String EDIT_PAGE = "books/edit-book";
	private final String LOGIN_PAGE = "login";
	private final String VIEW_BOOK_PAGE = "books/view-book";

	private final BookService bookService;
	private final AuthorService authorService;
	private final LogService logService;
	private final BookConverter bookConverter;

	@Autowired
	public PageController(BookService bookService, AuthorService authorService, LogService logService,
			BookConverter bookConverter) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.logService = logService;
		this.bookConverter = bookConverter;
	}

	@GetMapping("/user/login")
	public String login(Model model) {
		model.addAttribute(new AppUserRegistrationDto());
		return LOGIN_PAGE;
	}

	@GetMapping("/")
	public String home(Authentication authentication, Model model) {
		List<Log> finishedLogs = logService.findByUsername(authentication.getName());
		finishedLogs.removeIf(log -> log.getStatus() != ReadingStatus.FINISHED);
		List<Log> currentlyReading = logService.findByUsername(authentication.getName());
		currentlyReading.removeIf(log -> log.getStatus() != ReadingStatus.IN_PROGRESS);

		Map<String, List<Log>> logMap = finishedLogs.stream()
				.collect(Collectors.groupingBy(log -> log.getBook().getLanguage()));
		int totalPages = finishedLogs.stream().collect(Collectors.summingInt(log -> log.getBook().getPages()));

		model.addAttribute("books", bookService.findByUsername(authentication.getName()));
		model.addAttribute("currentlyReading", currentlyReading);
		model.addAttribute("languages", logMap);
		model.addAttribute("totalBooks", finishedLogs.size());
		model.addAttribute("totalPages", totalPages);
		return INDEX_PAGE;
	}

	@GetMapping("/addBook")
	public String addBook(Authentication authentication, Model model) {
		model.addAttribute("book", new BookDto(authentication.getName()));
		return ADD_BOOK_PAGE;
	}

	@GetMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookConverter.bookToClient(bookService.findById(id)));
		return EDIT_PAGE;
	}

	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") Long id, Authentication authentication, Model model) {
		logService.deleteByBookId(id);
		bookService.deleteBook(id);
		return "redirect:/";
	}

	@PostMapping("/save")
	public String saveBook(@Valid @ModelAttribute("book") final BookDto bookDTO, BindingResult result,
			Authentication authentication, Model model) {
		if (result.hasErrors()) {
			return ADD_BOOK_PAGE;
		}
		if (authorService.findByName(bookDTO.getAuthor()) == null) {
			authorService.saveAuthor(AuthorProvider.getFactory().create(bookDTO.getAuthor()));
		}
		bookService.saveBook(bookConverter.clientToBook(bookDTO));
		return "redirect:/";
	}

	@GetMapping("/view/{id}")
	public String viewBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
		model.addAttribute("logs", logService.findByBook(book));
		return VIEW_BOOK_PAGE;
	}

}
