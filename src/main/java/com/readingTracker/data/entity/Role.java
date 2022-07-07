package com.readingTracker.data.entity;

import static com.readingTracker.data.entity.Authority.ADMIN_AUTHORITIES;
import static com.readingTracker.data.entity.Authority.MANAGER_AUTHORITIES;
import static com.readingTracker.data.entity.Authority.USER_AUTHORITIES;

public enum Role {
	ROLE_USER(USER_AUTHORITIES), ROLE_MANAGER(MANAGER_AUTHORITIES), ROLE_ADMIN(ADMIN_AUTHORITIES);

	private final String[] authorities;

	Role(String... authorities) {
		this.authorities = authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}

}
