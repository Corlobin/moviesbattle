package com.letscode.moviesbattle.infra.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound() { super("User not found!"); }
}
