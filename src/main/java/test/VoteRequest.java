package test;

import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;

@Entity
public class VoteRequest extends Vote{

    @SerializedName("id")
    private long qoodleId;


    public VoteRequest(long qoodleId, String userId, ArrayList<Integer> voti)
    {
        super(userId, voti);
        this.qoodleId = qoodleId;
    }


    public long getQoodleId() {
        return qoodleId;
    }

    public void setQoodleId(long qoodleId) {
        this.qoodleId = qoodleId;
    }
}
