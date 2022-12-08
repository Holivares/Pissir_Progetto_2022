package OperazioniDao;

import Operazioni.Misura;
import Operazioni.Sensore;
import Utils.DBConnect;
import spark.QueryParamsMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GestioneMisura {

    public List<Misura> getAllMisuri(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, tipo, misurazione, data, sensore_id, serra_id FROM misure";
        List<Misura> misuri = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo").substring(0, 1).toUpperCase() + rs.getString("tipo").substring(1) + '.';
                String misurazioni= rs.getString("misurazione");
                if(tipo.equals("Temperatura."))
                    misurazioni += "°C";
                else {
                    if(tipo.equals("Umidita."))
                        tipo = "Umidità.";
                    else if(tipo.equals("Luminosita."))
                        tipo = "Luminosità.";
                    misurazioni+= "%";
                }

                Misura t = new Misura(rs.getInt("id"), tipo, misurazioni, rs.getString("data"), rs.getInt("sensore_id"), rs.getInt("serra_id"));
                misuri.add(t);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return where(queryParamsMap, misuri);
    }

    private List<Misura> where(QueryParamsMap queryParamsMap, List<Misura> misuri){
        String tipo= "";
        String misurazioni = "";
        String dateTime = "";
        String serraId = "";
        if(queryParamsMap.hasKey("tipo")) tipo = queryParamsMap.get("tipo").value().toLowerCase();
        if(queryParamsMap.hasKey("misurazioni")) misurazioni = queryParamsMap.get("misurazioni").value().toLowerCase();
        if(queryParamsMap.hasKey("dateTime")) dateTime = queryParamsMap.get("dateTime").value().toLowerCase();
        if(queryParamsMap.hasKey("serraId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();

        for(int i = 0; i<misuri.size(); i++) {
            if (!misuri.get(i).getTipo().toLowerCase().contains(tipo) ||
                    !misuri.get(i).getMisurazioni().toLowerCase().contains(misurazioni) ||
                    !misuri.get(i).getDateTime().toLowerCase().contains(dateTime) ||
                    !String.valueOf(misuri.get(i).getSerraId()).contains(serraId)) {
                misuri.remove(i);
                i--;
            }
        }

        return misuri;
    }

    /**
     * Add a new task into the DB
     * @param newMisura the Measure to be added
     */
    public void addMisura(Misura newMisura) {
        final String sql = "INSERT INTO misure(tipo, misurazione, data, sensore_id, locale_id) VALUES (?,?,?,?,?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, newMisura.getTipo());
            st.setString(2, newMisura.getMisurazioni());
            st.setString(3, newMisura.getDateTime());
            st.setInt(4, newMisura.getSensoreId());
            st.setInt(5, newMisura.getSerraId());

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMisura(int id) {
        final String sql = "DELETE FROM misure WHERE id = ?";

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

    public void truncateTable(){
        final String sql = "TRUNCATE TABLE misure";
        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Misura getLastMeasureOfSensor(Sensore sensor){
        String sql = "";
        if(sensor.getType().equals("temperatura,umidita"))
            sql = "SELECT id, tipo, misurazione, data, sensore_id, serra_id FROM misure WHERE sensore_id = ? AND tipo = ? ORDER BY id DESC LIMIT 1";
        else
            sql = "SELECT id, tipo, misurazione, data, sensore_id, serra_id FROM misure WHERE sensore_id = ? ORDER BY id DESC LIMIT 1";

        Misura measure = null;

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, sensor.getId());
            if(sensor.getType().equals("temperatura,umidita")) st.setString(2, "temperatura");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                measure= new Misura(rs.getInt("id"), rs.getString("tipo"), rs.getString("misurazione"), rs.getString("data"), sensor.getId(), rs.getInt("serra_id"));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return measure;
    }

}
