package OperazioniDao;
import Operazioni.Programma;
import Utils.DBConnect;
import spark.QueryParamsMap;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

public class GestioneProgramma {

    /**
     * Ottieni tutti i programmi d'irrigazione dal DB
     * @return la lista dei programmi d'irrigazione, nulla se non c'è niente nella lista
     * @param queryParamsMap
     */

    public List<Programma> getAllProgramma(QueryParamsMap queryParamsMap){
        final String sql = "SELECT id, data_p , ora_inizio, ora_fine, azienda_agri_id, utente_id FROM programmi";
        List<Programma> programs = new LinkedList<>();

        try{
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Programma p = new Programma(rs.getInt("id"), rs.getString("data_p"),rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("azienda_agri_id"), rs.getString("utente_id"));
                programs.add(p);
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  where(queryParamsMap, programs);
    }

    public List<Programma> getAllReservationsUser(String utenteId, QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, utente_id FROM programmi WHERE utente_id = ?";

        List<Programma> programs = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, utenteId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                Programma p = new Programma(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"),rs.getInt("azienda_agri_id"),rs.getString("utente_id"));
                programs.add(p);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return where(queryParamsMap, programs);
    }

    private List<Programma> where(QueryParamsMap queryParamsMap, List<Programma> programs){
        String date = "";
        String oraInizio = "";
        String oraFine = "";
        String aziendaAgricolaId = "";
        String userId = "";
        if (queryParamsMap.hasKey("date")) date = queryParamsMap.get("date").value().toLowerCase();
        if (queryParamsMap.hasKey("oraInizio")) oraInizio = queryParamsMap.get("oraInizio").value().toLowerCase();
        if (queryParamsMap.hasKey("oraFine")) oraFine = queryParamsMap.get("oraFine").value().toLowerCase();
        if (queryParamsMap.hasKey("aziendaAgricolaId")) aziendaAgricolaId = queryParamsMap.get("aziendaAgricolaId").value().toLowerCase();
        if (queryParamsMap.hasKey("userId")) userId = queryParamsMap.get("userId").value().toLowerCase();

        for(int i = 0; i<programs.size(); i++){
            if (!programs.get(i).getDate().toLowerCase().contains(date) || !String.valueOf(programs.get(i).getOraInizio()).contains(oraInizio) || !String.valueOf(programs.get(i).getOraFine()).contains(oraFine) || !String.valueOf(programs.get(i).getAziendaAgricolaId()).contains(aziendaAgricolaId) || !String.valueOf(programs.get(i).getUserId()).contains(userId)) {
                programs.remove(i);
                i--;
            }
        }

        return programs;
    }

    public List<Programma> getAllProgrammaIrrig(int aziendaAgricolaId) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, utente_id FROM programmi WHERE azienda_agri_id = ?";

        List<Programma> programs = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, aziendaAgricolaId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String utenteId = rs.getString("utente_id");
                Programma p = new Programma(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("aziendaAgricolaIdId"), rs.getString("utenteId"));
                programs.add(p);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public Programma getProgrammaIrrig(int id) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, utente_id FROM programmi WHERE id = ?";

        Programma programm = null;

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                programm = new Programma(id, rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("azienda_agri_id"), rs.getString("utente_id"));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programm;
    }



    /**
     * Aggiungi una nuova operazione nel DB
     * @param newProgramma the programm to be added
     */
    public void addProgrammaIrrig(Programma newProgramma) {
        final String sql = "INSERT INTO programmi (data_p, ora_inizio, ora_fine, azienda_gri_id, utente_id) VALUES (?,?,?,?,?,?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, newProgramma.getDate());
            st.setInt(2, newProgramma.getOraInizio());
            st.setInt(3, newProgramma.getOraFine());
            st.setInt(4, newProgramma.getAziendaAgricolaId());
            st.setString(5, newProgramma.getUserId());

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Programma updateProgrammaIrrig(int id) {
        final String sql = "UPDATE programmi  WHERE id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Programma programms = getProgrammaIrrig(id);

        return programms;
    }

    public void deleteProgrammaIrrig(int id) {
        final String sql = "DELETE FROM programmi WHERE id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProgrammaIrrig(String date, int oraInizio, int aziendaAgricolaId, String userId)
    {
        final String sql = "DELETE FROM programmi WHERE data_p = ? AND ora_inizio = ? AND azienda_agri_id = ? AND utente_id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, date);
            st.setInt(2, oraInizio);
            st.setInt(3, aziendaAgricolaId);
            st.setString(4, userId);

            st.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
