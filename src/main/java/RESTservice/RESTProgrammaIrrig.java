package RESTservice;

import Operazioni.ProgrammaIrrig;
import Operazioni.Serra;
import OperazioniDao.GestioneProgrammaIrrig;
import com.google.gson.Gson;
import jwtToken.Utils;
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
        get(baseURL + "/piani_irrigazione", (request, response) -> {
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            String utenteId = Utils.getUserId();

            List<ProgrammaIrrig> allProgrammiIrigg = new LinkedList<>();

            if (Utils.getRole().equals("agricoltori"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrig(request.queryMap());
            else if (Utils.getRole().equals("utente"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrigUser(utenteId, request.queryMap());
            else
                halt(401);

            return allProgrammiIrigg;

        }, gson::toJson);

        delete(baseURL + "/piani_irrigazione/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("utente")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
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

        put(baseURL + "/piani_irrigazione/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("agricoltori") || Utils.getRole().equals("agricoltori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            // Ottieni il corpo di HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            ProgrammaIrrig programmairrig = null;
            // controlla che tutto sia apposto
            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori")) {
                String agricoltori = String.valueOf(addRequest.get("agricoltori"));
                programmairrig = programmaIrrigDao.updateProgrammaIrrig(Integer.parseInt(agricoltori), Integer.parseInt(String.valueOf(request.params(":id"))));
                // se successo, prepara un codice di risposta HTTP
                response.status(201);
            } else {
                halt(400);
            }

            return programmairrig;
        }, gson::toJson);

        post(baseURL + "/serraId", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("agricoltori") || Utils.getRole().equals("agricoltori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUtenteId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);
            // get the body of the HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);

            // check whether everything is in place
            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori")) {
                String agricoltori = String.valueOf(addRequest.get("agricoltori"));
                String date = String.valueOf(request.params(":date"));
                int oraInizio = Integer.valueOf(request.params(":oraInizio"));
                int oraFine = Integer.valueOf(request.params(":oraFine"));
                int aziendaAgricolaId = Integer.valueOf(request.params(":aziendaAgricolaId"));
                String serraId = String.valueOf(request.params(":serraId"));
                String utenteId = Utils.getUserId();

                ProgrammaIrrig programmi = new ProgrammaIrrig(date, oraInizio, oraFine, aziendaAgricolaId ,serraId, utenteId);
                programmaIrrigDao.addProgrammaIrrig(programmi);

                // if success, prepare a suitable HTTP response code
                response.status(201);
            }
            else {
                halt(400);
            }

            return "";
        }, gson::toJson);
    }
}
