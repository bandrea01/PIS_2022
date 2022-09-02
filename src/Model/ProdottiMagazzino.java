package Model;

public class ProdottiMagazzino {
    private Magazzino magazzino;
    private Collocazione collocazione;
    private Prodotto prodotto;
    private int quantita;

    public ProdottiMagazzino() {
        this.magazzino = null;
        this.collocazione = null;
        this.prodotto = null;
        this.quantita = 0;
    }

    public ProdottiMagazzino(Magazzino magazzino, Collocazione collocazione, Prodotto prodotto, int quantita) {
        this.magazzino = magazzino;
        this.collocazione = collocazione;
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }

    public Collocazione getCollocazione() {
        return collocazione;
    }

    public void setCollocazione(Collocazione collocazione) {
        this.collocazione = collocazione;
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
