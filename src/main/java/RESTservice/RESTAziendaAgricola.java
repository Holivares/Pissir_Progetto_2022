package RESTservice;

import OperazioniDao.GestioneAziendaAgri;
import com.google.gson.Gson;

import java.util.List;

public class RESTAziendaAgricola {

    public static void REST(Gson gson, String baseURL) {

        GestioneAziendaAgricole aziendaDao = new GestioneAziendaAgricole();


        get(baseURL + "/azienda", (request, response) -> {
            if(!Utils.getRole().equals("admin")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);


            List<AziendaAgricola> allAziendaAgricola = aziendaDao.getAllAziendaAgricola(request.queryMap());

            return allAziendaAgricola;
        }, gson::toJson);


        get(baseURL + "/offices", (request, response) -> {
            if(!Utils.getnome().equals("nome")) halt(401);
            // set a proper response code and type
            response.type("application/json");
            response.status(200);


            List<RESTAziendaAgricola> allaziene = aziendeDao.getAllaziende(request.queryMap());

            return allOffices;
        }, gson::toJson);
    }
}
