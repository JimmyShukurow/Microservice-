package io.smartir.model;

import io.smartir.entity.TokenEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String token;

    public static Token toToken(TokenEntity entity) {
        Token tokenString = new Token();

        tokenString.setToken(entity.getToken());

        return tokenString;

    }
}
