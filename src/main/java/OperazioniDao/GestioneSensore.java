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

    GestioneMisura measureDao = new GestioneMisura();
    /**
     * Get all Sensors from the DB
     * @return a list of Sensor, or an empty list if no Sensors are available
     * @param queryParamsMap
     */
    public List<SensoreJs> getAllSensors(QueryParamsMap queryParamsMap) {
        final String sql = "SELECT id, descrizione, tipo, locale_id FROM sensori";

        List<SensoreJs> sensors = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Sensore sensor = new Sensore(rs.getInt("id"), rs.getString("descrizione"), rs.getString("tipo"), rs.getInt("locale_id"));
                Misura measure = measureDao.getLastMeasureOfSensor(sensor);
                String measurement = "";
                try {
                    measurement = measure.getMisurazioni();
                    if(measure.getTipo().equals("temperatura"))
                        measurement += "°C";
                    else measurement += "%";
                } catch(NullPointerException e) {}

                SensoreJs sensorJs = new SensoreJs(sensor.getId(), rs.getString("descrizione"), rs.getString("tipo"), measurement, rs.getInt("locale_id"));
                sensors.add(sensorJs);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return where(queryParamsMap, sensors);
    }

    private List<SensoreJs> where(QueryParamsMap queryParamsMap, List<SensoreJs> sensors){
        String description = "";
        String serraId = "";
        if(queryParamsMap.hasKey("description")) description = queryParamsMap.get("description").value().toLowerCase();
        if(queryParamsMap.hasKey("serraId")) serraId = queryParamsMap.get("serraId").value().toLowerCase();

        for(int i = 0; i<sensors.size(); i++) {
            if (!sensors.get(i).getDescription().toLowerCase().contains(description) ||
                    !String.valueOf(sensors.get(i).getSerraId()).contains(serraId)) {

                sensors.remove(i);
                i--;
            }
        }

        return sensors;
    }

    public Sensore getSensorOfLocal(int localId, String type)
    {
        if(type.equals("temperatura") || type.equals("umidita")) type = "temperatura,umidita";
        Sensore sensor = null;
        final String sql = "SELECT id, descrizione, tipo, locale_id FROM sensori WHERE locale_id = ? AND tipo = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, localId);
            st.setString(2, type);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                sensor = new Sensore(rs.getInt("id"), rs.getString("descrizione"), type, localId);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensor;
    }
}
