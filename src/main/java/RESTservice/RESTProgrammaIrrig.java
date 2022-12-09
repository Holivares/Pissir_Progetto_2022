package RESTservice;

import Operazioni.ProgrammaIrrig;
import OperazioniDao.GestioneProgrammaIrrig;
import Ruoli.Utils;
import com.google.gson.Gson;
import spark.QueryParamsMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTProgrammaIrrig {
    public static void REST(Gson gson, String baseURL) {
        GestioneProgrammaIrrig programmaIrrigDao = new GestioneProgrammaIrrig();

        // Ottieni tutte le operazioni
        get(baseURL + "/programmairrig", (request, response) -> {
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            String userId = Utils.getUtenteId();

            List<ProgrammaIrrig> allProgrammiIrigg = new LinkedList<>();

            if (Utils.getRuolo().equals("agricoltori"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrig(request.queryMap());
            else if (Utils.getRuolo().equals("collaboratori"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrigUser(userId, request.queryMap());
            else
                halt(401);

            return allProgrammiIrigg;

        }, gson::toJson);

        delete(baseURL + "/programmairrig/:id", "application/json", (request, response) -> {
            if (Utils.getRuolo().equals("agricoltori")) {
            } else if (Utils.getRuolo().equals("collaboratori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUserId().equals(Utils.getUtenteId()))
                    halt(401);
            } else halt(401);

            if (request.params(":id") != null) {
                // aggiungi un operazione nell DB
                programmaIrrigDao.deleteProgrammaIrrig(Integer.valueOf(request.params(":id")));
                response.status(201);
            } else {
                halt(400);
            }
            return "";
        });

        put(baseURL + "/programmairrig/updateagricoltori/:id", "application/json", (request, response) -> {
            if (Utils.getRuolo().equals("agricoltori")) {
            } else if (Utils.getRuolo().equals("collaboratori") || Utils.getRuolo().equals("agricoltori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUserId().equals(Utils.getUtenteId()))
                    halt(401);
            } else halt(401);

            // Ottieni il corpo di HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            ProgrammaIrrig programmairrig = null;
            // controlla che tutto sia apposto
            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori") && String.valueOf(addRequest.get("agricoltori")).length() != 0) {
                int agricoltori = (int) Math.round((Double) addRequest.get("agricoltori"));
                if (agricoltori > 5 || agricoltori < 1)
                    halt(400);

                programmairrig = programmaIrrigDao.updateProgrammaIrrig(agricoltori, Integer.parseInt(String.valueOf(request.params(":id"))));
                // se successo, prepara un codice di risposta HTTP
                response.status(201);
            } else {
                halt(400);
            }

            return programmairrig;
        }, gson::toJson);

    }
}
