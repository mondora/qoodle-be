package org.mondora.qoodle.response.details;

import org.mondora.qoodle.response.utils.Json;

public class DetailsResponse {



    public Details details;

    public DetailsResponse(String token) {
        if (token != "Invalid ID token.") {
            this.details = Json.fromJson(token, Details.class);
        }
    }

    @Override
    public String toString() {
        return Json.toJson(this.details);
    }
}

