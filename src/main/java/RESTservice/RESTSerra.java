package RESTservice;

import Operazioni.Serra;
import OperazioniDao.GestioneSerra;
import com.google.gson.Gson;
import jwtToken.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTSerra {
    public static void REST(Gson gson, String baseURL) {
        GestioneSerra serraDao = new GestioneSerra();

        // Ottieni tutte le operazioni
        get(baseURL + "/serre", (request, response) -> {
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            String utenteId = Utils.getUserId();

            List<Serra> allSerre = new LinkedList<>();

            if (Utils.getRole().equals("agricoltori"))
                allSerre = serraDao.getAllSerre(request.queryMap());
            else if (Utils.getRole().equals("utente"))
                allSerre = serraDao.getAllSerraUser(utenteId, request.queryMap());
            else
                halt(401);

            return allSerre;

        }, gson::toJson);

        delete(baseURL + "/serre/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("agricoltori")) {
                if (!serraDao.getSerre(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            if (request.params(":id") != null) {
                // aggiungi un operazione nell DB
                serraDao.deleteSerra(Integer.valueOf(request.params(":id")));
                response.status(201);
            } else {
                halt(400);
            }
            return "";
        });

        put(baseURL + "/serre/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("agricoltori") || Utils.getRole().equals("agricoltori")) {
                if (!serraDao.getSerre(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            // Ottieni il corpo di HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            Serra serra = null;
            // controlla che tutto sia apposto
            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori")) {
                String agricoltori = String.valueOf(addRequest.get("agricoltori"));
                serra = serraDao.updateSerra(Integer.parseInt(agricoltori), Integer.parseInt(String.valueOf(request.params(":id"))));
                // se successo, prepara un codice di risposta HTTP
                response.status(201);
            } else {
                halt(400);
            }

            return serra;
        }, gson::toJson);

        post(baseURL + "/serraId", "application/json", (request, response) -> {
            // get the body of the HTTP request
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("agricoltori")) {
                if (!serraDao.getSerre(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            Map addRequest = gson.fromJson(request.body(), Map.class);

            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori")) {
                String agricoltori = String.valueOf(addRequest.get("agricoltori"));

                String userId = Utils.getUserId();

                String aziendaAgricolaId = String.valueOf(request.params(":aziendaAgricolaId"));
                String descrizione = String.valueOf(request.params(":descrizione"));
                String tipoColtura = String.valueOf(request.params(":tipoColtura"));

                Serra serra = new Serra(aziendaAgricolaId, descrizione, tipoColtura, userId);
                serraDao.addSerra(serra);

                response.status(201);
            }
            else {
                halt(400);
            }

            return "";
        }, gson::toJson);


    }
}
