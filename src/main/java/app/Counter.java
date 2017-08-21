package app;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Counter {

    @Id
    private String id;
    private long seq;

    public Counter() {
        this.id = "";
        this.seq = 0;
    }

    public Counter(String id) {
        this.id = id;
        this.seq = 0;
    }

    public Counter(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }



}

