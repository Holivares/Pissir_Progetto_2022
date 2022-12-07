package OperazioniDao;
import Operazioni.ProgrammaIrrig;
import Utils.DBConnect;
import spark.QueryParamsMap;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GestioneProgrammaIrrig {

    /**
     * Ottieni tutti i programmi d'irrigazione dal DB
     *
     * @return la lista dei programmi d'irrigazione, nulla se non c'è niente nella lista
     * @param queryParamsMap
     */

    public List<ProgrammaIrrig> getAllProgrammaIrrig(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, data_p , ora_inizio, ora_fine, serra_id, utente_id,  FROM programmi";

        List<ProgrammaIrrig> programs = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            ProgrammaIrrig vecchia = null;
            while(rs.next()) {
                if(vecchia == null){
                    vecchia = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("serra_id"), rs.getString("utente_id"));
                }
                else if(vecchia.getOraFine() == rs.getInt("ora_fine") && vecchia.getDate().equals(rs.getString("data_p")) && vecchia.getSerraId() == rs.getInt("serra_id") && vecchia.getUserId() == rs.getString("utente_id"))
                    vecchia = new ProgrammaIrrig(vecchia.getId(), vecchia.getDate(), vecchia.getOraInizio(), rs.getInt("ora_fine"), vecchia.getSerraId(), vecchia.getUserId());
                else {
                    programs.add(vecchia);
                    ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("serra_id"), rs.getString("utente_id"));
                    programs.add(p);
                }
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, programs);
    }

    public List<ProgrammaIrrig> getAllProgrammaIrrigUser(String utenteId, QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, serra_id, utente_id FROM programmi WHERE utente_id = ?";

        List<ProgrammaIrrig> programs = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, utenteId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"),rs.getInt("serra_id"),rs.getString("utente_id"));
                programs.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return where(queryParamsMap, programs);
    }

    private List<ProgrammaIrrig> where(QueryParamsMap queryParamsMap, List<ProgrammaIrrig> programs){
        String date = "";
        String oraInizio = "";
        String oraFine = "";
        String serraId = "";
        String userId = "";
        if (queryParamsMap.hasKey("date")) date = queryParamsMap.get("date").value().toLowerCase();
        if (queryParamsMap.hasKey("oraInizio")) oraInizio = queryParamsMap.get("oraInizio").value().toLowerCase();
        if (queryParamsMap.hasKey("oraFine")) oraFine = queryParamsMap.get("oraFine").value().toLowerCase();
        if (queryParamsMap.hasKey("serraId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();
        if (queryParamsMap.hasKey("userId")) userId = queryParamsMap.get("userId").value().toLowerCase();



        for(int i = 0; i<programs.size(); i++){
            if (!programs.get(i).getDate().toLowerCase().contains(date) || !String.valueOf(programs.get(i).getOraInizio()).contains(oraInizio) || !String.valueOf(programs.get(i).getOraFine()).contains(oraFine) || !String.valueOf(programs.get(i).getSerraId()).contains(serraId) || !String.valueOf(programs.get(i).getUserId()).contains(userId)) {
                programs.remove(i);
                i--;
            }
        }

        return programs;
    }

    public List<ProgrammaIrrig> getAllProgrammaIrrigSerra(int serraId) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, serra_id, utente_id FROM programmi WHERE serra_id = ?";

        List<ProgrammaIrrig> programs = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, serraId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String utenteId = rs.getString("utente_id");
                ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), serraId, utenteId);
                programs.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public ProgrammaIrrig getProgrammaIrrig(int id) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, serra_id, utente_id FROM programmi WHERE id = ?";

        ProgrammaIrrig programm = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                programm = new ProgrammaIrrig(id, rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("serra_id"), rs.getString("utente_id"));
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programm;
    }



    /**
     * Aggiungi una nuova operazione nel DB
     * @param newProgramma the programm to be added
     */
    public void addProgrammaIrrig(ProgrammaIrrig newProgramma) {
        final String sql = "INSERT INTO programmi (data_p, ora_inizio, ora_fine, serra_id, utente_id) VALUES (?,?,?,?,?,?)";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, newProgramma.getDate());
            st.setInt(2, newProgramma.getOraInizio());
            st.setInt(3, newProgramma.getOraFine());
            st.setInt(4, newProgramma.getSerraId());
            st.setString(5, newProgramma.getUserId());

            st.executeUpdate();

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProgrammaIrrig updateProgrammaIrrig(int id) {
        final String sql = "UPDATE programmi  WHERE id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ProgrammaIrrig programms = getProgrammaIrrig(id);

        return programms;
    }

    public void deleteProgrammaIrrig(int id) {
        final String sql = "DELETE FROM programmi WHERE id = ?";

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

    public void deleteProgrammaIrrig(String date, int oraInizio, int serraId, String userId)
    {
        final String sql = "DELETE FROM programmi WHERE data_p = ? AND ora_inizio = ? AND serra_id = ? AND utente_id = ?";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, date);
            st.setInt(2, oraInizio);
            st.setInt(3, serraId);
            st.setString(4, userId);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
