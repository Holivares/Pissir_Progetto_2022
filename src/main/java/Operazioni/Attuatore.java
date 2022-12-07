package Operazioni;

public class Attuatore {
    private int id;
    private String descripzione;
    private String stato;
    private String manual;
    private int serraId;

    public Attuatore(int id, String descripzione, String stato, String manual, int serraId) {
        this.id = id;
        this.descripzione = descripzione;
        this.stato = stato;
        this.manual = manual;
        this.serraId = serraId;
    }
    public int getId()
    {
        return id;
    }
    public String getDescription()
    {
        return descripzione;
    }
    public String getStato()
    {
        return stato;
    }
    public String getManual()
    {
        return manual;
    }

}
