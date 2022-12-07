package RESTservice;

import Operazioni.ProgrammaIrrig;
import OperazioniDao.GestioneProgrammaIrrig;
import com.google.gson.Gson;
import spark.QueryParamsMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTProgrammaIrrig {
    public static void REST(Gson gson, String baseURL){
        GestioneProgrammaIrrig programmaDao = new GestioneProgrammaIrrig();

        // get all operazioni
        get(baseURL + "/programmi", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all operazioni dal DB
            List<ProgrammaIrrig> allPrograms = programmaDao.getAllProgrammaIrrig(request.queryMap());
            // prepare the JSON-related structure to return
            Map<String, List<ProgrammaIrrig>> finalJson = new HashMap<>();
            finalJson.put("programmi", allPrograms);

            return allPrograms;
        }, gson::toJson);

        delete(baseURL + "/programmi/:id", "application/json", (request, response) -> {
            if(request.params(":id")!=null) {
                // add the task into the DB
                programmaDao.deleteProgrammaIrrig(Integer.valueOf(request.params(":id")));
                response.status(201);
            }
            else {
                halt(403);
            }
            return "";
        });

        post(baseURL + "/AziendaAgricolaId/:utenti", "application/json", (request, response) -> {
            // get the body of the HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);

            // check whether everything is in place
            if(addRequest!=null && addRequest.containsKey("schedule") && addRequest.containsKey("date")) {
                String schedule = String.valueOf(addRequest.get("schedule"));
                String date = String.valueOf(addRequest.get("date"));

                int i = schedule.indexOf(':');
                int oraInizio = Integer.valueOf(schedule.substring(0, i));
                int oraFine = Integer.valueOf(schedule.substring(i+4, schedule.length()-3));
                int aziendaAgricolaId = Integer.valueOf(request.params(":aziendaAgricolaId"));
                String serraId = String.valueOf(request.params(":serraId"));

                ProgrammaIrrig program = new ProgrammaIrrig(date, oraInizio, oraFine, aziendaAgricolaId, serraId);
                programmaDao.addProgrammaIrrig(program);

                // if success, prepare a suitable HTTP response code
                response.status(201);
            }
            else {
                halt(403);
            }

            return "";
        }, gson::toJson);
    }
}
