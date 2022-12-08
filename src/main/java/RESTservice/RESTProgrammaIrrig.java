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

        // get all the tasks
        get(baseURL + "/programmairrig", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            String userId = Utils.getUserId();

            List<ProgrammaIrrig> allProgrammiIrigg = new LinkedList<>();

            if (Utils.getRole().equals("agricoltori"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrig(request.queryMap());
            else if (Utils.getRole().equals("collaboratori"))
                allProgrammiIrigg = programmaIrrigDao.getAllProgrammaIrrigUser(userId, request.queryMap());
            else
                halt(401);

            return allProgrammiIrigg;

        }, gson::toJson);

        delete(baseURL + "/programmairrig/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("collaboratori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUserId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            if (request.params(":id") != null) {
                // add the task into the DB
                programmaIrrigDao.deleteProgrammaIrrig(Integer.valueOf(request.params(":id")));
                response.status(201);
            } else {
                halt(400);
            }
            return "";
        });

        put(baseURL + "/programmairrig/updateagricoltori/:id", "application/json", (request, response) -> {
            if (Utils.getRole().equals("agricoltori")) {
            } else if (Utils.getRole().equals("collaboratori") || Utils.getRole().equals("agricoltori")) {
                if (!programmaIrrigDao.getProgrammaIrrig(Integer.valueOf(request.params(":id"))).getUserId().equals(Utils.getUserId()))
                    halt(401);
            } else halt(401);

            // get the body of the HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);
            ProgrammaIrrig programmairrig = null;
            // check whether everything is in place
            if (request.params(":id") != null && addRequest != null && addRequest.containsKey("agricoltori") && String.valueOf(addRequest.get("agricoltori")).length() != 0) {
                int agricoltori = (int) Math.round((Double) addRequest.get("agricoltori"));
                if (agricoltori > 5 || agricoltori < 1)
                    halt(400);

                programmairrig = programmaIrrigDao.updateProgrammaIrrig(agricoltori, Integer.parseInt(String.valueOf(request.params(":id"))));
                // if success, prepare a suitable HTTP response code
                response.status(201);
            } else {
                halt(400);
            }

            return programmairrig;
        }, gson::toJson);

    }
}
