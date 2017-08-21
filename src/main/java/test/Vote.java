package test;

import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class Vote {
    @Id
    @SerializedName("username")
    private String userId;
    @SerializedName("elements")
    private ArrayList<Integer> votes;

    public Vote(String userId, ArrayList<Integer> votes) {
        this.userId = userId;
        this.votes = votes;
    }

    public Vote() {
        this.userId = "";
        this.votes = new ArrayList<>();
        votes.add ( -1);
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

    @Override
    public String toString() {
        return "Vote{" +
                "userId='" + userId + '\'' +
                ", votes=" + votes +
                '}';
    }
}
