package com.readingTracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.entity.Author;
import com.readingTracker.data.entity.Book;
import com.readingTracker.data.entity.UserRole;
import com.readingTracker.data.entity.factory.AppUserProvider;
import com.readingTracker.data.entity.factory.AuthorProvider;
import com.readingTracker.data.repository.BookRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.impl.BookServiceImpl;

/**
 * @author skylar
 *
 */
@SpringBootTest
class BookServiceTests {
	AppUser appUser;
	Author author;
	Book testBook;
	Book newBook;

	@Mock
	private BookRepository repository;
	@Mock
	private AppUserService appUserService;
	private BookService service;

	@BeforeEach
	void setUp() {
		service = new BookServiceImpl(repository, appUserService);
		appUser = AppUserProvider.getFactory().create("Jesse Jackson", "user", "magnets",
				LocalDate.now().minusYears(25), UserRole.ROLE_USER);
		author = AuthorProvider.getFactory().create(5L, "Walt Whitman");
		newBook = new Book();
		testBook = new Book(2L, "book title", author, "English", 32, appUser);
	}

	@AfterEach
	void tearDown() {
		appUser = null;
		author = null;
		testBook = null;
		newBook = null;
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.BookServiceImpl#saveBook(com.readingTracker.data.entity.Book)}.
	 */
	@Test
	void testSaveBook() {
		service.saveBook(testBook);
		ArgumentCaptor<Book> bookArgCaptor = ArgumentCaptor.forClass(Book.class);
		verify(repository).save(bookArgCaptor.capture());
		assertThat(bookArgCaptor.getValue()).isEqualTo(testBook);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.BookServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	void testFindById() {
		given(repository.findById(testBook.getId())).willReturn(Optional.of(testBook));
		assertThat(service.findById(testBook.getId())).isEqualTo(testBook);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.BookServiceImpl#getAllBooks()}.
	 */
	@Test
	void testAllBooks() {
		service.getAllBooks();
		verify(repository).findAll();
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.BookServiceImpl#updateBook(com.readingTracker.data.entity.Book)}.
	 */
	@Test
	void testUpdateBook() {
		given(repository.findById(testBook.getId())).willReturn(Optional.of(testBook));
		testBook.setTitle("new title");
		service.updateBook(testBook);
		ArgumentCaptor<Book> updateArgCaptor = ArgumentCaptor.forClass(Book.class);
		verify(repository).saveAndFlush(updateArgCaptor.capture());
		assertThat(updateArgCaptor.getValue()).isEqualTo(testBook);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.BookServiceImpl#deleteBook(java.lang.Long)}.
	 */
	@Test
	void testDeleteBook() {
		given(repository.existsById(testBook.getId())).willReturn(true);
		service.deleteBook(testBook.getId());
		ArgumentCaptor<Long> deleteArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).deleteById(deleteArgCaptor.capture());
		assertThat(deleteArgCaptor.getValue()).isEqualTo(testBook.getId());
	}

}
