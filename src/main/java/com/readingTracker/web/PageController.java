package com.readingTracker.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.service.exceptions.AppUserNotFoundException;
import com.readingTracker.web.domain.BookConverter;
import com.readingTracker.web.dto.AppUserRegistrationDto;
import com.readingTracker.web.dto.BookDto;

@Controller
public class PageController {
	private final String ADD_PAGE = "add-book";
	private final String INDEX_PAGE = "index";
	private final String EDIT_PAGE = "edit-book";
	private final String LOGIN_PAGE = "login";
	private final String ADD_LOG_PAGE = "add-log";
	private final String VIEW_BOOK_PAGE = "view-book";

	private final BookService bookService;
	private final AuthorService authorService;
	private final LogService logService;
	private final AppUserService appUserService;
	private final BookConverter bookConverter;

	@Autowired
	public PageController(BookService bookService, AuthorService authorService, LogService logService,
			AppUserService appUserService, BookConverter bookConverter) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.logService = logService;
		this.appUserService = appUserService;
		this.bookConverter = bookConverter;
	}

	@GetMapping("/user/login")
	public String login(Model model) {
		model.addAttribute(new AppUserRegistrationDto());
		return LOGIN_PAGE;
	}

	@GetMapping("/")
	public String home(Authentication authentication, Model model) {
		model.addAttribute("books", bookService.findByUsername(authentication.getName()));
		return INDEX_PAGE;
	}

	@GetMapping("/addBook")
	public String addBook(Authentication authentication, Model model) {
		model.addAttribute("book", new BookDto(authentication.getName()));
		return ADD_PAGE;
	}

	@GetMapping("addLog/{id}")
	public String addLog(@PathVariable("id") Long id, Authentication authentication, Model model)
			throws NotFoundException {
		Book book = bookService.findById(id);
		BookDto bookDTO = bookConverter.bookToClient(book);
		bookDTO.setStart(LocalDate.now());
		model.addAttribute("book", bookDTO);
		return ADD_LOG_PAGE;
	}

	@GetMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookConverter.bookToClient(bookService.findById(id)));
		return EDIT_PAGE;
	}

	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") Long id, Authentication authentication, Model model) {
		bookService.deleteBook(id);
		model.addAttribute("books", bookService.getAllBooks());
		return home(authentication, model);
	}

	@PostMapping("save")
	public String save(@Valid @ModelAttribute("book") final BookDto bookDTO, Authentication authentication,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return ADD_PAGE;
		}
		if (authorService.findByName(bookDTO.getAuthor()) == null) {
			authorService.saveAuthor(new Author(bookDTO.getAuthor()));
		}
		bookService.saveBook(bookConverter.clientToBook(bookDTO));
		if (bookDTO.getStart() != null && bookDTO.getFinish() != null) {
			Book book = bookService.findById(bookDTO.getId());
			AppUser user = appUserService.findByUsername(authentication.getName())
					.orElseThrow(() -> new AppUserNotFoundException(authentication.getName()));
			logService.saveLog(new Log(book, ReadingStatus.FINISHED, bookDTO.getStart(), bookDTO.getFinish(), user));
		}
		return home(authentication, model);
	}

	@GetMapping("/view/{id}")
	public String viewBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", bookService.findById(id));
		return VIEW_BOOK_PAGE;
	}
}
