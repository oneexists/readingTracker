package com.readingTracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	private final String INDEX_PAGE = "index";
	private final String HOME_PAGE = "home";
	private final String LOGIN_PAGE = "login";
	
	@GetMapping("/")
	public String landingPage() {
		return INDEX_PAGE;
	}

	@GetMapping("/home")
	public String home(Model model) {
		return HOME_PAGE;
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		return LOGIN_PAGE;
	}
	
}
