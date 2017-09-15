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

    private static String getQoodleElements( Gson gson, Datastore datastore) {
        final List<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).retrievedFields(true, "qeList").asList();
        final ArrayList<org.mondora.qoodle.QoodleElement> templateExample;
        try{
            templateExample = primaQuery.get(99).getQeList();
            return gson.toJson(templateExample);
        }
        catch( Exception e)
        {
                return "{[]}";
            }
    }




    private static Object submitVotes(Datastore datastore, Gson gson, Request req) {
        final org.mondora.qoodle.VoteRequest completeObject = gson.fromJson(req.body().toString(), org.mondora.qoodle.VoteRequest.class);
        final org.mondora.qoodle.Vote newVote = new org.mondora.qoodle.Vote(completeObject.getUserId(), completeObject.getRealName(), completeObject.getVotes());

        final Query<org.mondora.qoodle.Qoodle> updateQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", completeObject.getQoodleId());
        final UpdateOperations<org.mondora.qoodle.Qoodle> updateQoodleVote;


        if(!updateQuery.get().getVoList().contains(newVote)) {
            updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).add("voList", newVote);
        }
        else {//se esiste lo sostituisco
            updateQuery.get().getVoList().set(updateQuery.get().getVoList().indexOf(newVote), newVote);
            updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).set("voList", newVote);
        }
        datastore.update(updateQuery, updateQoodleVote);

        return req.body();
    }



    private static String showUserToken(Gson gson, Request req)
    {

        org.mondora.qoodle.AuthObject recivedObject = gson.fromJson(req.body(), org.mondora.qoodle.AuthObject.class);

        String googleId = recivedObject.getId_token();
        String clientId = recivedObject.getId_client();


        return (Checker.verify(googleId, clientId, gson));
    }




    public static String getDetails(Datastore ds , Gson gson, Request req)
    {

        if(isLoggedIn(gson, req)) {

            long id = Long.parseLong(req.params(":id"));

            final Query<org.mondora.qoodle.Qoodle> primaQuery = ds.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId", "title", "qeList", "voList");
            final org.mondora.qoodle.Qoodle targetQoodle = primaQuery.limit(1).get();


            final int nrElements = targetQoodle.getQeList().size();
            final int nrUser = targetQoodle.getVoList().size();
            Detail[] details = new Detail[nrElements];

            for (int i = 0; i < nrElements; i++)
                details[i] = new Detail(targetQoodle.getQeList().get(i).getName());


            final ArrayList<SingleVote> allVotes = new ArrayList<>();

            //toocomplicated
            for (Vote v : targetQoodle.getVoList()) {
                for (int i = 0; i < v.getVotes().size(); i++) {
                    allVotes.add(new SingleVote(v.getUserId(), v.getRealName(), v.getVotes().get(i)));
                }
            }


            //too complicated
            for (int i = 0, j = 0; i < allVotes.size(); i++, j = ((j + nrElements) % (allVotes.size()))) {
                if ((i != 0) && i % (nrUser) == 0) j++;
                // System.out.println("VOTOall  " + j + "va nel posto" + i % nrElements +  "  " + allVotes.get(j));
                details[(j % nrElements)].addWho(allVotes.get(j));
            }

            Details d = new Details(targetQoodle.getTitle(), details);

            return gson.toJson(d);
        }else
        {
            return "ACCESSO VIETATO";
        }

    }


    public static boolean isLoggedIn(Gson gs, Request req)
    {

   //     System.out.println("QOESTA È LA REQUEWST la mail" + req.headers("email") + req.headers("id_token")  + "   " + req.headers("id_client"));

        String googleId = req.headers("id_token");
        String clientId = req.headers("id_client");
        String email = req.headers("email");


        return (Checker.check(googleId, clientId, email));
    }





    private static String getList(Datastore datastore, Gson gson, Request req) {

        System.out.println(isLoggedIn(gson, req));

        if(isLoggedIn(gson, req)) {
            final Query<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).retrievedFields(true, "qoodleId", "title", "description", "closingDate", "voList", "backgroundImage");
            final List<org.mondora.qoodle.Qoodle> sal = primaQuery.asList();


            ArrayList<org.mondora.qoodle.Qoodles> qList = new ArrayList<>();

            for (org.mondora.qoodle.Qoodle x : sal) {
                qList.add(
                        new org.mondora.qoodle.Qoodles
                                (x.getqoodleId(),
                                        x.getTitle(),
                                        x.getDescription(),
                                        x.getVoList().size(),
                                        x.getClosingDate(),
                                        x.getBackgroundImage())
                );


            }

            return gson.toJson(qList);
        }
        else
        {
            return "ACCESSO VIETATO";
        }
    }

    private static String getQoodleView(Gson gson,Datastore datastore, Request req) {

        if(isLoggedIn(gson, req)) {
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
        else
        {
            return "ACCESSO VIETATO";
        }
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

            //LIST -aut
            get("/qoodles", (req, res) ->getList(datastore, gson, req));




            //DETAILS -aut
            get("/details/:id", (req, res) -> getDetails(datastore, gson, req) );


            //AUTHENTICATION
            post("/token", (req, res) -> showUserToken(gson, req));




            //VIEW -aut

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
