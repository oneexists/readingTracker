package com.readingTracker;

import java.time.LocalDate;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.UserRole;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.BookService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class ReadingTrackerApplication {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("jasyptStringEncryptor")
	StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("pypy");
		config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService, AuthorService authorService, BookService bookService) {
		return args -> {
			appUserService
					.saveUser(new AppUser("Jesse", "user", "pass", LocalDate.now().minusYears(27), UserRole.ROLE_USER));
			authorService.saveAuthor(new Author("Oscar Wilde"));
			bookService.saveBook(new Book("The Picture of Dorian Gray", authorService.getAllAuthors().get(0), "EN", 254,
					appUserService.findByUsername("user").get()));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingTrackerApplication.class, args);
	}

}
