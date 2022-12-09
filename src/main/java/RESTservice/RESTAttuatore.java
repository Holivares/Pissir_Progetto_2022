package RESTservice;
import OperazioniModel.AttuaroreJs;
import Ruoli.Utils;
import com.google.gson.Gson;
import Operazioni.Attuatore;
import OperazioniDao.GestioneAttuatore;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTAttuatore {


    public static void REST(Gson gson, String baseURL){

        GestioneAttuatore attuatoreDao = new GestioneAttuatore();

        // Ottieni tutte le operazioni
        get(baseURL + "/attuatori", (request, response) -> {
            if(!Utils.getRuolo().equals("agricoltori")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // Ottieni tutte le operazioni dal DB
            List<AttuaroreJs> allAttuatori = attuatoreDao.getAllAttuatori(request.queryMap());
            // prepara il JSON relativo al return

            return allAttuatori;
        }, gson::toJson);

        put(baseURL + "/actuators/updatemanual/:id", "application/json", (request, response) -> {
            if(!Utils.getRuolo().equals("agricoltori")) halt(401);
            // Ottieni il corpo HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            Attuatore attuatore = null;
            // controlla che tutto sia apposto
            if(request.params(":id")!=null && addRequest!=null && addRequest.containsKey("manual") && String.valueOf(addRequest.get("manual")).length() != 0) {
                String manual = String.valueOf(addRequest.get("manual"));
                attuatore = attuatoreDao.updateAttuatore(manual, Integer.parseInt(String.valueOf(request.params(":id"))));
                // se da successo, prepara  HTTP response code
                response.status(201);
            }
            else {
                halt(400);
            }

            return attuatore;
        }, gson::toJson);

        put(baseURL + "/actuators/updatestate/:id", "application/json", (request, response) -> {
            if(!Utils.getRuolo().equals("agricoltori")) halt(401);
            // Ottieni il corpo di HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            Attuatore attuatore = null;
            // controlla che tutto sia apposto
            if(request.params(":id")!=null && addRequest!=null && addRequest.containsKey("stato")) {
                String stato = String.valueOf(addRequest.get("stato"));
                attuatore = attuatoreDao.updateStato(stato, Integer.parseInt(String.valueOf(request.params(":id"))));
                // se da successo, prepara  HTTP response code
                response.status(201);
            }
            else {
                halt(400);
            }

            return attuatore;
        }, gson::toJson);
    }
}


