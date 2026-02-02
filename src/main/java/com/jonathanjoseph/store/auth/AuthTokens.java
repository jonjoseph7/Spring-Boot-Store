package com.jonathanjoseph.store.auth;

import lombok.Data;

@Data
public class AuthTokens {
    private String accessToken;
    private String refreshToken;

    public AuthTokens(Jwt accessToken, Jwt refreshToken) {
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }

    public AuthTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
