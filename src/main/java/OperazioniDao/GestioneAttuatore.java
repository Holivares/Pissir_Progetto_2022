package OperazioniDao;
import OperazioniModel.AttuaroreJs;
import spark.QueryParamsMap;
import Operazioni.Attuatore;
import Operazioni.Misura;
import Operazioni.Sensore;
import Utils.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneAttuatore {

    GestioneSensore sensoreDao = new GestioneSensore();
    GestioneMisura misuraDao = new GestioneMisura();
    /**
     * Ottieni tutte le misure dal DB
     * @return una lista delle misure, o una lista vuotà se non c'è un sensore diponibile
     * @param queryParamsMap
     */
    public List<AttuaroreJs> getAllAttuatori(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descrizione, tipo, stato, manuale, serra_id FROM attuatori";

        List<AttuaroreJs> attuatori = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String state = rs.getString("stato");
                if(state.equals("off")) state = "Spento.";
                else if(state.equals("on")) state = "Acceso.";

                try{
                    Integer.valueOf(state); // Se non crea eccezioni, si tratta di una temperatura
                    state = "Impostato su " + state + "°C";
                } catch (NumberFormatException e) {}

                Sensore sensore = sensoreDao.getSensorOfLocal(rs.getInt("serra_id"), rs.getString("tipo"));
                Misura misura = misuraDao.getLastMeasureOfSensor(sensore);
                String misurazione = "";
                try {
                    misurazione = misura.getMisurazioni();
                    if(misura.getTipo().equals("temperatura"))
                        misurazione += "°C";
                    else misurazione += "%";
                } catch(NullPointerException e) {}

                AttuaroreJs t = new AttuaroreJs(rs.getInt("id"), rs.getString("descrizione"), rs.getString("tipo"), state, rs.getString("manuale"), misurazione, rs.getInt("serra_id"));
                attuatori.add(t);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, attuatori);
    }

    private List<AttuaroreJs> where(QueryParamsMap queryParamsMap, List<AttuaroreJs> attuatori){
        String descrizione = "";
        String manual = "";
        String serraId = "";
        if(queryParamsMap.hasKey("descrizione")) descrizione = queryParamsMap.get("descrizione").value().toLowerCase();
        if(queryParamsMap.hasKey("manual")) manual = queryParamsMap.get("manual").value().toLowerCase();
        if(queryParamsMap.hasKey("serraId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();

        for(int i = 0; i<attuatori.size(); i++) {
            if (!attuatori.get(i).getDescrizione().toLowerCase().contains(descrizione) ||
                    !String.valueOf(attuatori.get(i).getSerraId()).contains(serraId) ||
                    !attuatori.get(i).getManual().toLowerCase().contains(manual)) {

                attuatori.remove(i);
                i--;
            }
        }

        return attuatori;
    }

    public Attuatore getAttuatore(int id) {
        final String sql = "SELECT id, descrizione, tipo, stato, manuale, serra_id FROM attuatori WHERE id = ?";

        Attuatore attuatore = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                String stato = rs.getString("stato");
                if(stato.equals("off")) stato = "Spento.";
                else if(stato.equals("on")) stato = "Acceso.";
                attuatore = new Attuatore(id, rs.getString("descrizione"), stato, rs.getString("manuale"), rs.getInt("serra_id"));
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attuatore;
    }

    public Attuatore getActuatorOfLocal(int serraId, String tipo) {
        if(tipo.equals("temperatura") || tipo.equals("umidita")) tipo = "temperatura,umidita";
        final String sql = "SELECT id, descrizione, tipo, stato, manuale, serra_id FROM attuatori WHERE serra_id = ? AND tipo = ?";

        Attuatore attuatore = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, serraId);
            st.setString(2, tipo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                attuatore = new Attuatore(rs.getInt("id"), rs.getString("descrizione"),rs.getString("stato"), rs.getString("manuale"), serraId);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attuatore;
    }

    public Attuatore updateAttuatore(String manual, int id) {
        final String sql = "UPDATE attuatori SET manuale = ?, stato = 'off' WHERE id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, manual);
            st.setInt(2, id);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Attuatore attuatore = getAttuatore(id);

        return attuatore;
    }

    public Attuatore updateStato(String stato, int id) {
        final String sql = "UPDATE attuatori SET stato = ? WHERE id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, stato);
            st.setInt(2, id);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Attuatore attuatore = getAttuatore(id);

        return attuatore;
    }
}