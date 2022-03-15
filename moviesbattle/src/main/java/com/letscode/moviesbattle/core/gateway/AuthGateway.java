package com.letscode.moviesbattle.core.gateway;

public interface AuthGateway {

    String authenticate(String username, String password);

    void authenticate(String token);
}
