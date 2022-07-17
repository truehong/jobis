package com.test.jabis.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Token {
    private String type = "BEARER";
    private String token;

    public static Token create(String token) {
        return new Token("BEARER" , token);
    }
}
