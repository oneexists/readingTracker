package com.readingTracker.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.web.dto.AppUserRegistrationDto;

@Controller
public class PageController {
	private final String INDEX_PAGE = "index";
	private final String LOGIN_PAGE = "login";

	private final BookService bookService;
	private final LogService logService;

	@Autowired
	public PageController(BookService bookService, LogService logService) {
		this.bookService = bookService;
		this.logService = logService;
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

}
