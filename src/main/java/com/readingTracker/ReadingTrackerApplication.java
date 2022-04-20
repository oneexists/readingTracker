package com.readingTracker;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.service.AppUserService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class ReadingTrackerApplication {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			AppUser appUser = new AppUser("user", "pass", LocalDate.now().minusYears(27), "Admin");
			appUserService.saveUser(appUser);
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReadingTrackerApplication.class, args);
	}

}
