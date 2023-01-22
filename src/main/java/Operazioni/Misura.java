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
    private int aziendaAgricolaId;


    /**
     * @param id rappresenta l'identificativo unico del programma d'irrigazione
     */
    public Misura(int id, String tipo, String misurazioni, String dateTime, int sensoreId, int aziendaAgricolaId) {
        this.id = id;
        this.tipo = tipo;
        this.misurazioni = misurazioni;
        this.dateTime = dateTime;
        this.sensoreId = sensoreId;
        this.aziendaAgricolaId = aziendaAgricolaId;
    }

    /**
     * Overloaded constructore.Crea un programma d'irrigazione senza un dato id.
     *
     */
    public Misura(String tipo, String misurazioni, String dateTime, int sensoreId, int aziendaAgricolaId) {
        this.id = 0;
        this.tipo = tipo;
        this.misurazioni = misurazioni;
        this.dateTime = dateTime;
        this.sensoreId = sensoreId;
        this.aziendaAgricolaId = aziendaAgricolaId;
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

    public int getAziendaAgricolaId() {
        return aziendaAgricolaId;
    }
}