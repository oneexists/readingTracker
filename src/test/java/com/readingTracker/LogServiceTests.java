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
import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.data.entity.UserRole;
import com.readingTracker.data.entity.factory.AppUserProvider;
import com.readingTracker.data.entity.factory.AuthorProvider;
import com.readingTracker.data.repository.LogRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.BookService;
import com.readingTracker.service.LogService;
import com.readingTracker.service.impl.LogServiceImpl;

/**
 * @author skylar
 *
 */
@SpringBootTest
class LogServiceTests {
	Book book;
	Author author;
	AppUser appUser;
	Log testLog;
	Log newLog;

	@Mock
	private LogRepository repository;
	@Mock
	private AppUserService appUserService;
	@Mock
	private BookService bookService;
	private LogService service;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		service = new LogServiceImpl(repository, appUserService, bookService);
		author = AuthorProvider.getFactory().create(5L, "Walt Whitman");
		appUser = AppUserProvider.getFactory().create("Jesse Jackson", "user", "magnets",
				LocalDate.now().minusYears(25), UserRole.ROLE_USER);
		book = new Book(2L, "book title", author, "English", 32, appUser);
		testLog = new Log(3L, book, ReadingStatus.FINISHED, LocalDate.now().minusMonths(1), LocalDate.now());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		book = null;
		author = null;
		appUser = null;
		testLog = null;
		newLog = null;
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.LogServiceImpl#saveLog(com.readingTracker.data.entity.Log)}.
	 */
	@Test
	void testSaveLog() {
		service.saveLog(testLog);
		ArgumentCaptor<Log> logArgCaptor = ArgumentCaptor.forClass(Log.class);
		verify(repository).save(logArgCaptor.capture());
		assertThat(logArgCaptor.getValue()).isEqualTo(testLog);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.LogServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	void testFindById() {
		given(repository.findById(testLog.getId())).willReturn(Optional.of(testLog));
		assertThat(service.findById(testLog.getId())).isEqualTo(testLog);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.LogServiceImpl#getAllLogs()}.
	 */
	@Test
	void testAllLogs() {
		service.getAllLogs();
		verify(repository).findAll();
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.LogServiceImpl#updateLog(com.readingTracker.data.entity.Log)}.
	 */
	@Test
	void testUpdateLog() {
		given(repository.findById(testLog.getId())).willReturn(Optional.of(testLog));
		testLog.setStart(LocalDate.now().minusWeeks(1));
		testLog.setFinish(LocalDate.now().minusDays(3));
		service.updateLog(testLog);
		ArgumentCaptor<Log> updateArgCaptor = ArgumentCaptor.forClass(Log.class);
		verify(repository).saveAndFlush(updateArgCaptor.capture());
		assertThat(updateArgCaptor.getValue()).isEqualTo(testLog);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.LogServiceImpl#deleteLog(java.lang.Long)}.
	 */
	@Test
	void testDeleteLog() {
		given(repository.existsById(testLog.getId())).willReturn(true);
		service.deleteLog(testLog.getId());
		ArgumentCaptor<Long> deleteArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).deleteById(deleteArgCaptor.capture());
		assertThat(deleteArgCaptor.getValue()).isEqualTo(testLog.getId());
	}

}
