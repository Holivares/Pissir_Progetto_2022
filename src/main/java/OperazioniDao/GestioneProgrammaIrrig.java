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
        final String sql = "SELECT id, data_p , ora_inizio, ora_fine, azienda_agri_id, serra_id, utente_id FROM programmi";

        List<ProgrammaIrrig> programmi = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            ProgrammaIrrig vecchia = null;
            while(rs.next()) {
                if(vecchia == null){
                    vecchia = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("azienda_agri_id"), rs.getString("serra_id"),rs.getString("utente_id"));
                }
                else if(vecchia.getOraFine() == rs.getInt("ora_fine") && vecchia.getDate().equals(rs.getString("data_p")) && vecchia.getAziendaAgricolaId() == rs.getInt("azienda_agri_id") && vecchia.getSerraId() == rs.getString("serra_id"))
                    vecchia = new ProgrammaIrrig(vecchia.getId(), vecchia.getDate(), vecchia.getOraInizio(), rs.getInt("ora_fine"), vecchia.getAziendaAgricolaId(), vecchia.getSerraId(),vecchia.getUtenteId());
                else {
                    programmi.add(vecchia);
                    ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("serra_id"), rs.getString("azienda_agri_id"),rs.getString("utente_id"));
                    programmi.add(p);
                }
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, programmi);
    }

    public List<ProgrammaIrrig> getAllProgrammaIrrigAzienda(int aziendaAgricolaId) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, serra_id, utente_id FROM programmi WHERE azienda_agri_id = ?";

        List<ProgrammaIrrig> programmi = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, aziendaAgricolaId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String serraId = rs.getString("serra_id");
                String utenteId = rs.getString("utente_id");
                ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), aziendaAgricolaId, serraId,utenteId);
                programmi.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programmi;
    }

    public List<ProgrammaIrrig> getAllProgrammaIrrigUser(String userId, QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, serra_id, utente_id FROM programmi WHERE utente_id = ?";

        List<ProgrammaIrrig> programmi = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, userId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("azienda_agri_id"),rs.getString("serra_id"),rs.getString("utente_id"));
                programmi.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return where(queryParamsMap, programmi);
    }

    private List<ProgrammaIrrig> where(QueryParamsMap queryParamsMap, List<ProgrammaIrrig> programmi){
        String date = "";
        String oraInizio = "";
        String oraFine = "";
        String aziendaAgricolaId = "";
        String serraId = "";
        String utenteId = "";
        if (queryParamsMap.hasKey("date")) date = queryParamsMap.get("date").value().toLowerCase();
        if (queryParamsMap.hasKey("oraInizio")) oraInizio = queryParamsMap.get("oraInizio").value().toLowerCase();
        if (queryParamsMap.hasKey("oraFine")) oraFine = queryParamsMap.get("oraFine").value().toLowerCase();
        if (queryParamsMap.hasKey("aziendaAgricolaId")) aziendaAgricolaId = queryParamsMap.get("aziendaAgricolaId").value().toLowerCase();
        if (queryParamsMap.hasKey("userId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();
        if (queryParamsMap.hasKey("userId")) utenteId = queryParamsMap.get("userId").value().toLowerCase();



        for(int i = 0; i<programmi.size(); i++){
            if (!programmi.get(i).getDate().toLowerCase().contains(date) || !String.valueOf(programmi.get(i).getOraInizio()).contains(oraInizio) || !String.valueOf(programmi.get(i).getOraFine()).contains(oraFine) || !String.valueOf(programmi.get(i).getAziendaAgricolaId()).contains(aziendaAgricolaId) || !String.valueOf(programmi.get(i).getSerraId()).contains(serraId)|| !String.valueOf(programmi.get(i).getUtenteId()).contains(utenteId)) {
                programmi.remove(i);
                i--;
            }
        }

        return programmi;
    }

    public List<ProgrammaIrrig> getAllProgrammaIrrigSerra(int serraId) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, azienda_agri_id, serra_id, utente_id FROM programmi WHERE serra_id = ?";

        List<ProgrammaIrrig> programmi = new LinkedList<>();

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, serraId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                String aziendaAgricolaId = rs.getString("azienda_agri_id");
                String utenteId = rs.getString("utente_id");
                ProgrammaIrrig p = new ProgrammaIrrig(rs.getInt("id"), rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), serraId, aziendaAgricolaId, utenteId);
                programmi.add(p);
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programmi;
    }

    public ProgrammaIrrig getProgrammaIrrig(int id) {
        final String sql = "SELECT id, data_p, ora_inizio, ora_fine, serra_id, azienda_agri_id, utente_id FROM programmi WHERE id = ?";

        ProgrammaIrrig programmi = null;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                programmi = new ProgrammaIrrig(id, rs.getString("data_p"), rs.getInt("ora_inizio"), rs.getInt("ora_fine"), rs.getInt("serra_id"),rs.getString("azienda_agri_id"), rs.getString("utente_id"));
            }

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programmi;
    }
    public boolean agguinto(int serraId, String date, int oraInizio) {
        final String sql = "SELECT id FROM programmi WHERE aziendaAgricola_id = ? AND data_p = ? AND ora_inizio = ?";

        boolean find = false;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setInt(1, serraId);
            st.setString(2, date);
            st.setInt(3, oraInizio);
            ResultSet rs = st.executeQuery();

            if(rs.next()) find = true;

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return find;
    }


    /**
     * Aggiungi una nuova operazione nel DB
     * @param newProgramma the programm to be added
     */
    public void addProgrammaIrrig(ProgrammaIrrig newProgramma) {
        final String sql = "INSERT INTO programmi (data_p, ora_inizio, ora_fine, azienda_agri_id, serra_id, utente_id) VALUES (?,?,?,?,?,?,?)";

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, newProgramma.getDate());
            st.setInt(2, newProgramma.getOraInizio());
            st.setInt(3, newProgramma.getOraFine());
            st.setInt(4, newProgramma.getAziendaAgricolaId());
            st.setString(5, newProgramma.getSerraId());
            st.setString(5, newProgramma.getUtenteId());

            st.executeUpdate();

            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProgrammaIrrig updateProgrammaIrrig(int id, int i) {
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

        ProgrammaIrrig programmi = getProgrammaIrrig(id);

        return programmi;
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

    public void deleteProgrammaIrrig(String date, int oraInizio, int aziendaAgricolaId, String serraId, String utenteId)
    {
        final String sql = "DELETE FROM programmi WHERE data_p = ? AND ora_inizio = ?AND collaboratori = ? AND azienda_agri_id = ? AND serra_id = ? AND utente_id" ;

        try {
            Connection co = DBConnect.getInstance().getConnection();
            PreparedStatement st = co.prepareStatement(sql);
            st.setString(1, date);
            st.setInt(2, oraInizio);
            st.setInt(3, aziendaAgricolaId);
            st.setString(4, serraId);
            st.setString(4, utenteId);

            st.executeUpdate();
            co.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
