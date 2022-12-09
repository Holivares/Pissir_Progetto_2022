package Operazioni;

public class AziendaAgricola {
    private int id;
    private String nome;

    public AziendaAgricola(int id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public AziendaAgricola(String nome){
        this.nome = nome;
    }

    public int getId()
    {
        return id;
    }
    public String getNome()
    {
        return nome;
    }
}
