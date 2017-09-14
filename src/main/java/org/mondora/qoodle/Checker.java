

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

        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier(clientId);

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
                risposta = "Invalid ID token.";
            }



        }catch (Exception e)
        {
            e.printStackTrace();
            risposta = "Invalid ID token.";
        }
        return risposta;
    }

    public static GoogleIdTokenVerifier getGoogleIdTokenVerifier(String clientId) {
        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = new GsonFactory();

        return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }


    public static boolean check(String googleId, String clientId) {

        GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier(clientId);

        boolean risposta;

        try {
            GoogleIdToken idToken = verifier.verify(googleId);

            if (idToken != null) {
                risposta =  true;


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
}
