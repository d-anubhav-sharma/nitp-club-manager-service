package com.nitp.club.management.exceptions;

public class AuthCookieNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -8687829106637987266L;

	public AuthCookieNotFoundException() {
		super("Auth cookie not found. You sure you have logged in?");
	}
}
