package org.mondora.qoodle.response.view;

import org.mondora.qoodle.Insertable;
import org.mondora.qoodle.QoodleElement;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class QoodleView extends Insertable {

    @Id
    private long qoodleViewId;
    private String title;
    private String description;
    private String closingDate;
    private ArrayList<QoodleElement> ele;
    private String type;

    public QoodleView(String title, String description, String chiusura, ArrayList<QoodleElement> ele) {
        this.qoodleViewId = 0;
        this.title = title;
        this.description = description;
        this.closingDate = chiusura;
        this.ele = ele;
        this.type = "generic";
    }

    public QoodleView(long qoodleViewId, String title, String description, String closingDate, ArrayList<QoodleElement> ele, String type) {
        this.qoodleViewId = qoodleViewId;
        this.title = title;
        this.description = description;
        this.closingDate = closingDate;
        this.ele = ele;
        this.type = type;
    }

    public QoodleView() {
        this.qoodleViewId = 0;
        this.title = "";
        this.description = "";
        this.closingDate = "";
        this.ele = new ArrayList<QoodleElement>();
        this.type = "generic";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void insert(String name, Datastore ds) {
        long nuovoId = this.inserisci(name, ds);
        this.setQoodleViewId(nuovoId);
        ds.save(this);

    }
}
