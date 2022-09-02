package Model;

public class Collocazione {

    private int idCollocazione;
    private int corsia;
    private int scaffale;

    public Collocazione() {
        this.corsia = 0;
        this.scaffale = 0;
    }
    public Collocazione(int idCollocazione, int corsia, int scaffale) {
        this.idCollocazione = idCollocazione;
        this.corsia = corsia;
        this.scaffale = scaffale;
    }

    public int getIdCollocazione() {
        return idCollocazione;
    }

    public void setIdCollocazione(int idCollocazione) {
        this.idCollocazione = idCollocazione;
    }

    public int getCorsia() {
        return corsia;
    }

    public void setCorsia(int corsia) {
        this.corsia = corsia;
    }

    public int getScaffale() {
        return scaffale;
    }

    public void setScaffale(int scaffale) {
        this.scaffale = scaffale;
    }
}
