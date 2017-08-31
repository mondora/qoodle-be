package org.mondora.qoodle;


import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import org.mongodb.morphia.query.UpdateOperations;
import spark.Request;



public class Main {

    private static void saveQoodle(String targetId, Request req, Gson gson, Datastore datastore) {

        final org.mondora.qoodle.Qoodle primoQoodle = gson.fromJson(req.body().toString(), org.mondora.qoodle.Qoodle.class);

        primoQoodle.insert(targetId, datastore);
    }

    private static String getList(Datastore datastore, Gson gson) {
        final Query<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).retrievedFields(true, "qoodleId","title", "description","closingDate", "voList", "backgroundImage");
        final List<org.mondora.qoodle.Qoodle> sal = primaQuery.asList();


        ArrayList<org.mondora.qoodle.Qoodles> qList = new ArrayList<>();

        for ( org.mondora.qoodle.Qoodle x : sal)
        {
            qList.add(
            new org.mondora.qoodle.Qoodles
            (x.getqoodleId(),
            x.getTitle(),
            x.getDescription() ,
            x.getVoList().size(),
            x.getClosingDate(),
            x.getBackgroundImage())
            );


        }

        return gson.toJson(qList);
    }

    private static String getQoodleView(Gson gson,Datastore datastore, Request req) {
        long id = Long.parseLong( req.params(":id"));

        final Query<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId","title", "description","closingDate", "qeList");
        final org.mondora.qoodle.Qoodle targetQoodle = primaQuery.limit(1).get();


        org.mondora.qoodle.QoodleView qView =
        new org.mondora.qoodle.QoodleView(

        targetQoodle.getQoodleId(),
        targetQoodle.getTitle(),
        targetQoodle.getDescription() ,
        targetQoodle.getClosingDate(),
        targetQoodle.getQeList()
                );

        return gson.toJson(qView);
    }

    private static String getQoodleElements( Gson gson, Datastore datastore) {
        final List<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).retrievedFields(true, "qeList").asList();
        final ArrayList<org.mondora.qoodle.QoodleElement> templateExample;
        if (primaQuery.size() > 0) {
            templateExample = primaQuery.get(0).getQeList();
            return gson.toJson(templateExample);
        }
        else
        {
                return "{[]}";
            }
    }




    private static Object submitVotes(Datastore datastore, Gson gson, Request req) {
        final org.mondora.qoodle.VoteRequest completeObject = gson.fromJson(req.body().toString(), org.mondora.qoodle.VoteRequest.class);
        final org.mondora.qoodle.Vote newVote = new org.mondora.qoodle.Vote(completeObject.getUserId(), completeObject.getVotes());


        final Query<org.mondora.qoodle.Qoodle> updateQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", completeObject.getQoodleId());
        final UpdateOperations<org.mondora.qoodle.Qoodle> updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).add("voList", newVote);

        datastore.update(updateQuery, updateQoodleVote);

        return req.body();
    }

    public static void main(String[] args) {
        final String from= "http://54.77.36.67:3000";
        final String how= "get";
        final String head= "*";


        org.mondora.qoodle.Inizialization init = new org.mondora.qoodle.Inizialization(from, how, head);
        init.enableCORS();

        final Datastore datastore = init.createDatastore("org.mondora.qoodle", "qoodledb");

        datastore.ensureIndexes();


        try{



            Gson gson = new Gson();





            //LIST
            get("/qoodles", (req, res) ->getList(datastore, gson));



            //VIEW

            get("/qoodle/:id", (req, res) ->   getQoodleView(gson, datastore, req) );

            post("/qoodle/:id", (req, res) ->       submitVotes(datastore, gson, req) );


            //CREATE
            post("/qoodles", (req, res) ->
            {
                saveQoodle("qoodleId", req, gson, datastore);
                return  req.body();
            });

            get("/create", (req, res) -> getQoodleElements(gson, datastore));


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }

}
