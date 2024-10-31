package com.nitp.club.management.exceptions;

public class IncorrectPasswordException extends RuntimeException{
	private static final long serialVersionUID = -4616145730836757015L;

	public IncorrectPasswordException() {
		super("Invalid username/password");
	}
}
