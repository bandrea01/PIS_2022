package Model;


public class Prodotto extends Articolo implements IArticolo {

    public Prodotto() {
        super();
    }

    public Prodotto(String name, Float prezzo) {
        this.name = name;
        this.prezzo = prezzo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Float getPrezzo() {
        return this.prezzo;
    }

    @Override
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
