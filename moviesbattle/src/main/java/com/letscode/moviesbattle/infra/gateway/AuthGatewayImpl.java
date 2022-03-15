package com.letscode.moviesbattle.infra.gateway;

import com.letscode.moviesbattle.core.gateway.AuthGateway;
import com.letscode.moviesbattle.infra.config.JwtConfig;
import com.letscode.moviesbattle.infra.database.entity.User;
import com.letscode.moviesbattle.infra.database.repository.UserRepository;
import com.letscode.moviesbattle.infra.exception.UserNotFound;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthGatewayImpl implements AuthGateway {

    public static final String IRS = "IRS";
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    @Override
    public String authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return generateToken(authentication);
    }

    @Override
    public void authenticate(String token) {
        if (StringUtils.isEmpty(token)) {
            return;
        }
        validateToken(token);
        userRepository.findById(getTokenId(token))
            .ifPresentOrElse(this::setAuthentication, () -> { throw new UserNotFound(); });
    }

    private void setAuthentication(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getUserProfiles());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private long getTokenId(String token) {
        Claims body = Jwts.parser()
            .setSigningKey(jwtConfig.getSecret())
            .parseClaimsJws(token)
            .getBody();
        return Long.parseLong(body.getSubject());
    }

    private void validateToken(String token) {
        Jwts.parser()
            .setSigningKey(jwtConfig.getSecret())
            .parseClaimsJws(token);
    }

    private String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
            .setIssuer(IRS)
            .setSubject(String.valueOf(user.getId()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + Long.parseLong(jwtConfig.getExpiration())))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
            .compact();
    }
}
