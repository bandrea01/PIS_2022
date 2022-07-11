package Model;

import Business.FactoryMethod.NotificationFactory;

import java.util.List;

public class Cliente extends Acquirente{
    private List<ListaAcquisto> listeacquisto;
    private List<Prenotazione> prenotazioni;
    private PuntoVendita puntoVenditaRegistrato;
    private NotificationFactory.TipoNotifica canalePreferito;

    public List<ListaAcquisto> getListeacquisto() {
        return listeacquisto;
    }
    public void setListeacquisto(List<ListaAcquisto> listeacquisto) {
        this.listeacquisto = listeacquisto;
    }
    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
    public PuntoVendita getPuntoVenditaRegistrato() {
        return puntoVenditaRegistrato;
    }
    public void setPuntoVenditaRegistrato(PuntoVendita puntoVenditaRegistrato) {
        this.puntoVenditaRegistrato = puntoVenditaRegistrato;
    }
    public NotificationFactory.TipoNotifica getCanalePreferito() {
        return canalePreferito;
    }
    public void setCanalePreferito(NotificationFactory.TipoNotifica canalePreferito) {
        this.canalePreferito = canalePreferito;
    }
}
