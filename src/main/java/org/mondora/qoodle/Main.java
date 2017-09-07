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


        final Query<org.mondora.qoodle.Qoodle> testQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", completeObject.getQoodleId());
        ArrayList<Vote> listaVoti = testQuery.limit(1).get().getVoList();

        System.out.println(listaVoti.contains(newVote)? "voto già esistente": "voto non ancora effettuato");


        System.out.println(newVote.equals(new Vote("asciugamano42@gmail.com", new ArrayList<Integer>() )) );


        final UpdateOperations<org.mondora.qoodle.Qoodle> updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).add("voList", newVote);

        datastore.update(updateQuery, updateQoodleVote);

        return req.body();
    }



    private static String showUserToken(Request req)
    {
        Gson gson = new Gson();

        org.mondora.qoodle.AuthObject recivedObject = gson.fromJson(req.body(), org.mondora.qoodle.AuthObject.class);

        String googleId = recivedObject.getId_token();
        String clientId = recivedObject.getId_client();

        System.out.println("clientId  è : " + recivedObject.getId_client());




        return (Checker.verify(googleId, clientId, gson));
    }




    public static String getDetails(Datastore ds , Gson gson, Request req)
    {

        long id = Long.parseLong( req.params(":id"));

        final Query<org.mondora.qoodle.Qoodle> primaQuery = ds.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId","title", "qeList", "voList");
        final org.mondora.qoodle.Qoodle targetQoodle = primaQuery.limit(1).get();


        System.out.println(targetQoodle );


     /*   final String [] nomi = new String [targetQoodle.getQeList().size()] ;

        for ( int i = 0 ; i < targetQoodle.getQeList().size(); i++)
            nomi[i] = targetQoodle.getQeList().get(i).getName();

        System.out.println(nomi[0] + nomi[1]);

*/





        final int nrElements = targetQoodle.getQeList().size();


        Detail [] details = new Detail [nrElements] ;

        for ( int i = 0 ; i < nrElements; i++)
            details[i] = new Detail( targetQoodle.getQeList().get(i).getName());



        for(Vote v : targetQoodle.getVoList())
            System.out.println(v);



        final ArrayList<SingleVote> allVotes = new ArrayList<>() ;

        for( Vote v : targetQoodle.getVoList()) {
            for (int i = 0; i < v.getVotes().size(); i++) {
                allVotes.add(new SingleVote(v.getUserId(), v.getVotes().get(i)));
                //System.out.println(v);
            }
        }

        //for(int i = 0 ; i < allVotes.size(); i++ )
           // System.out.println(allVotes.get(i));

       // System.out.println(allVotes.size());


       // for(int i = allVotes.size(), j = 0 ; i > 0 ; i--, j = ( ( j + nrElements ) % ( allVotes.size() -1 ) ))
        //{
        //    details[j].addWho(allVotes.get(j));
       // }


      //  for(int i = 0 ; i < details.length; i++ )
        //    System.out.println(details[i].getWhat() + details[i].getWhos());



    return "ciao";

    }




    public static void main(String[] args) {
        final String from= "http://54.77.36.67:3000";
        final String how= "get";
        final String head= "*";


        org.mondora.qoodle.Inizialization init = new org.mondora.qoodle.Inizialization(from, how, head);
        init.enableCORS();

        final Datastore datastore = init.createDatastore("org.mondora.qoodle", "morphia_example");

        datastore.ensureIndexes();


        try{




            Gson gson = new Gson();




            //AUTHENTICATION
            post("/token", (req, res) -> showUserToken(req));


            //LIST
            get("/qoodles", (req, res) ->getList(datastore, gson));



            //DETAILS
            get("/details/:id", (req, res) -> getDetails(datastore, gson, req) );

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
