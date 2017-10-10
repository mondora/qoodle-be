package org.mondora.qoodle;

public class UserInfo {

    private String userId="";
    private String name="";
    private String email="";
    private String pictureUrl="";

    public UserInfo(String userId,String name, String email, String pictureUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
