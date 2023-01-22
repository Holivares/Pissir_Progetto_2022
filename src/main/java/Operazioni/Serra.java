package Operazioni;

public class Serra {
    private int id;
    private String aziendaAgricolaId;
    private String descrizione;
    private String tipoColtura ;
    private String utenteId;


    public Serra(int id, String aziendaAgricolaId, String descrizione, String tipoColtura, String utenteId) {
        this.id = id;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipoColtura = tipoColtura;
        this.utenteId = utenteId;

    }
 /**
     * Overloaded constructore. Crea una serra senza un dato id.
     *
     */
    public Serra(String aziendaAgricolaId, String descrizione, String tipoColtura, String utenteId)
    {
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipoColtura = tipoColtura;
        this.utenteId = utenteId;
    }

    public int getId() {
        return id;
    }

    public String aziendaAgricolaId() {
        return aziendaAgricolaId;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipoColtura() {
        return tipoColtura;
    }


    public String getUtenteId() {
        return utenteId;
    }
}

