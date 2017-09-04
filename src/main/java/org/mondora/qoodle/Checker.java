

package org.mondora.qoodle;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;




import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;


public class Checker {


    public Checker() {

    }


    public static String verify(String googleId, String clientId) {

        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();


        String risposta = "";



        try {
            GoogleIdToken idToken = verifier.verify(googleId);




            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");


                UserInfo user = new UserInfo(givenName, email, pictureUrl);

                Gson gson = new Gson();
                risposta = gson.toJson(user);


            } else {
                System.out.println("Invalid ID token.");
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return risposta;
    }

}
