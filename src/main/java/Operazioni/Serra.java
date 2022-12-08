package Operazioni;

public class Serra {
    private int id;
    private String aziendaAgricolaId;
    private String descrizione;
    private String tipoColtura ;


    public Serra(int id, String idAzienda, String descrizione, String tipo) {
        this.id = id;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipoColtura = tipoColtura;

    }
 /**
     * Overloaded constructore. Crea una serra senza id dato.
     *
     */
    public Serra(String idAzienda, String descrizione, String tipoColtura)
    {
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipoColtura = tipoColtura;
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

    public String getTipo() {
        return tipoColtura;
    }



}

