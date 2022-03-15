package com.letscode.moviesbattle.entrypoint.config;

import com.letscode.moviesbattle.core.usecase.AuthUseCase;
import com.letscode.moviesbattle.entrypoint.exception.CredentialNotFound;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";

    private final AuthUseCase authUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authUseCase.authenticate(getHeader(request));
        filterChain.doFilter(request, response);
    }

    private String getHeader(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(AUTH_HEADER))
            .filter(StringUtils::isNotEmpty)
            .map(s -> s.substring(7))
            .orElse(null);
    }
}
