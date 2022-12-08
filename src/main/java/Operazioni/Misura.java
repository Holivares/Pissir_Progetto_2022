package Operazioni;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;


public class Misura {
    private int id;
    private String tipo;
    private String misurazioni;
    private String dateTime;
    private int sensoreId;
    private int serraId;



    /**
     * @param id represents the reservation unique identifier
     */
    public Misura(int id, String tipo, String misurazioni, String dateTime, int sensoreId, int serraId) {
        this.id = id;
        this.tipo = tipo;
        this.misurazioni = misurazioni;
        this.dateTime = dateTime;
        this.sensoreId = sensoreId;
        this.serraId = serraId;
    }


    /**
     * Overloaded constructor. It create a reservation without a given id.
     *
     */
    public Misura(String tipo, String misurazioni, String dateTime, int sensoreId, int serraId) {
        this.id = 0;
        this.tipo = tipo;
        this.misurazioni = misurazioni;
        this.dateTime = dateTime;
        this.sensoreId = sensoreId;
        this.serraId = serraId;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMisurazioni() {
        return misurazioni;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getSensoreId(){
        return sensoreId;
    }

    public int getSerraId() {
        return serraId;
    }
}