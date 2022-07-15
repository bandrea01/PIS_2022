package Model;

public class Feedback {
    public enum Gradimento { PESSIMO, SCARSO, NORMALE, BUONO, ECCELLENTE }

    private int idFeedback;
    private Articolo articolo;
    private Utente utente;
    private String commento;
    private Gradimento gradimento;
    private String risposta;
    private Manager manager;


    public Feedback(int idFeedback, Articolo articolo, Utente utente, String commento, Gradimento gradimento, String risposta, Manager manager) {
        this.idFeedback = idFeedback;
        this.articolo = articolo;
        this.utente = utente;
        this.commento = commento;
        this.gradimento = gradimento;
        this.risposta = risposta;
        this.manager = manager;
    }

    public Feedback() {
        this.idFeedback = 0;
        this.articolo = null;
        this.utente = null;
        this.commento = "";
        this.gradimento = null;
        this.risposta = "";
        this.manager = null;
    }

    public int getIdFeedback() {
        return idFeedback;
    }
    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }
    public Articolo getArticolo() {
        return articolo;
    }
    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }
    public Utente getUtente () {
        return utente;
    }
    public void setUtente (Utente utente) {
        this.utente = utente;
    }
    public String getCommento() {
        return commento;
    }
    public void setCommento(String commento) {
        this.commento = commento;
    }
    public Gradimento getGradimento() {
        return gradimento;
    }
    public void setGradimento(Gradimento gradimento) {
        this.gradimento = gradimento;
    }
    public String getRisposta() {
        return risposta;
    }
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
