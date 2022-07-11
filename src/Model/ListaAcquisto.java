package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaAcquisto {
    public enum StatoLista { DA_PAGARE, PAGATA }

    private String nome;
    private List<IArticolo> prodotti = new ArrayList<>();
    private Date dataCreazione;
    private StatoLista statoLista;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<IArticolo> getProdotti() {
        return prodotti;
    }
    public void setProdotti(List<IArticolo> prodotti) {
        this.prodotti = prodotti;
    }
    public Date getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public StatoLista getStatoLista() {
        return statoLista;
    }
    public void setStatoLista(StatoLista statoLista) {
        this.statoLista = statoLista;
    }
}
