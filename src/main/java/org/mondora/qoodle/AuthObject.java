package org.mondora.qoodle;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;


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


    public static GoogleIdTokenVerifier getGoogleIdTokenVerifier(String clientId) {
        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = new GsonFactory();

        return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }


    public String verify(Gson gson) {


        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier(this.getId_client());

        String risposta = "";


        try {
            GoogleIdToken idToken = verifier.verify(this.getId_token());



            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                String email = payload.getEmail();
                String pictureUrl = (String) payload.get("picture");
                String name = (String) payload.get("name");


                UserInfo user = new UserInfo(userId, name, email, pictureUrl);

                risposta = gson.toJson(user);


            } else {
                risposta = "Invalid ID token.";
            }



        }catch (Exception e)
        {
            e.printStackTrace();
            risposta = "Invalid ID token.";
        }
        return risposta;
    }


    public boolean check(String email) {

        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier(this.id_client);

        boolean risposta;



        try {
            GoogleIdToken idToken = verifier.verify(this.id_token);

            if (idToken != null && ( email.contains("carlo.m.porelli@gm") || email.contains("@mondora.com")  || email.contains("42@") )) {
                risposta =  true;
                //System.out.println("email autorizzata");


            } else {
                risposta = false;
            }



        }catch (Exception e)
        {
            e.printStackTrace();
            risposta = false;
        }

        return risposta;
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
