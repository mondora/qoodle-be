package org.mondora.qoodle.response.auth;

import org.mondora.qoodle.response.utils.Json;

public class AuthResponse {

    public AuthData data;

    public AuthResponse(String token) {
        if (token != "Invalid ID token.") {
            this.data = Json.fromJson(token, AuthData.class);
        }
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }
}
