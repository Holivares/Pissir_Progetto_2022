package Operazioni;

public class Sensore {
    private int id;
    private String description;
//    private String type;
    private int aziendaAgricolaId;

    /**
     * sensore main constructore.
     *
     * @param id  rappresenta l'id di un unico sensore
     * @param description descrizione del sensore
     */
    public Sensore(int id, String description, int aziendaAgricolaId) {
        this.id = id;
        this.description = description;
//        this.type = type;
        this.aziendaAgricolaId = aziendaAgricolaId;
    }

    /*** Getters **/
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
//    public String getType() {
//        return type;
//    }
    public int getAziendaAgricolaId() {
        return aziendaAgricolaId;
    }
}
