package com.letscode.moviesbattle.core.usecase;

import com.letscode.moviesbattle.core.gateway.AuthGateway;
import com.letscode.moviesbattle.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUseCase implements UserDetailsService {

    private final AuthGateway authGateway;
    private final UserGateway userGateway;

    public Optional<String> authenticate(String username, String password) {
        return Optional.ofNullable(authGateway.authenticate(username, password));
    }

    public void authenticate(String token) {
        authGateway.authenticate(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userGateway.findByUsername(username);
    }
}
