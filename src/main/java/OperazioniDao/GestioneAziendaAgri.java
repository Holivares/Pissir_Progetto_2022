package OperazioniDao;

import Operazioni.AziendaAgri;

public class GestioneAziendaAgri {

    public List<AziendaAgri> getAllAziendaAgri(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, nome,FROM aziende";

        List<AziendaAgri> aziende = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AziendaAgri p = new AziendaAgri(rs.getInt("id"), rs.getString("nome"));
                aziende.add(p);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, aziende);
    }
    /**
     * Get all offices from the DB
     * @return a list of office, or an empty list if no offices are available
     * @param queryParamsMap
     */
    public List<AziendaAgri> getAllAziendeAgri(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, nome, FROM aziende WHERE tipo = 'Serre'";

        List<AziendaAgri> aziende = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                AziendaAgri t = new AziendaAgri(rs.getInt("id"), rs.getString("nome"));
                aziende.add(t);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, aziende);
    }

    private List<AziendaAgri> where(QueryParamsMap queryParamsMap, List<AziendaAgri> aziende){
        String nome = "";
        if(queryParamsMap.hasKey("nome")) nome = queryParamsMap.get("nome").value().toLowerCase();


        for(int i = 0; i<aziende.size(); i++) {
            if (!aziende.get(i).getAziendaAgri().toLowerCase().contains("nome")) {

                locals.remove(i);
                i--;
            }
        }

        return locals;
    }

    public Locale getLocal(int id) {
        final String sql = "SELECT id, descrizione, tipo, num_posti FROM locali WHERE id = ?";

        Locale local = null;

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                local = new Locale(rs.getInt("id"), rs.getString("descrizione"), rs.getString("tipo"), rs.getInt("num_posti"));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return local;
    }
}
