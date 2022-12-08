package RESTservice;
import com.google.gson.Gson;
import Operazioni.Utente;
import OperazioniDao.GestioneUtente;

import java.util.List;

import static spark.Spark.*;
import static spark.Spark.halt;

public class RESTUtente {


    public static void REST(Gson gson, String baseURL) {

        GestioneUtente UserDao = new GestioneUtente();

        // get all opazioni
        get(baseURL + "/users", (request, response) -> {
            if(!Utils.getRuolo().equals("Agri")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all Operazione del DB
            List<Utente> allUsers = UserDao.getAllUsers();

            return allUsers;
        }, gson::toJson);
    }
}
