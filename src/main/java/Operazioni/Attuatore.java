package Operazioni;

public class Attuatore {
    private int id;
    private String descrizione;
    private String stato;
    private String manual;
    private int aziendaAgricolaId;

    public Attuatore(int id, String descrizione, String stato, String manual, int aziendaAgricolaId) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = stato;
        this.manual = manual;
        this.aziendaAgricolaId = aziendaAgricolaId;
    }
    public int getId()
    {
        return id;
    }
    public String getDescrizione()
    {
        return descrizione;
    }
    public String getStato()
    {
        return stato;
    }
    public String getManual()
    {
        return manual;
    }
    public int getAziendaAgricolaId() { return aziendaAgricolaId; }
}
