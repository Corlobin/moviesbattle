package com.letscode.moviesbattle.entrypoint.exception;

public class CredentialNotFound extends RuntimeException {
    public CredentialNotFound() {
        super("Ops, credential not found!");
    }
}
