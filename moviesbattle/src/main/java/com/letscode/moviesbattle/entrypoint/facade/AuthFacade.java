package com.letscode.moviesbattle.entrypoint.facade;

import com.letscode.moviesbattle.core.usecase.AuthUseCase;
import com.letscode.moviesbattle.entrypoint.dto.CredentialDTO;
import com.letscode.moviesbattle.entrypoint.dto.TokenDTO;
import com.letscode.moviesbattle.entrypoint.exception.TokenNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthUseCase authUseCase;

    public TokenDTO authenticate(CredentialDTO credentialDTO) {
        return authUseCase.authenticate(credentialDTO.username(), credentialDTO.password())
            .map(TokenDTO::of)
            .orElseThrow(TokenNotFound::new);
    }
}
