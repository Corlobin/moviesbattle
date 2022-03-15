package com.letscode.moviesbattle.core.gateway;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserGateway {
    UserDetails findByUsername(String username);
}
