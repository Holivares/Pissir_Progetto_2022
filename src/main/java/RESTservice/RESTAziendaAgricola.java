package RESTservice;

import Operazioni.AziendaAgricola;
import OperazioniDao.GestioneAziendaAgricola;
import Ruoli.Utils;
import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.halt;

public class RESTAziendaAgricola {

    public static void REST(Gson gson, String baseURL) {

        GestioneAziendaAgricola aziendaDao = new GestioneAziendaAgricola();

        // get all the tasks
        get(baseURL + "/aziende", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all tasks from the DB
            List<AziendaAgricola> allAziendeAgricole = aziendaDao.getAllAziendeAgricole(request.queryMap());

            return allAziendeAgricole;
        }, gson::toJson);

        // get all the tasks
        get(baseURL + "/serre", (request, response) -> {
            if(!Utils.getRole().equals("collaboratoriri") && !Utils.getRole().equals("agricoltori")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all tasks from the DB
            List<AziendaAgricola> allAziendeAgricole = aziendaDao.getAllAziendeAgricole(request.queryMap());

            return allAziendeAgricole;
        }, gson::toJson);
    }
}
