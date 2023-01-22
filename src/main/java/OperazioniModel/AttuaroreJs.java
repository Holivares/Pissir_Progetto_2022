package OperazioniModel;

public class AttuaroreJs {

    private int id;

    private String descrizione;
    private String tipo;
    private String stato;
    private String manual;
    private String ultimaMisura;
    private int aziendaAgricolaId;


    /**
     * serra main constructor.
     *
     */
    public AttuaroreJs(int id, String descrizione, String tipo, String stato, String manual, String  ultimaMisura, int aziendaAgricolaId) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.stato = stato;
        this.manual = manual;
        this.ultimaMisura = ultimaMisura;
        this.aziendaAgricolaId = aziendaAgricolaId;
    }
    public int getId() { return id; }
    public String getDescrizione() {
        return descrizione;
    }
    public String getTipo() {
        return tipo;
    }
    public String getStato() { return stato; }
    public String getManual() { return manual; }

    public String getUltimaMisura() { return ultimaMisura; }

    public int getAziendaAgricolaId() {
        return aziendaAgricolaId;
    }
}
