package Model;

import java.io.File;
import java.util.ArrayList;

public class ProdottoOrdine{
    private Prodotto prodotto;
    private int quantita;

    public ProdottoOrdine(){
        this.prodotto = null;
        this.quantita = 0;
    }

    public ProdottoOrdine(Prodotto prodotto, int quantita){
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }
    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

}
