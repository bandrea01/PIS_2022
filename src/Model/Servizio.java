package Model;

public class Servizio extends Articolo implements IArticolo{

    Float prezzo;
    String name;

    public Servizio(Float prezzo) {
        super(prezzo);
    }

    public Servizio() {
        this.name = "";
        this.prezzo = 0F;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Float getPrezzo() {
        return null;
    }

    @Override
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}
