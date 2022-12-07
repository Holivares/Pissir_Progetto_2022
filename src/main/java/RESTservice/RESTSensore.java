package RESTservice;

import Operazioni.Sensore;
import OperazioniDao.GestioneSensore;
import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.get;

public class RESTSensore {
    public static void REST(Gson gson, String baseURL){

        GestioneSensore sensoreDao = new GestioneSensore();

        // get all the tasks
        get(baseURL + "/sensori", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all tasks from the DB
            List<Sensore> allSensori = sensoreDao.getAllSensors(request.queryMap());

            return allSensori;
        }, gson::toJson);
    }
}
