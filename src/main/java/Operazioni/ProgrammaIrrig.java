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
    private String serraId;
    private String utenteId;





    /**
    @param id rappresenta l'd di un singolo programma d'irrigazione
    */

    public ProgrammaIrrig(int id, String date, int oraInizio, int oraFine, int aziendaAgricolaId , String serraId, String utenteId){

        this.id = id;
        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.serraId = serraId;
        this.utenteId = utenteId;

    }

    /**
     * Overloaded constructore. Crea un programma d'irrigazione senza id dato.
     *
     */
    public ProgrammaIrrig(String date, int oraInizio, int oraFine, int aziendaAgricolaId, String serraId, String utenteId){

        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.serraId = serraId;
        this.utenteId = utenteId;



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

    public String getSerraId() { return serraId; }

    public String getUtenteId() { return utenteId; }
}
