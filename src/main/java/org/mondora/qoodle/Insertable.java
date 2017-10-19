package org.mondora.qoodle;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public abstract class Insertable {

    public abstract void insert(String name, Datastore ds);

    public long inserisci(String name, Datastore ds) {
        org.mondora.qoodle.Counter newCounter;
        if ((newCounter = ds.find(org.mondora.qoodle.Counter.class).field("id").equal(name).get()) != null) {
            long nuovoId = (newCounter.getSeq()) + 1;

            Query<org.mondora.qoodle.Counter> query = ds.createQuery(org.mondora.qoodle.Counter.class).field("id").equal(name);
            UpdateOperations<org.mondora.qoodle.Counter> ops = ds.createUpdateOperations(org.mondora.qoodle.Counter.class).set("seq", nuovoId);
            ds.update(query, ops);
            return nuovoId;
        } else {
            newCounter = new org.mondora.qoodle.Counter(name, 0L);
            ds.save(newCounter);
            return 0L;
        }
    }

    public static void progressiveId(String targetId, Datastore ds) {
        final org.mondora.qoodle.Counter counter = new org.mondora.qoodle.Counter(targetId);
        ds.save(counter);
    }

}
