package Model;

public class Fornitore {
    private int id;
    private String nome;
    private String sito;
    private String citta;
    private String nazione;

    public Fornitore() {
        this.id = 0;
        this.nome = "";
        this.sito = "";
        this.citta = "";
        this.nazione = "";
    }

    public Fornitore(int id, String nome, String sito, String citta, String nazione) {
        this.id = id;
        this.nome = nome;
        this.sito = sito;
        this.citta = citta;
        this.nazione = nazione;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSito() {
        return sito;
    }
    public void setSito(String sito) {
        this.sito = sito;
    }
    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    public String getNazione() {
        return nazione;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
}
