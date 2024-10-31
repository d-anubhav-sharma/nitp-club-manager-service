package com.nitp.club.management.exceptions;

public class AlreadyLoggedOutException extends RuntimeException{
	private static final long serialVersionUID = -8307235243139864162L;

	public AlreadyLoggedOutException() {
		super("User doesn't seem to be logged in. perhaps already logged out");
	}
	
}
