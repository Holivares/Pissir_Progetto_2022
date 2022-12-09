package OperazioniModel;

public class SensoreJs {
    private int id;
    private String descrizione;
    private String tipo;
    private String ultimaMisura;
    private int serraId;

    public SensoreJs(int id, String descrizione, String tipo,String ultimaMisura, int serraId) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.ultimaMisura = ultimaMisura;
        this.serraId = serraId;
    }
    public int getId() { return id; }
    public String getDescrizione() { return descrizione;}
    public String getTipo() {
        return tipo;
    }
    public String getUltimaMisura() {
        return ultimaMisura;
    }
    public int getSerraId() { return serraId; }
}
