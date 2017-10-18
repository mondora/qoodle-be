package org.mondora.qoodle.response.view;

import org.mondora.qoodle.response.utils.Json;

public class ViewResponse {

    public QoodleView view;

    public ViewResponse(String token) {
        if (token != "Invalid ID token.") {
            this.view = Json.fromJson(token, QoodleView.class);
        }
    }

    @Override
    public String toString() {
        return Json.toJson(this.view);
    }
}
