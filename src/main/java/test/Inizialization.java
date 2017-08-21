package test;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import static spark.Spark.before;
import static spark.Spark.options;

public class Inizialization {

    private String from;
    private String how;
    private String head;

    public Inizialization(String from, String how, String head) {
        this.from = from;
        this.how = how;
        this.head = head;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }


    protected void enableCORS() {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", this.from);
            response.header("Access-Control-Request-Method", this.how);
            response.header("Access-Control-Allow-Headers", this.head);
            response.type("application/json");
        });
    }

    protected Datastore createDatastore(String pack, String dbName)
    {
        final Morphia morphia = new Morphia();
        morphia.mapPackage(pack);

        return morphia.createDatastore(new MongoClient(), "morphia_example");

    }





}
