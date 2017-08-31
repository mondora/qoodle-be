package org.mondora.qoodle;


import com.google.gson.annotations.SerializedName;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;


@Entity
public class Qoodle extends org.mondora.qoodle.Insertable {

    @Id
    private long qoodleId ;
    @SerializedName("title")
    private String title = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("closingDate")
    private String closingDate;

    @SerializedName("qeList")
    private ArrayList<org.mondora.qoodle.QoodleElement> qeList;

    @SerializedName("voList")
    private ArrayList<org.mondora.qoodle.Vote> voList = new ArrayList<>();

    @SerializedName("backgroundImage")
    private String backgroundImage;

    public Qoodle(String title, String description, String d, ArrayList<org.mondora.qoodle.QoodleElement> qeList, String backgroundImage) {
        this.qoodleId = 0L;
        this.title = title;
        this.description = description;
        this.closingDate = d;
        this.qeList = qeList;
        this.backgroundImage = backgroundImage;
    }

    public Qoodle(String title, String description, String d ) {
        this.qoodleId = 0L;
        this.title = title;
        this.description = description;
        this.closingDate = d;
        this.qeList = new ArrayList<>();

    }



    public Qoodle(String s, long i )
    {
        this.title = s;
        this.qoodleId = i;
    }

    public Qoodle()
    {
        this.title = "default value";
        this.qoodleId = 0;
    }


    public long getQoodleId() {
        return qoodleId;
    }

    public void setQoodleId(long qoodleId) {
        this.qoodleId = qoodleId;
    }

    public ArrayList<org.mondora.qoodle.QoodleElement> getQeList() {
        return qeList;
    }


    public String getClosingDate() {
        return closingDate;
    }


    public String getTitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public long getqoodleId() {
        return qoodleId;
    }

    public void setId(long id) {
        this.qoodleId = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<org.mondora.qoodle.Vote> getVoList() {
        return voList;
    }

    public void setVoList(ArrayList<org.mondora.qoodle.Vote> voList) {
        this.voList = voList;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public void insert(String name, Datastore ds) {
        long nuovoId = this.inserisci(name, ds);
        this.setQoodleId(nuovoId);
        ds.save(this);

    }
}
