package org.mondora.qoodle;

import com.google.gson.annotations.SerializedName;

public class AuthMail extends AuthObject{

    @SerializedName("email")
    private String email;


    public AuthMail(String id_client, String id_token, String email) {
        super(id_client, id_token);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
