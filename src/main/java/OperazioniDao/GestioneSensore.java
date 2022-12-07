package OperazioniDao;

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
     * Get tutti Sensori dal DB
     * @return una lista dei sensori, o una lista vuota se nessun sensore è disponibile
     * @param queryParamsMap
     */
    public List<Sensore> getAllSensors(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descrizione, azienda_agri_id FROM sensori";

        List<Sensore> sensori = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Sensore sensore = new Sensore(rs.getInt("id"), rs.getString("descrizione"),rs.getInt("azienda_agri_id"));
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
        String aziendaAgricolaId = "";
        if(queryParamsMap.hasKey("description")) description = queryParamsMap.get("description").value().toLowerCase();
        if(queryParamsMap.hasKey("aziendaAgricolaId ")) aziendaAgricolaId  = queryParamsMap.get("aziendaAgricolaId ").value().toLowerCase();

        for(int i = 0; i<sensori.size(); i++) {
            if (!sensori.get(i).getDescription().toLowerCase().contains(description) ||
                    !String.valueOf(sensori.get(i).getAziendaAgricolaId()).contains(aziendaAgricolaId)) {

                sensori.remove(i);
                i--;
            }
        }

        return sensori;
    }

    public Sensore getSensorOfAzienda(int aziendaAgricolaId)
    {
        Sensore sensore = null;
        final String sql = "SELECT id, descrizione, azienda_agri_id FROM sensori WHERE azienda_agri_id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, aziendaAgricolaId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                sensore = new Sensore(rs.getInt("id"), rs.getString("descrizione"), aziendaAgricolaId);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensore;
    }
}
