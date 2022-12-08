package Operazioni;

public class AziendaAgri {
    private int id;
    private String nome;



    public AziendaAgri(int id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public  AziendaAgri(String nome){
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
