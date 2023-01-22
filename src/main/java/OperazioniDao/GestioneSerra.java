package OperazioniDao;

import Operazioni.Serra;
import Utils.DBConnect;
import spark.QueryParamsMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneSerra {

    /**
     * Ottieni tutti i programmi d'irrigazione dal DB
     *
     * @return la lista delle serre d'irrigazione, nulla se non c'è niente nella lista
     * @param queryParamsMap
     */

    public List<Serra> getAllSerre(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, azienda_agri_id, descizione, tipo_coltura, utente_id FROM serre";

        List<Serra> serra = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Serra t = new Serra(rs.getInt("id"), rs.getString("azienda_agri_id"), rs.getString("descrizione"),rs.getString("tipo_coltura"),rs.getString("utente_id"));
                serra.add(t);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, serra);
    }

    public List<Serra> getAllSerraAzienda(int serraId) {
        final String sql = "SELECT id, azienda_agri_id, descizione, tipo_coltura, utente_id FROM serre WHERE serre_id = ?";

        List<Serra> serre = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                Serra p = new Serra(rs.getInt("id"), rs.getString("azienda_agri_id"), rs.getString("descrizione"),rs.getString("tipo_coltura"),rs.getString("utente_id"));
                serre.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serre;
    }

    public List<Serra> getAllSerraUser(String userId, QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, azienda_agri_id, descizione, tipo_coltura, utente_id FROM serre WHERE utente_id = ?";

        List<Serra> serre = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, userId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                Serra p = new Serra(rs.getInt("id"), rs.getString("azienda_agri_id"), rs.getString("descizione"),rs.getString("tipo_coltura"),rs.getString("utente_id"));
                serre.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return where(queryParamsMap, serre);
    }

    private List<Serra> where(QueryParamsMap queryParamsMap, List<Serra> serre){
        String aziendaAgricolaId = "";
        String descrizione = "";
        String tipoColtura = "";
        String utenteId ="";
        if (queryParamsMap.hasKey("aziendaAgricolaId")) aziendaAgricolaId = queryParamsMap.get("aziendaAgricolaId").value().toLowerCase();
        if (queryParamsMap.hasKey("descrizione")) descrizione = queryParamsMap.get("descrizione").value().toLowerCase();
        if (queryParamsMap.hasKey("tipoColtura")) tipoColtura = queryParamsMap.get("tipoColtura").value().toLowerCase();
        if (queryParamsMap.hasKey("utenteId")) utenteId = queryParamsMap.get("utenteId").value().toLowerCase();



        for(int i = 0; i<serre.size(); i++){
            if (!serre.get(i).aziendaAgricolaId().toLowerCase().contains(aziendaAgricolaId) || !String.valueOf(serre.get(i).getDescrizione()).contains(descrizione) || !String.valueOf(serre.get(i).getTipoColtura()).contains(tipoColtura)|| !String.valueOf(serre.get(i).getUtenteId()).contains(utenteId)) {
                serre.remove(i);
                i--;
            }
        }

        return serre;
    }


    public Serra getSerre(int id) {
        final String sql = "SELECT id, azienda_agri_id, descrizione, tipo_coltura, utente_id FROM serre WHERE id = ?";

        Serra serre = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                serre = new Serra(id, rs.getString("azienda_agri_id"), rs.getString("desrizione"), rs.getString("tipo_coltura"),rs.getString("utente_id"));
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serre;
    }


    /**
     * Aggiungi una nuova operazione nel DB
     * @param newSerra the programm to be added
     */
    public void addSerra(Serra newSerra) {
        final String sql = "INSERT INTO serre (azienda_agri_id, descrizione, tipo_coltura, utente_id) VALUES (?,?,?,?,?)";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, newSerra.aziendaAgricolaId());
            st.setString(2, newSerra.getDescrizione());
            st.setString(3, newSerra.getTipoColtura());
            st.setString(3, newSerra.getUtenteId());


            st.executeUpdate();

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Serra updateSerra(int id, int i) {
        final String sql = "UPDATE serre  WHERE id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Serra serre = getSerre(id);

        return serre;
    }

    public void deleteSerra(int id) {
        final String sql = "DELETE FROM serra WHERE id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
