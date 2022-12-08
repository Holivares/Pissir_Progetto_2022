package RESTservice;

import Operazioni.Sensore;
import OperazioniDao.GestioneSensore;
import OperazioniModel.SensoreJs;
import Ruoli.Utils;
import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.halt;

public class RESTSensore {
    public static void REST(Gson gson, String baseURL){

        GestioneSensore sensorDao = new GestioneSensore();

        // get all the tasks
        get(baseURL + "/sensors", (request, response) -> {
            if(!Utils.getRole().equals("agricoltori")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all tasks from the DB
            List<SensoreJs> allSensori = sensorDao.getAllSensors(request.queryMap());

            return allSensori;
        }, gson::toJson);
    }
}
