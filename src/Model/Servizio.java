package Model;

import java.util.ArrayList;

public class Servizio extends Articolo implements IArticolo{

    Fornitore fornitore;

    public Servizio() {
        super();
        this.fornitore = null;
    }

    public Servizio(int id, String name, Float prezzo, String descrizione, ICategoria categoria, Fornitore fornitore) {
        this.id = id;
        this.name = name;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.fornitore = fornitore;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Float getPrezzo() {
        return prezzo;
    }
    @Override
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
    public Fornitore getFornitore() {
        return fornitore;
    }
    public void setFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
    }

}
