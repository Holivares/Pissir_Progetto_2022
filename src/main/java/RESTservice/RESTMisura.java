package RESTservice;

import Operazioni.Misura;
import OperazioniDao.GestioneMisura;
import com.google.gson.Gson;
import jwtToken.Utils;
import java.util.List;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTMisura {
    public static void REST(Gson gson, String baseURL){

        GestioneMisura misuraDao = new GestioneMisura();

        // Ottieni tutte le operazioni
        get(baseURL + "/misure", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            // Ottieni tutte le operazioni dal DB
            List<Misura> allMisure = misuraDao.getAllMisure(request.queryMap());

            return allMisure;
        }, gson::toJson);

        delete(baseURL + "/misure/:id", "application/json", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);

            if(request.params(":id")!=null) {
                // aggiungi un'operazione nel DB
                misuraDao.deleteMisura(Integer.parseInt(String.valueOf(request.params(":id"))));
                response.status(201);
            }
            else {
                halt(400);
            }
            return "";
        }, gson::toJson);
    }
}
