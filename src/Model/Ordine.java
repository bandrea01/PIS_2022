package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ordine {

    private int idOrdine;
    private Utente utente;
    private ArrayList<ProdottoOrdine> prodotti;
    private ArrayList<Servizio> servizi;
    private Date dataCreazione;
    public enum StatoOrdine { DA_PAGARE, PAGATA }
    private StatoOrdine statoOrdine;

    public Ordine(int idOrdine, Utente utente, ArrayList<ProdottoOrdine> prodotti, ArrayList<Servizio> servizi, Date dataCreazione, StatoOrdine statoOrdine) {
        this.idOrdine = idOrdine;
        this.utente = utente;
        this.prodotti = prodotti;
        this.servizi = servizi;
        this.dataCreazione = dataCreazione;
        this.statoOrdine = statoOrdine;
    }

    public Ordine() {
        this.idOrdine = 0;
        this.utente = null;
        this.prodotti = null;
        this.servizi = null;
        this.dataCreazione = null;
        this.statoOrdine = StatoOrdine.DA_PAGARE;
    }

    public int getIdOrdine() {
        return idOrdine;
    }
    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }
    public Utente getUtente() {
        return utente;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    public ArrayList<ProdottoOrdine> getProdotti() {
        return prodotti;
    }
    public void setProdotti(ArrayList<ProdottoOrdine> prodotti) {
        this.prodotti = prodotti;
    }
    public ArrayList<Servizio> getServizi() {
        return servizi;
    }
    public void setServizi(ArrayList<Servizio> servizi) {
        this.servizi = servizi;
    }
    public Date getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }
    public void setStatoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
}
