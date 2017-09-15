package org.mondora.qoodle;
import com.google.gson.annotations.SerializedName;

public class AuthObject {

    @SerializedName("id_client")
    private String id_client;

    @SerializedName("id_token")
    private String id_token;
    public AuthObject() {
        id_client = "";
        id_token = "";
    }

    public AuthObject(String id_client, String id_token) {
        this.id_client = id_client;
        this.id_token = id_token;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
