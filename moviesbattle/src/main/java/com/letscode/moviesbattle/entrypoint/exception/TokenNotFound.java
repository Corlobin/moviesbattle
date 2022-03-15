package com.letscode.moviesbattle.entrypoint.exception;

public class TokenNotFound extends RuntimeException {
    public TokenNotFound() {
        super("Ops, usuário ou senha inválidos!");
    }
}
