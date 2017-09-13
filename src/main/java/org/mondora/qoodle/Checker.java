

package org.mondora.qoodle;


import java.util.Collections;





import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;


public class Checker {


    public Checker() {

    }


    public static String verify(String googleId, String clientId, Gson gson) {

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

                String userId = payload.getSubject();
                String email = payload.getEmail();
                String pictureUrl = (String) payload.get("picture");
                String name = (String) payload.get("name");

                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());


                UserInfo user = new UserInfo(userId, name, email, pictureUrl);

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
