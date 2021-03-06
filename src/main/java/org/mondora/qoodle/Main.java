package org.mondora.qoodle;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import org.mondora.qoodle.response.details.Detail;
import org.mondora.qoodle.response.details.Details;
import org.mondora.qoodle.response.details.DetailsResponse;
import org.mondora.qoodle.response.list.ListResponse;
import org.mondora.qoodle.response.auth.AuthResponse;
import org.mondora.qoodle.response.list.Qoodles;
import org.mondora.qoodle.response.submit.SubmitResponse;
import org.mondora.qoodle.response.template.ElementResponse;
import org.mondora.qoodle.response.view.QoodleView;
import org.mondora.qoodle.response.view.ViewResponse;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import org.mongodb.morphia.query.UpdateOperations;
import spark.Request;
import spark.Response;

public class Main {

    private static void setResponseStatus(SubmitResponse submitResponse, Response res) {
        if (submitResponse.message.equals("OK")) {
            res.status(200);
        } else {
            res.status(401);
        }

    }

    private static String showUserToken(Gson gson, Request req) {
        AuthObject recivedObject = gson.fromJson(req.body(), AuthObject.class);

        return (recivedObject.verify(gson));
    }

    public static boolean isLoggedIn(Request req) {

        String email = req.headers("email");
        AuthObject checkIdentity = new AuthObject(req.headers("id_client"), req.headers("id_token"));

        return (checkIdentity.check(email));
    }

    private static String getList(Datastore datastore, Gson gson, Request req) {

        if (isLoggedIn(req)) {
            final List<org.mondora.qoodle.Qoodle> qoodles = datastore.createQuery(org.mondora.qoodle.Qoodle.class)
                    .retrievedFields(true, "qoodleId", "title", "description", "closingDate", "voList", "backgroundImage", "owner").asList();

            ArrayList<Qoodles> qList = new ArrayList<>();

            for (org.mondora.qoodle.Qoodle x : qoodles) {
                qList.add(new Qoodles(x.getqoodleId(), x.getTitle(), x.getDescription(), x.getVoList().size(), x.getClosingDate(), x.getBackgroundImage(), x.getOwner()));

            }

            return gson.toJson(qList);
        } else {
            return "ACCESSO VIETATO";
        }
    }

