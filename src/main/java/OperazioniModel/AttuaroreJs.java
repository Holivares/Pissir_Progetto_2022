package OperazioniModel;

public class AttuaroreJs {

    private int id;

    private String description;

    private String type;

    private String state;

    private String manual;

    private String lastMeasure;
    private int serraId;


    /**
     * serra main constructor.
     *
     */
    public AttuaroreJs(int id, String description, String type, String state, String manual, String lastMeasure, int serraId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.state = state;
        this.manual = manual;
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
    public String getState() {
        return state;
    }
    public String getManual() { return manual; }

    public String getLastMeasure() {
        return lastMeasure;
    }
    public int getSerraId() {
        return serraId;
    }
}
