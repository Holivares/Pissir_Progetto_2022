package Operazioni;

import java.sql.Date;
import java.sql.Time;


public class Utente {

    private String id;
    private String username;
    private String email;
    private String nome;
    private String cognome;
    private String ruolo;

    public Utente(String id, String username, String email, String nome, String cognome, String ruolo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getRuolo() {
        return ruolo;
    }

}
