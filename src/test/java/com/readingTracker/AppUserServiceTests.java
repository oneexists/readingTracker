package com.readingTracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
import com.readingTracker.data.entity.Role;
import com.readingTracker.data.entity.factory.AppUserFactory;
import com.readingTracker.data.entity.factory.AppUserProvider;
import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.service.AppUserService;
import com.readingTracker.service.impl.AppUserServiceImpl;
import com.readingTracker.web.dto.AppUserRegistrationDto;

/**
 * @author skylar
 * 
 *         Tests for AppUserService
 */
@SpringBootTest
class AppUserServiceTests {
	AppUser newAppUser;
	AppUser testAppUser;
	AppUserRegistrationDto appUserDto;

	@Mock
	private AppUserRepository repository;
	@Mock
	private PasswordEncoder passwordEncoder;
	private AppUserService service;

	@BeforeEach
	void setUp() throws Exception {
		service = new AppUserServiceImpl(repository, passwordEncoder);
		AppUserFactory userFactory = AppUserProvider.getFactory();

		testAppUser = userFactory.create("Jesse Jackson", "user", "magnets", LocalDate.now().minusYears(25),
				Role.ROLE_USER);
		newAppUser = userFactory.create(null, null, null, null, null);
		appUserDto = new AppUserRegistrationDto("Mike", "security", "newpass",
				LocalDate.now().minusYears(40).toString());
	}

	@AfterEach
	void tearDown() throws Exception {
		newAppUser = null;
		testAppUser = null;
		appUserDto = null;
	}

	/**
	 * Test find by app user id <br />
	 * Test method for
	 * {@link com.readingTracker.service.impl.AppUserServiceImpl#getUser(java.lang.Long)}
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
	 * Test get all app users <br />
	 * Test method for
	 * {@link com.readingTracker.service.impl.AppUserServiceImpl#getAllUsers()}.
	 */
	@Test
	void testGetAllUsers() {
		service.getAllUsers();
		verify(repository).findAll();
	}

	/**
	 * Test update app user <br />
	 * Test method for
	 * {@link com.readingTracker.service.impl.AppUserServiceImpl#updateUser(com.readingTracker.data.entity.AppUser)}
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
	 * Test delete app user <br />
	 * Test method for
	 * {@link com.readingTracker.service.impl.AppUserServiceImpl#delete(java.lang.Long)}
	 */
	@Test
	void testDelete() {
		given(repository.existsById(testAppUser.getUserId())).willReturn(true);
		service.delete(testAppUser.getUserId());
		ArgumentCaptor<Long> deleteArgCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repository).deleteById(deleteArgCaptor.capture());
		assertThat(deleteArgCaptor.getValue()).isEqualTo(testAppUser.getUserId());
	}

	/**
	 * Test method for
	 * {@link com.readingTracker.service.impl.AppUserServiceImpl#registerUser(com.readingTracker.web.dto.AppUserRegistrationDto)}.
	 */
	@Test
	void testRegisterUser() {
		service.registerUser(appUserDto);
		ArgumentCaptor<AppUser> registerArgCaptor = ArgumentCaptor.forClass(AppUser.class);
		verify(repository).save(registerArgCaptor.capture());
		assertThat(registerArgCaptor.getValue().getName()).isEqualTo(appUserDto.getName());
		assertThat(registerArgCaptor.getValue().getDateOfBirth()).isEqualTo(appUserDto.getDateOfBirth());
		assertThat(registerArgCaptor.getValue().getUsername()).isEqualTo(appUserDto.getUsername());
		assertThat(registerArgCaptor.getValue().getPassword()).isNotEqualTo(appUserDto.getPassword());
		assertThat(registerArgCaptor.getValue().getUserRole()).isEqualTo(Role.ROLE_USER);
	}

	@Test
	void testRegisterUserFutureDob() {
		appUserDto.setDateOfBirth(LocalDate.now().plusDays(1).toString());
		assertThatThrownBy(() -> service.registerUser(appUserDto)).isInstanceOf(Exception.class)
				.hasMessageContaining("Invalid date of birth (" + appUserDto.getDateOfBirth() + ")");
		verify(repository, never()).save(any());

	}

	@Test
	void testRegisterTakenUsername() {
		given(repository.existsByUsername(appUserDto.getUsername())).willReturn(true);
		assertThatThrownBy(() -> service.registerUser(appUserDto)).isInstanceOf(Exception.class)
				.hasMessageContaining("Username is taken (" + appUserDto.getUsername() + ")");
		verify(repository, never()).save(any());
	}

}
