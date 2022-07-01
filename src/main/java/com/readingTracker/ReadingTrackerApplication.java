package com.readingTracker;

import java.time.LocalDate;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.readingTracker.data.entity.factory.AuthorProvider;
import com.readingTracker.data.entity.factory.BookProvider;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.BookService;
import com.readingTracker.web.dto.AppUserRegistrationDto;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class ReadingTrackerApplication extends SpringBootServletInitializer {
	private static Class<ReadingTrackerApplication> applicationClass = ReadingTrackerApplication.class;

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
			appUserService.registerUser(
					new AppUserRegistrationDto("Jesse", "user", "pass", LocalDate.now().minusYears(27).toString()));
			authorService.saveAuthor(AuthorProvider.getFactory().create("Oscar Wilde"));
			authorService.saveAuthor(AuthorProvider.getFactory().create("Simone de Beauvoir"));
			bookService.saveBook(BookProvider.getFactory().create(3L, "The Picture of Dorian Gray",
					authorService.findByName("Oscar Wilde"), "EN", 254, null,
					appUserService.findByUsername("user").get()));
			bookService.saveBook(BookProvider.getFactory().create(4L, "Tous les hommes sont mortels",
					authorService.findByName("Simone de Beauvoir"), "FR", 526, null,
					appUserService.findByUsername("user").get()));
		};
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingTrackerApplication.class, args);
	}

}
