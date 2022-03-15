package com.letscode.moviesbattle.entrypoint.controller;

import com.letscode.moviesbattle.entrypoint.dto.CredentialDTO;
import com.letscode.moviesbattle.entrypoint.dto.TokenDTO;
import com.letscode.moviesbattle.entrypoint.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping
    public TokenDTO authenticate(@RequestBody @Validated CredentialDTO credentialDTO) {
        return authFacade.authenticate(credentialDTO);
    }
}
