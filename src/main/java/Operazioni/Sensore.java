package Operazioni;

public class Sensore {
    private int id;
    private String description;
    private String tipo;
    private int serraId;


    /**
     * sensore main constructore.
     *
     * @param id  rappresenta l'id di un unico sensore
     * @param description descrizione del sensore
     */
    public Sensore(int id, String description, String tipo,  int serraId) {
        this.id = id;
        this.description = description;
        this.tipo = tipo;
        this.serraId = serraId;
    }
    /**
     * Overloaded constructor. Crea un programma d'irrigazione senza id dato.
     *
     */
    public Sensore(String description, String tipo,  int serraId) {
        this.id = 0;
        this.description = description;
        this.tipo = tipo;
        this.serraId = serraId;

    }
    /*** Getters **/
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getTipo() {return tipo;}
    public int getSerraId() {
        return serraId;
    }

}
