package Operazioni;


/**
 * Descrive un'operazione e le sue proprietà.
 */
public class ProgrammaIrrig {

    private int id;
    private String date;
    private int oraInizio;
    private int oraFine;
    private int aziendaAgricolaId;
    private String userId;

    /**
    @param id rappresenta un singolo programma d'irrigazione
    */

    public ProgrammaIrrig(int id, String date, int oraInizio, int oraFine, int aziendaAgricolaId, String userId){

        this.id = id;
        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.userId = userId;
    }

    /**
     * Overloaded constructor. Crea un programma d'irrigazione senza id dato.
     *
     */
    public ProgrammaIrrig(String date, int oraInizio, int oraFine, int aziendaAgricolaId, String userId){

        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getOraInizio() {
        return oraInizio;
    }

    public int getOraFine() {
        return oraFine;
    }

    public int getAziendaAgricolaId() {
        return aziendaAgricolaId;
    }

    public String getUserId() {
        return userId;
    }
}
