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

        // Ottieni tutte le operazioni
        get(baseURL + "/aziende", (request, response) -> {
            if(!Utils.getRuolo().equals("agricoltori")) halt(401);
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            // Ottieni tutte le operazioni dal DB
            List<AziendaAgricola> allAziendeAgricole = aziendaDao.getAllAziendeAgricole(request.queryMap());

            return allAziendeAgricole;
        }, gson::toJson);

        // Ottieni tutte le operazioni
        get(baseURL + "/serre", (request, response) -> {
            if(!Utils.getRuolo().equals("collaboratoriri") && !Utils.getRuolo().equals("agricoltori")) halt(401);
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            //Ottieni tutte le operazioni dal DB
            List<AziendaAgricola> allAziendeAgricole = aziendaDao.getAllAziendeAgricole(request.queryMap());

            return allAziendeAgricole;
        }, gson::toJson);
    }
}
