package Model;

import java.util.Date;

public class Feedback {
    public enum Gradimento { PESSIMO, SCARSO, NORMALE, BUONO, ECCELLENTE }

    private int id;
    private String openText;
    private Gradimento gradimento;
    private boolean letto;
    //private boolean risposto;
    //posso fare classe Risposta per scalabilità
    private String risposta;
    private Date data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenText() {
        return openText;
    }

    public void setOpenText(String openText) {
        this.openText = openText;
    }

    public Gradimento getGradimento() {
        return gradimento;
    }

    public void setGradimento(Gradimento gradimento) {
        this.gradimento = gradimento;
    }

    public boolean isLetto() {
        return letto;
    }

    public void setLetto(boolean letto) {
        this.letto = letto;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
