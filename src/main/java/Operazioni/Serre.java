package Operazioni;

public class Serre {
    private int id;
    private String aziendaAgricolaId;
    private String descrizione;
    private String tipo ;


    public Serre(int id, String idAzienda, String descrizione, String tipo) {
        this.id = id;
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipo = tipo;

    }

    public Serre(String idAzienda, String descrizione, String tipo)
    {
        this.aziendaAgricolaId = aziendaAgricolaId;
        this.descrizione = descrizione;
        this.tipo = tipo;
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
        return tipo;
    }



}

