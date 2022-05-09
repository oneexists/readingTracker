package com.readingTracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.readingTracker.data.entity.Author;
import com.readingTracker.data.repository.AuthorRepository;
import com.readingTracker.service.AuthorService;
import com.readingTracker.service.impl.AuthorServiceImpl;

/**
 * @author skylar
 *
 */
@SpringBootTest
class AuthorServiceTests {
	Author author;

	@Mock
	private AuthorRepository repository;
	private AuthorService service;

	@BeforeEach
	void setUp() {
		service = new AuthorServiceImpl(repository);
		author = new Author(1L, "name");
	}

	@AfterEach
	void tearDown() {
		author = null;
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#saveAuthor(com.readingTracker.data.entity.Author)}.
	 */
	@Test
	void testSaveAuthor() {
		service.saveAuthor(author);
		ArgumentCaptor<Author> authorArgCaptor = ArgumentCaptor.forClass(Author.class);
		verify(repository).save(authorArgCaptor.capture());
		assertThat(authorArgCaptor.getValue()).isEqualTo(author);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	void testFindById() {
		given(repository.findById(author.getId())).willReturn(Optional.of(author));
		assertThat(service.findById(author.getId())).isEqualTo(author);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#findByName(java.lang.String)}.
	 */
	@Test
	void testFindByName() {
		service.findByName("name");
		ArgumentCaptor<String> nameArgCaptor = ArgumentCaptor.forClass(String.class);
		verify(repository).findByName(nameArgCaptor.capture());
		assertThat(nameArgCaptor.getValue()).isEqualTo("name");
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#getAllAuthors()}.
	 */
	@Test
	void testGetAllAuthors() {
		service.getAllAuthors();
		verify(repository).findAll();
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#updateAuthor(com.readingTracker.data.entity.Author)}.
	 */
	@Test
	void testUpdateAuthor() {
		given(repository.findById(author.getId())).willReturn(Optional.of(author));
		author.setName("new name");
		service.updateAuthor(author);
		ArgumentCaptor<Author> updateArgCaptor = ArgumentCaptor.forClass(Author.class);
		verify(repository).saveAndFlush(updateArgCaptor.capture());
		assertThat(updateArgCaptor.getValue()).isEqualTo(author);
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AuthorServiceImpl#deleteAuthor(java.lang.Long)}.
	 */
	@Test
	void testDeleteAuthor() {
		given(repository.existsById(author.getId())).willReturn(true);
		service.deleteAuthor(author.getId());
		ArgumentCaptor<Long> deleteArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).deleteById(deleteArgCaptor.capture());
		assertThat(deleteArgCaptor.getValue()).isEqualByComparingTo(author.getId());
	}

}
