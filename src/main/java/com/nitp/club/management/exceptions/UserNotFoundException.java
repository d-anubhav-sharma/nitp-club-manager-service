package com.nitp.club.management.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String username) {
		super(username+": user not found");
	}

	private static final long serialVersionUID = 7013750206105909625L;

}
