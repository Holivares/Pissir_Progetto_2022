import MQTT.GestioneMisureLocaliMQTT;
import RESTservice.*;
import com.google.gson.Gson;
import static spark.Spark.*;

public class Service {
    public static void main(String[] args) {
        // init
        Gson gson = new Gson();
        String baseURL = "/api/v1";

        // enable CORS
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> { // prima di qualunque richiesta (POST, GET, ...)
            response.header("Access-Control-Allow-Origin", "*"); // qualunque dominio
            response.header("Access-Control-Allow-Methods", "GET, POST"); // metodi accettati GET e POST
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

        RESTProgrammaIrrig.REST(gson,baseURL);
        RESTAziendaAgricola.REST(gson, baseURL);
        RESTAttuatore.REST(gson, baseURL);
        RESTSensore.REST(gson, baseURL);
        RESTMisura.REST(gson, baseURL);
        RESTUtente.REST(gson, baseURL);
        RESTSerra.REST(gson, baseURL);


        GestioneMisureLocaliMQTT gestioneMisureLocaliMQTT = new GestioneMisureLocaliMQTT();
        gestioneMisureLocaliMQTT.MQTTInit();
    }
}
