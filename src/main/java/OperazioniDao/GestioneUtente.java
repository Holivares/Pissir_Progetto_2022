package OperazioniDao;


import Operazioni.Utente;
import Utils.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GestioneUtente {

    /**
     * Ottieni tutti gli utenti DB
     * @return una lista di utenti , o una lista vuota se non ci sono utenti
     */
    public List<Utente> getAllUtente() {
        final String sql = "SELECT id, username, email, nome, cognome, ruolo FROM utenti";

        List<Utente> utenti = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Utente u = new Utente(rs.getString("id"), rs.getString("username"), rs.getString("email"), rs.getString("nome"), rs.getString("cognome"), rs.getString("ruolo"));
                utenti.add(u);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utenti;
    }
}