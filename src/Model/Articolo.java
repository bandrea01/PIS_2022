package Model;

import java.util.List;

public class Articolo {

    protected String name;
    protected Float prezzo;
    private List<Feedback> feedbackList;

    public Articolo() {
        prezzo = 0F;
    }

    public Articolo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public String getName() {
        return name;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}
