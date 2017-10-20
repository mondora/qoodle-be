package org.mondora.qoodle;

import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.annotations.Entity;

@Entity
public class VoteRequest extends Vote {

    @SerializedName("id")
    private long qoodleId;

    public long getQoodleId() {
        return qoodleId;
    }

    public void setQoodleId(long qoodleId) {
        this.qoodleId = qoodleId;
    }
}
