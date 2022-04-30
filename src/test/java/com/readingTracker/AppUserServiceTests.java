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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.impl.AppUserServiceImpl;

/**
 * Tests for AppUserService
 * 
 * @author skylar
 */
@SpringBootTest
class AppUserServiceTests {
	AppUser newAppUser;
	AppUser testAppUser;
	
	@Mock
	private AppUserRepository repository;
	@Mock
	private PasswordEncoder passwordEncoder;
	private AppUserService service;

	@BeforeEach
	void setUp() throws Exception {
		service = new AppUserServiceImpl(repository, passwordEncoder);
		newAppUser = new AppUser();
		testAppUser = new AppUser(1L, "Jesse Jackson", "user", "magnets", LocalDate.now().minusYears(25));
	}

	@AfterEach
	void tearDown() throws Exception {
		newAppUser = null;
		testAppUser = null;
	}

	/**
	 * Test save new app user
	 */
	@Test
	void testSaveUser() {
		service.saveUser(testAppUser);
		ArgumentCaptor<AppUser> appUserArgCaptor = ArgumentCaptor.forClass(AppUser.class);
		verify(repository).save(appUserArgCaptor.capture());
		assertThat(appUserArgCaptor.getValue()).isEqualTo(testAppUser);
	}

	/**
	 * Test find by app user id
	 */
	@Test
	void testGetUser() {
		given(repository.findById(testAppUser.getUserId())).willReturn(Optional.of(testAppUser));
		service.getUser(testAppUser.getUserId());
		ArgumentCaptor<Long> idArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).findById(idArgCaptor.capture());
		assertThat(idArgCaptor.getValue()).isEqualTo(testAppUser.getUserId());
	}

	/**
	 * Test get all app users
	 */
	@Test
	void testGetAllUsers() {
		service.getAllUsers();
		verify(repository).findAll();
	}

	/**
	 * Test update app user
	 */
	@Test
	void testUpdateUser() {
		given(repository.findById(testAppUser.getUserId())).willReturn(Optional.of(testAppUser));
		testAppUser.setName("Aaron Paul");
		service.updateUser(testAppUser);
		ArgumentCaptor<AppUser> updateArgCaptor = ArgumentCaptor.forClass(AppUser.class);
		verify(repository).saveAndFlush(updateArgCaptor.capture());
		assertThat(updateArgCaptor.getValue()).isEqualTo(testAppUser);
	}

	/**
	 * Test delete app user
	 */
	@Test
	void testDelete() {
		given(repository.existsById(testAppUser.getUserId())).willReturn(true);
		service.delete(testAppUser.getUserId());
		ArgumentCaptor<Long> deleteArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).deleteById(deleteArgCaptor.capture());
		assertThat(deleteArgCaptor.getValue()).isEqualTo(testAppUser.getUserId());
	}

}
