package RESTservice;
import com.google.gson.Gson;
import Operazioni.Utente;
import OperazioniDao.GestioneUtente;
import jwtToken.Utils;

import java.util.List;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTUtente {


    public static void REST(Gson gson, String baseURL) {

        GestioneUtente UtentiDao = new GestioneUtente();

        // Ottieni tutte le operazioni
        get(baseURL + "/utenti", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);
            // impostare un codice e un tipo di risposta appropriati
            response.type("application/json");
            response.status(200);

            // Ottieni tutte le operazioni dal DB
            List<Utente> allUtenti = UtentiDao.getAllUtente();

            return allUtenti;
        }, gson::toJson);
    }
}
