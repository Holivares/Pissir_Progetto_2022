package Operazioni;


/**
 * Descrive un'operazione e le sue proprietà.
 */
public class ProgrammaIrrig {

    private int id;
    private String date;
    private int oraInizio;
    private int oraFine;
    private int collaboratori;
    private int aziendaAgricolaId;
    private String serraId;

    private String userId;





    /**
    @param id rappresenta un singolo programma d'irrigazione
    */

    public ProgrammaIrrig(int id, String date, int oraInizio, int oraFine, int collaboratori, int aziendaAgricolaId , String serraId, String userId){

        this.id = id;
        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.collaboratori = collaboratori;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.serraId = serraId;
        this.userId = userId;

    }

    /**
     * Overloaded constructore. Crea un programma d'irrigazione senza id dato.
     *
     */
    public ProgrammaIrrig(String date, int oraInizio, int oraFine,int collaboratori, int aziendaAgricolaId, String serraId, String userId){

        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.collaboratori = collaboratori;
        this.aziendaAgricolaId = aziendaAgricolaId;
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
    public int getCollaboratori() { return collaboratori; }
    public int getAziendaAgricolaId() {
        return aziendaAgricolaId;
    }

    public String getSerraId() { return serraId; }

    public String getUserId() { return userId; }
}
