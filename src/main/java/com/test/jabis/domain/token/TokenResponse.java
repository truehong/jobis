package com.test.jabis.domain.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class TokenResponse {
    private final String token;
    private final String type = "BEARER";

    private TokenResponse(String token) {
        this.token = token;
    }

    public static TokenResponse create(String token) {
        return new TokenResponse(token);
    }
}
