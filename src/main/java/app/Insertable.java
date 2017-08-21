package app;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;


public abstract class Insertable {


    public abstract void insert(String name, Datastore ds);

    public long inserisci(String name, Datastore ds) {
        Counter newCounter;
        if( (newCounter = ds.find(Counter.class).field("id").equal(name).get()) != null) {
            long nuovoId = (newCounter.getSeq()) + 1;


            Query<Counter> query = ds.createQuery(Counter.class).field("id").equal(name);
            UpdateOperations<Counter> ops = ds.createUpdateOperations(Counter.class).set("seq", nuovoId);
            ds.update(query, ops);
            return nuovoId;
        }
        else {
            newCounter = new Counter(name, 0L);
            ds.save(newCounter);
            return 0L;
        }
    }

    public static void progressiveId(String targetId, Datastore ds)
    {
        final Counter counter = new Counter(targetId);
        ds.save(counter);
    }

}
