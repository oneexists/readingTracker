package com.readingTracker.data.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "app_user")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 202204001L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	@Column(unique = true)
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	@Enumerated(EnumType.STRING)
	@Column(name = "user_roles")
	private Role userRole;
	@OneToMany(mappedBy = "user")
	private Set<Book> books = new HashSet<>();

	public AppUser() {
		super();
	}

	public AppUser(String name, String username, String password, LocalDate dateOfBirth, Role userRoles) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.userRole = userRoles;
	}

	public Set<Book> getBooks() {
		return Set.copyOf(books);
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "AppUser [userId=" + userId + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", dateOfBirth=" + dateOfBirth + ", userRole=" + userRole + "]";
	}

}
