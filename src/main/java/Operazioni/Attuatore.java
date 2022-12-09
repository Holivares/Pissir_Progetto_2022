package Operazioni;

public class Attuatore {
    private int id;
    private String descrizione;
    private String stato;
    private String manual;
    private int serraId;

    public Attuatore(int id, String descrizione, String stato, String manual, int serraId) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = stato;
        this.manual = manual;
        this.serraId = serraId;
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
    public int getSerraId() { return serraId; }
}
