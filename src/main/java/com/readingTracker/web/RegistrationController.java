package com.readingTracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.readingTracker.service.AppUserService;
import com.readingTracker.web.dto.AppUserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	private AppUserService appUserService;

	@Autowired
	public RegistrationController(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	@ModelAttribute("appUser")
	public AppUserRegistrationDto appUserRegistrationDto() {
		return new AppUserRegistrationDto();
	}

	@GetMapping("/register")
	public String registerUser(@ModelAttribute("appUser") AppUserRegistrationDto appUserDto) {
		appUserService.registerUser(appUserDto);
		return "redirect:/registration?success";
	}

	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") AppUserRegistrationDto registrationDto) {
		appUserService.registerUser(registrationDto);
		return "redirect:/registration?success";
	}

}
