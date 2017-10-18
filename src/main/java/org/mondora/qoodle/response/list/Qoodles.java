package org.mondora.qoodle.response.list;
import com.google.gson.annotations.SerializedName;
import org.mondora.qoodle.Insertable;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


@Entity
public class Qoodles extends Insertable {


    @Id
    private long qoodlesId ;
    private String title;

    private String description;

    private int partecipants;
    private String closingDate;
    @SerializedName("backgroundImage")
    private String backgroundImage;

    private String owner;

    public Qoodles() {

        this.qoodlesId = 0L;
        this.title = "defTitle";
        this.description = "defDescription";
        this.partecipants = 0;
        this.closingDate = "";
        this.backgroundImage = "";

    }

    public Qoodles( String title, String description, int partecipants, String closingDate, String backgroundImage) {
        this.qoodlesId = 0;
        this.title = title;
        this.description = description;
        this.partecipants = partecipants;
        this.closingDate = closingDate;
        this.backgroundImage = backgroundImage;
    }




    public Qoodles(long qoodlesId, String title, String description, int partecipants, String closingDate, String backgroundImage, String owner) {
        this.qoodlesId = qoodlesId;
        this.title = title;
        this.description = description;
        this.partecipants = partecipants;
        this.closingDate = closingDate;
        this.backgroundImage = backgroundImage;
        this.owner = owner;
    }



    public long getQoodlesId() {
        return qoodlesId;
    }

    public void setQoodlesId(long qoodlesId) {
        this.qoodlesId = qoodlesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getdescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(int partecipants) {
        this.partecipants = partecipants;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Qoodles{" +
                "qoodlesId=" + qoodlesId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", partecipants=" + partecipants +
                ", closingDate='" + closingDate + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public void insert( String name, Datastore ds) {
        long nuovoId = inserisci(name, ds);
            this.setQoodlesId(nuovoId);
            ds.save(this);

    }
}
