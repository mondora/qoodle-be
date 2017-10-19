package org.mondora.qoodle;

import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class Vote {
    @Id
    @SerializedName("username")
    private String userId;
    @SerializedName("realName")
    private String realName;

    @SerializedName("elements")
    private ArrayList<Integer> votes;

    public Vote(String userId, ArrayList<Integer> votes) {
        this.userId = userId;
        this.votes = votes;
        this.realName = "";
    }

    public Vote(String userId, String realName, ArrayList<Integer> votes) {
        this.userId = userId;
        this.votes = votes;
        this.realName = realName;
    }

    public Vote() {
        this.userId = "";
        this.votes = new ArrayList<>();
        votes.add(-1);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Integer> votes) {
        this.votes = votes;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "Vote{" + "userId='" + userId + '\'' + ", votes=" + votes + ", realName='" + realName + '\'' + '}';
    }

    @Override // ok
    public boolean equals(Object o) {
        boolean test = false;
        if (o instanceof Vote) {
            Vote v = (Vote) o;
            test = (v.getUserId().equals(this.getUserId()) && v.getRealName().equals(this.getRealName()));
        }
        return test;
    }
}
