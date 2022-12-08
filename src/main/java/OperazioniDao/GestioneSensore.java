package OperazioniDao;

import Operazioni.ProgrammaIrrig;
import Operazioni.Sensore;
import Utils.DBConnect;
import spark.QueryParamsMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneSensore {

    /**
     * ottieni tutti Sensori dal DB
     * @return una lista dei sensori, o una lista vuota se nessun sensore è disponibile
     * @param queryParamsMap
     */
    public List<Sensore> getAllSensors(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, description,tipo,serra_id FROM sensori";

        List<Sensore> sensori = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Sensore sensore = new Sensore(rs.getInt("id"), rs.getString("description"),rs.getString("tipo"),rs.getInt("serra_id"));
                sensori.add(sensore);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, sensori);
    }
    private List<Sensore> where(QueryParamsMap queryParamsMap, List<Sensore> sensori){
        String description = "";
        String type = "";
        String serraId = "";
//        String serraId = "";
        if (queryParamsMap.hasKey("date")) description = queryParamsMap.get("description").value().toLowerCase();
        if (queryParamsMap.hasKey("type")) type= queryParamsMap.get("type").value().toLowerCase();
        if (queryParamsMap.hasKey("serraId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();


        for(int i = 0; i<sensori.size(); i++){
            if (!sensori.get(i).getDescription().toLowerCase().contains(description) || !String.valueOf(sensori.get(i).getType()).contains(type) || !String.valueOf(sensori.get(i).getSerraId()).contains(serraId)) {
                sensori.remove(i);
                i--;
            }
        }

        return sensori;
    }

    public Sensore getSensorOfSerra(int serraId)
    {
        Sensore sensori = null;
        final String sql = "SELECT id, descrizione, tipo, serra_id, FROM sensori WHERE serra_id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, serraId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                sensori = new Sensore(rs.getInt("id"), rs.getString("descrizione"), rs.getString("tipo"),serraId);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensori;
    }
}
