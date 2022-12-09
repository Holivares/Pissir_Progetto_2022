package Operazioni;

public class Sensore {
    private int id;
    private String descrizione;
    private String tipo;
    private int serraId;


    /**
     * sensore main constructore.
     *
     * @param id  rappresenta l'id di un unico sensore
     * @param descirzione descrizione del sensore
     */
    public Sensore(int id, String descrizione, String tipo,  int serraId) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.serraId = serraId;
    }
    /**
     * Overloaded constructor. Crea un programma d'irrigazione senza id dato.
     *
     */
    public Sensore(String descrizione, String tipo,  int serraId) {
        this.id = 0;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.serraId = serraId;

    }
    /*** Getters **/
    public int getId() {
        return id;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public String getTipo() {return tipo;}
    public int getSerraId() {
        return serraId;
    }

}
