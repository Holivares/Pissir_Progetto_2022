package OperazioniModel;

public class SensoreJs {
    private int id;

    private String description;

    private String type;

    private String lastMeasure;
    private int serraId;

    public SensoreJs(int id, String description, String type,String lastMeasure, int serraId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.lastMeasure = lastMeasure;
        this.serraId = serraId;
    }

    public int getId() { return id; }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }

    public String getLastMeasure() {
        return lastMeasure;
    }

    public int getSerraId() { return serraId; }
}
