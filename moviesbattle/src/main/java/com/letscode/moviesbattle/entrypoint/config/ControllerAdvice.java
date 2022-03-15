package com.letscode.moviesbattle.entrypoint.config;

import com.letscode.moviesbattle.entrypoint.dto.ErrorDTO;
import com.letscode.moviesbattle.entrypoint.exception.UserLostGame;
import com.letscode.moviesbattle.infra.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handle(UserNotFound userNotFound) {
        return ErrorDTO.builder()
            .description("Ops, usuário não encontrado na base!")
            .build();
    }

    @ExceptionHandler(UserLostGame.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDTO handle(UserLostGame userNotFound) {
        return ErrorDTO.builder()
            .description("Oopps! Você perdeu o jogo :( tente novamente")
            .build();
    }


}
