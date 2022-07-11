package View.ViewModel;

public class RowCatalog {
    private int idProdotto;
    private String nomeProdotto;
    private String nomeProduttore;
    private Float prezzo;
    private String nomeCategoria;
    private Boolean selezionato;

    public int getIdProdotto() {
        return idProdotto;
    }
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }
    public String getNomeProdotto() {
        return nomeProdotto;
    }
    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }
    public String getNomeProduttore() {
        return nomeProduttore;
    }
    public void setNomeProduttore(String nomeProduttore) {
        this.nomeProduttore = nomeProduttore;
    }
    public Float getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public boolean getSelezionato() {
        return selezionato;
    }
    public void setSelezionato(boolean selezionato) {
        this.selezionato = selezionato;
    }
}
