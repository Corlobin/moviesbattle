package com.letscode.moviesbattle.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private static final String BEARER = "Bearer";

    private String type;
    private String token;

    public static TokenDTO of(String token) {
        return TokenDTO.builder()
            .token(token)
            .type(BEARER)
            .build();
    }

}
