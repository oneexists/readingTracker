package com.readingTracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.readingTracker.web.dto.AppUserRegistrationDto;

@Controller
public class PageController {
	@GetMapping("/user/login")
	public String login(Model model) {
		model.addAttribute(new AppUserRegistrationDto());
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

}
