package OperazioniDao;

import Operazioni.Misura;
import Operazioni.ProgrammaIrrig;
import Operazioni.Sensore;
import OperazioniModel.SensoreJs;
import Utils.DBConnect;
import spark.QueryParamsMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneSensore {

    GestioneMisura misuraDao = new GestioneMisura();
    /**
     * Ottieni tutte le misure dal DB
     * @return una lista dei sensori, o una lista vuotà se non c'è un sensore diponibile
     * @param queryParamsMap
     */
    public List<SensoreJs> getAllSensori(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descrizione, tipo, azienda_agri_id FROM sensori";

        List<SensoreJs> sensori = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Sensore sensore = new Sensore(rs.getInt("id"), rs.getString("descrizione"), rs.getString("tipo"), rs.getInt("azienda_agri_id"));
                Misura misura = misuraDao.getUltimaMisuraSensore(sensore);
                String misurazione = "";
                try {
                    misurazione = misura.getMisurazioni();
                    if(misura.getTipo().equals("temperatura"))
                        misurazione += "°C";
                    else misurazione += "%";
                } catch(NullPointerException e) {}

                SensoreJs sensorJs = new SensoreJs(sensore.getId(), rs.getString("descrizione"), rs.getString("tipo"), misurazione, rs.getInt("azienda_agri_id"));
                sensori.add(sensorJs);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, sensori);
    }

    private List<SensoreJs> where(QueryParamsMap queryParamsMap, List<SensoreJs> sensori){
        String descrizione = "";
        String aziendaAgricolaId = "";
        if(queryParamsMap.hasKey("description")) descrizione = queryParamsMap.get("description").value().toLowerCase();
        if(queryParamsMap.hasKey("aziendaAgricolaId")) aziendaAgricolaId = queryParamsMap.get("aziendaAgricolaId").value().toLowerCase();

        for(int i = 0; i<sensori.size(); i++) {
            if (!sensori.get(i).getDescrizione().toLowerCase().contains(descrizione) ||
                    !String.valueOf(sensori.get(i).getAziendaAgricolaId()).contains(aziendaAgricolaId)) {

                sensori.remove(i);
                i--;
            }
        }

        return sensori;
    }

    public Sensore getSensoreOfAziende(int aziendaAgricolaId, String tipo)
    {
        if(tipo.equals("luminosita") ||tipo.equals("temperatura") || tipo.equals("umidita")) tipo = "luminosita,temperatura,umidita";
        Sensore sensore = null;
        final String sql = "SELECT id, descrizione, tipo, azienda_agri_id FROM sensori WHERE serra_id = ? AND tipo = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, aziendaAgricolaId);
            st.setString(2, tipo);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                sensore = new Sensore(rs.getInt("id"), rs.getString("descrizione"), tipo, aziendaAgricolaId);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensore;
    }
}
