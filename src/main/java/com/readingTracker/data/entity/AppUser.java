package com.readingTracker.data.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class AppUser implements Serializable {
	private static final long serialVersionUID = 202204001L;
	@Id @GeneratedValue
	private Long userId;
	private String name;
	@Column(unique=true)
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private LocalDate dateOfBirth;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	@OneToOne
	private ReadingList readingList;
	
	public AppUser() {
		super();
	}
	
	public AppUser(String username, String password, LocalDate dateOfBirth, String name) {
		super();
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.userRole = UserRole.ROLE_USER;
		this.readingList = new ReadingList(this);
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
	
	public UserRole getUserRole() {
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

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "AppUser [userId=" + userId + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", dateOfBirth=" + dateOfBirth + ", userRole=" + userRole + "]";
	}

	public ReadingList getReadingList() {
		return readingList;
	}

	public void setReadingList(ReadingList readingList) {
		this.readingList = readingList;
	}
	
}
