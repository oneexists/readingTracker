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
import org.springframework.web.bind.annotation.RequestMapping;

import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.data.entity.factory.LogProvider;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.web.domain.BookConverter;
import com.readingTracker.web.dto.BookDto;

@Controller
@RequestMapping("/logs")
public class LogController {
	private final String ADD_LOG_PAGE = "logs/add-log";
	private final String VIEW_LOGS_PAGE = "logs/view-logs";

	private final BookConverter bookConverter;
	private final BookService bookService;
	private final LogService logService;

	@Autowired
	public LogController(BookConverter bookConverter, BookService bookService, LogService logService) {
		this.bookConverter = bookConverter;
		this.bookService = bookService;
		this.logService = logService;
	}

	@ModelAttribute("/log")
	public BookDto log() {
		return new BookDto();
	}

	@PostMapping("/save")
	public String saveLog(@Valid @ModelAttribute("book") final BookDto bookDto, BindingResult result,
			Authentication authentication, Model model) {
		if (result.hasErrors()) {
			return ADD_LOG_PAGE;
		}
		Book book = bookService.findById(bookDto.getId());

		if (bookDto.getFinish() == null) {
			logService.saveLog(LogProvider.getFactory().create(book, ReadingStatus.IN_PROGRESS, bookDto.getStart(),
					bookDto.getFinish()));
		} else {
			logService.saveLog(LogProvider.getFactory().create(book, ReadingStatus.FINISHED, bookDto.getStart(),
					bookDto.getFinish()));
		}
		return "redirect:/books/" + book.getId();
	}

	@GetMapping("add{id}")
	public String addLog(@PathVariable("id") Long id, Authentication authentication, Model model)
			throws NotFoundException {
		Book book = bookService.findById(id);
		BookDto bookDTO = bookConverter.bookToClient(book);
		bookDTO.setStart(LocalDate.now());
		model.addAttribute("book", bookDTO);
		return ADD_LOG_PAGE;
	}

	@GetMapping("/delete{id}")
	public String deleteLog(@PathVariable("id") Long id, Authentication authentication, Model model) {
		Book book = bookService.findById(logService.findById(id).getBook().getId());
		logService.deleteLog(id);
		return "redirect:/books/" + book.getId();
	}

	@GetMapping("/all")
	public String viewLogs(Authentication authentication, Model model) {
		model.addAttribute("logs", logService.findByUsername(authentication.getName()));
		return VIEW_LOGS_PAGE;
	}
}
