package com.letscode.moviesbattle.infra.gateway;

import com.letscode.moviesbattle.core.gateway.UserGateway;
import com.letscode.moviesbattle.infra.database.repository.UserRepository;
import com.letscode.moviesbattle.infra.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;

    @Override
    public UserDetails findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(UserNotFound::new);
    }
}