    public static String getDetails(Datastore ds, Gson gson, Request req) {

        if (isLoggedIn(req)) {

            long id = Long.parseLong(req.params(":id"));

            final Query<org.mondora.qoodle.Qoodle> primaQuery = ds.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId", "title",
                    "qeList", "voList", "type");
            final org.mondora.qoodle.Qoodle targetQoodle = primaQuery.limit(1).get();

            int nrElements = 0;
            if (targetQoodle.getQeList() != null)
                nrElements = targetQoodle.getQeList().size();
            final int nrUser = targetQoodle.getVoList().size();
            Detail[] details = new Detail[nrElements];

            for (int i = 0; i < nrElements; i++)
                details[i] = new Detail(targetQoodle.getQeList().get(i).getName());

            final ArrayList<SingleVote> allVotes = new ArrayList<>();

            for (Vote v : targetQoodle.getVoList()) {
                for (int i = 0; i < v.getVotes().size(); i++) {
                    allVotes.add(new SingleVote(v.getUserId(), v.getRealName(), v.getVotes().get(i)));
                }
            }

            // too complicated
            for (int i = 0, j = 0; i < allVotes.size(); i++, j = ((j + nrElements) % (allVotes.size()))) {
                if ((i != 0) && i % (nrUser) == 0)
                    j++;
                // System.out.println("VOTOall " + j + "va nel posto" + i % nrElements + " " +
                // allVotes.get(j));
                details[(j % nrElements)].addWho(allVotes.get(j));
            }

            Details d = new Details(targetQoodle.getTitle(), details, targetQoodle.getType());

            return gson.toJson(d);
        } else {
            return "ACCESSO VIETATO";
        }

    }

    private static String getQoodleView(Datastore datastore, Gson gson, Request req) {

        if (isLoggedIn(req)) {
            long id = Long.parseLong(req.params(":id"));

            final Query<org.mondora.qoodle.Qoodle> primaQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", id).retrievedFields(true, "qoodleId",
                    "title", "description", "closingDate", "qeList", "type");
            final org.mondora.qoodle.Qoodle targetQoodle = primaQuery.limit(1).get();

            QoodleView qView = new QoodleView(

                    targetQoodle.getQoodleId(), targetQoodle.getTitle(), targetQoodle.getDescription(), targetQoodle.getClosingDate(), targetQoodle.getQeList(),
                    targetQoodle.getType());

            if (targetQoodle.getQeList() == null)
                qView.setEle(new ArrayList<>());

            return gson.toJson(qView);
        } else {
            return "ACCESSO VIETATO";
        }
    }

    private static String submitVotes(Datastore datastore, Gson gson, Request req) {

        if (isLoggedIn(req)) {
            final org.mondora.qoodle.VoteRequest completeObject = gson.fromJson(req.body().toString(), org.mondora.qoodle.VoteRequest.class);
            final org.mondora.qoodle.Vote newVote = new org.mondora.qoodle.Vote(completeObject.getUserId(), completeObject.getRealName(), completeObject.getVotes());

            final Query<org.mondora.qoodle.Qoodle> updateQuery = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", completeObject.getQoodleId());
            final UpdateOperations<org.mondora.qoodle.Qoodle> updateQoodleVote;

            if (!updateQuery.get().getVoList().contains(newVote)) {
                ArrayList<Vote> withNewVote = updateQuery.get().getVoList();
                withNewVote.add(newVote);
                updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).set("voList", withNewVote);
            } else {// se esiste lo sostituisco
                updateQuery.get().getVoList().set(updateQuery.get().getVoList().indexOf(newVote), newVote);

                updateQoodleVote = datastore.createUpdateOperations(org.mondora.qoodle.Qoodle.class).set("voList", updateQuery.get().getVoList());

            }

            datastore.update(updateQuery, updateQoodleVote);

            return "OK";
        } else {
            return "ACCESSO VIETATO";
        }

    }

    private static String saveQoodle(String targetId, Datastore datastore, Gson gson, Request req) {

        if (isLoggedIn(req)) {
            final org.mondora.qoodle.Qoodle primoQoodle = gson.fromJson(req.body().toString(), org.mondora.qoodle.Qoodle.class);
            primoQoodle.insert(targetId, datastore);
            return "OK";

        } else {
            return "ACCESSO VIETATO";
        }

    }

    private static String deleteQoodle(Request req, Gson gson, Datastore datastore) {

        long targetId = Long.parseLong(req.params(":id"));

        AuthObject checkIdentity = new AuthObject(req.headers("id_client"), req.headers("id_token"));

        UserInfo suspectedUser = gson.fromJson(checkIdentity.verify(gson), UserInfo.class);

        if (isLoggedIn(req) && (req.headers("owner").equals(suspectedUser.getEmail()))) {

            Qoodle.delete(targetId, datastore);

            return "OK";

        } else {
            return "ACCESSO VIETATO";
        }

    }

    private static String getQoodleElements(Request req, Gson gson, Datastore datastore) {

        if (isLoggedIn(req)) {
            final org.mondora.qoodle.Qoodle templateExample = datastore.createQuery(org.mondora.qoodle.Qoodle.class).filter("qoodleId ==", 99).retrievedFields(true, "qeList")
                    .get();

            return templateExample != null ? gson.toJson(new ArrayList<>(templateExample.getQeList())) : gson.toJson(new ArrayList<QoodleElement>());

        } else {
            return "ACCESSO VIETATO";
        }
    }

    public static void main(String[] args) {
        final String from = "http://qoodle.mondora.com";
        final String how = "get";
        final String head = "*";

        org.mondora.qoodle.Inizialization init = new org.mondora.qoodle.Inizialization(from, how, head);
        init.enableCORS();

        final Datastore datastore = init.createDatastore("org.mondora.qoodle", "morphia_example");

        datastore.ensureIndexes();

        try {

            Gson gson = new Gson();

            // AUTHENTICATION
            post("/token", (req, res) -> {

                AuthResponse authResponse = new AuthResponse(showUserToken(gson, req));
                if (null != authResponse.data) {
                    res.status(200);
                } else {
                    res.status(401);
                }

                return authResponse;
            });

            // LIST -aut
            get("/qoodles", (req, res) -> {
                ListResponse listResponse = new ListResponse(getList(datastore, gson, req));
                if (null != listResponse.list) {
                    res.status(200);
                } else {
                    res.status(401);
                }
                return listResponse;
            });

            // DETAILS -aut
            get("/details/:id", (req, res) -> {
                DetailsResponse detailsResponse = new DetailsResponse(getDetails(datastore, gson, req));
                if (null != detailsResponse.details) {
                    res.status(200);
                } else {
                    res.status(401);
                }
                return detailsResponse;
            });

            // VIEW -aut

            get("/qoodle/:id", (req, res) -> // getQoodleView(gson, datastore, req));
            {
                ViewResponse viewResponse = new ViewResponse(getQoodleView(datastore, gson, req));
                if (null != viewResponse.view) {
                    res.status(200);
                } else {
                    res.status(401);
                }
                return viewResponse;
            });

            // -aut
            post("/qoodle/:id", (req, res) -> // submitVotes(datastore, gson, req));
            {
                SubmitResponse submitResponse = new SubmitResponse(submitVotes(datastore, gson, req));
                setResponseStatus(submitResponse, res);
                return submitResponse;
            });

            // CREATE -aut
            post("/qoodles", (req, res) -> {
                SubmitResponse submitResponse = new SubmitResponse(saveQoodle("qoodleId", datastore, gson, req));

                setResponseStatus(submitResponse, res);
                return submitResponse;
            });

            // DELETE
            delete("/qoodle/:id", (req, res) -> // deleteQoodle(req, gson, datastore));
            {
                SubmitResponse submitResponse = new SubmitResponse(deleteQoodle(req, gson, datastore));

                setResponseStatus(submitResponse, res);
                return submitResponse;
            });

            // -aut
            get("/create", (req, res) -> // getQoodleElements(gson, datastore, req));
            {
                ElementResponse elementResponse = new ElementResponse(getQoodleElements(req, gson, datastore));
                if (elementResponse.elementList != null) {
                    res.status(200);
                } else {
                    res.status(401);
                }
                return elementResponse;
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
