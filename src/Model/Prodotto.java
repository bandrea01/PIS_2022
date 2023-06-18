package Model;


import java.util.ArrayList;

public class Prodotto extends Articolo implements IArticolo {

    private Produttore produttore;
    ArrayList<Prodotto> sottoProdotti;




    public Prodotto() {
        super();
        this.produttore = null;
        this.sottoProdotti = null;
    }

    public Prodotto(int id, String name, Float prezzo, String descrizione, ICategoria categoria, Produttore produttore) {
        this.id = id;
        this.name = name;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.produttore = produttore;
        this.sottoProdotti = null;
    }

    public Prodotto(int id, String name, String descrizione, ICategoria categoria, Produttore produttore, ArrayList<Prodotto> sottoProdotti) {
        this.id = id;
        this.name = name;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.produttore = produttore;
        this.sottoProdotti = sottoProdotti;
        this.prezzo = getPrezzo(this.sottoProdotti);
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public Float getPrezzo() {
        return this.prezzo;
    }
    public Float getPrezzo(ArrayList<Prodotto> sottoProdotti){
        this.prezzo = 0F;
        for (Prodotto p : sottoProdotti){
            this.prezzo += p.getPrezzo();
        }
        return prezzo;
    }
    @Override
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    public Produttore getProduttore() {
        return produttore;
    }
    public void setProduttore(Produttore produttore) {
        this.produttore = produttore;
    }
    public ArrayList<Prodotto> getSottoProdotti() {
        return sottoProdotti;
    }
    public void setSottoProdotti(ArrayList<Prodotto> sottoProdotti) {
        this.sottoProdotti = sottoProdotti;
    }

}
