package Operazioni;

public class AziendaAgricola {
    private int id;
    private String descrizione;
    private String tipo;


    public AziendaAgricola(int id, String descrizione, String tipo) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;


    }

    public AziendaAgricola(String descrizione,String tipo){
        this.descrizione = descrizione;
    }

    public int getId()
    {
        return id;
    }
    public String getDescrizione()
    {
        return descrizione;
    }

    public String getTipo() {
        return tipo;
    }

}
