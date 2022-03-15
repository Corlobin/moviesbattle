package com.letscode.moviesbattle.entrypoint.dto;

import javax.validation.constraints.NotNull;

public record CredentialDTO (@NotNull String username, @NotNull String password) {
}
