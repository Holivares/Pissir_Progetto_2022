package RESTservice;

import Operazioni.Sensore;
import OperazioniDao.GestioneSensore;
import OperazioniModel.SensoreJs;
import com.google.gson.Gson;
import jwtToken.Utils;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.halt;

public class RESTSensore {
    public static void REST(Gson gson, String baseURL){

        GestioneSensore sensoreDao = new GestioneSensore();

        // Ottieni tutte le operazioni
        get(baseURL + "/sensori", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            // Ottieni tutte le operazioni  DB
            List<SensoreJs> allSensori = sensoreDao.getAllSensori(request.queryMap());

            return allSensori;
        }, gson::toJson);
    }
}
