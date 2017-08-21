package org.mondora.qoodle;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class QoodleView extends Insertable{

    @Id
    private long qoodleViewId;
    private String title;
    private String description;
    private String closingDate;
    private ArrayList<QoodleElement> ele;

    public QoodleView(String title, String description, String chiusura, ArrayList<QoodleElement> ele) {
        this.qoodleViewId = 0;
        this.title = title;
        this.description = description;
        this.closingDate = chiusura;
        this.ele = ele;
    }

    public QoodleView(long qoodleViewId, String title, String description, String closingDate, ArrayList<QoodleElement> ele) {
        this.qoodleViewId = qoodleViewId;
        this.title = title;
        this.description = description;
        this.closingDate = closingDate;
        this.ele = ele;
    }

    public QoodleView() {
        this.qoodleViewId = 0;
        this.title = "";
        this.description = "";
        this.closingDate = "";
        this.ele = new ArrayList<QoodleElement>();
    }

    



    public long getQoodleViewId() {
        return qoodleViewId;
    }

    public void setQoodleViewId(long qoodleViewId) {
        this.qoodleViewId = qoodleViewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public ArrayList<QoodleElement> getEle() {
        return ele;
    }

    public void setEle(ArrayList<QoodleElement> ele) {
        this.ele = ele;
    }

    @Override
    public void insert(String name, Datastore ds) {
        long nuovoId = this.inserisci(name, ds);
        this.setQoodleViewId(nuovoId);
        ds.save(this);

    }
}
