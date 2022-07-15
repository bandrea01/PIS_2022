package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Articolo implements IArticolo {

    protected int id;
    protected String name;
    protected Float prezzo;
    protected String descrizione;
    protected ArrayList<File> immagini;
    protected ICategoria categoria;

    public Articolo() {
    }

    public Articolo(int id, String name, Float prezzo, ICategoria categoria) {
        this.id = id;
        this.name = name;
        this.prezzo = prezzo;
    }

    @Override
    public int getId(){
        return id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public Float getPrezzo() {
        return prezzo;
    }
    @Override
    public void setId (int id){
        this.id = id;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
    @Override
    public String getDescrizione() {
        return descrizione;
    }
    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    @Override
    public ArrayList<File> getImmagini() {
        return immagini;
    }
    @Override
    public void setImmagini(ArrayList<File> immagini) {
        this.immagini = immagini;
    }
    @Override
    public ICategoria getCategoria() {
        return categoria;
    }
    @Override
    public void setCategoria(ICategoria categoria) {
        this.categoria = categoria;
    }
}
