package OperazioniDao;


import Operazioni.AziendaAgricola;
import Utils.DBConnect;
import spark.QueryParamsMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneAziendaAgricola {

    public List<AziendaAgricola> getAllAziendeAgricole(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descizione,tipo FROM aziende";

        List<AziendaAgricola> aziende = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AziendaAgricola t = new AziendaAgricola(rs.getInt("id"), rs.getString("descrizione"),rs.getString("tipo"));
                aziende.add(t);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, aziende);
    }
    /**
     * Ottieni tutte le aziende agricole dal DB
     * @return lista delle aziende agricole, o niente se la lista è vuota
     * @param queryParamsMap
     */
    public List<AziendaAgricola> getAllSerre(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descrizione,tipo FROM aziende" ;

        List<AziendaAgricola> aziende = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                AziendaAgricola a = new AziendaAgricola(rs.getInt("id"), rs.getString("descrizione"),rs.getString("tipo"));
                aziende.add(a);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, aziende);
    }

    private List<AziendaAgricola> where(QueryParamsMap queryParamsMap, List<AziendaAgricola> aziende){
        String descrizione = "";
        String tipo ="";


        if(queryParamsMap.hasKey("descizione")) descrizione= queryParamsMap.get("descrizione").value().toLowerCase();
        if(queryParamsMap.hasKey("tipo")) descrizione= queryParamsMap.get("tipo").value().toLowerCase();

        for(int i = 0; i<aziende.size(); i++) {
            if (!aziende.get(i).getDescrizione().toLowerCase().contains(descrizione)  || !aziende.get(i).getTipo().toLowerCase().contains(tipo)) {

                aziende.remove(i);
                i--;
            }
        }

        return aziende;
    }

    public AziendaAgricola getAziendaAgricola(int id) {
        final String sql = "SELECT id, descrizione, tipo FROM aziende WHERE id = ?";

        AziendaAgricola azienda = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                azienda = new AziendaAgricola(rs.getInt("id"), rs.getString("descrizione"),rs.getString("tipo"));
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return azienda;
    }

}
