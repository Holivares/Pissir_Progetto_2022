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
    private int serraId;
    private String userId;

    /**
    @param id rappresenta un singolo programma d'irrigazione
    */

    public ProgrammaIrrig(int id, String date, int oraInizio, int oraFine, int serraId, String userId){

        this.id = id;
        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.userId = userId;
        this.serraId = serraId;
    }

    /**
     * Overloaded constructor. Crea un programma d'irrigazione senza id dato.
     *
     */
    public ProgrammaIrrig(String date, int oraInizio, int oraFine, int serraId, String userId){

        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.serraId = serraId;
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

    public int getSerraId() {
        return serraId;
    }

    public String getUserId() {
        return userId;
    }


}
