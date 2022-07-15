package Model;

import Business.FactoryMethod.NotificationFactory;

import java.util.List;

public class Cliente extends Utente{
    private List<ListaAcquisto> listeacquisto;
    private List<Prenotazione> prenotazioni;
    private PuntoVendita puntoVenditaRegistrato;
    private NotificationFactory.TipoNotifica canalePreferito;
    private boolean banned;

    public Cliente() {
        super();
        listeacquisto = null;
        prenotazioni = null;
        puntoVenditaRegistrato = null;
        canalePreferito = null;
        banned = false;
    }

    public Cliente(int id, String name, String surname, String email, String username, String password, String phone, int age, String city, String job, List<ListaAcquisto> listeacquisto, List<Prenotazione> prenotazioni, PuntoVendita puntoVenditaRegistrato, NotificationFactory.TipoNotifica canalePreferito, boolean banned) {
        super(id, name, surname, email, username, password, phone, age, city, job);
        this.listeacquisto = listeacquisto;
        this.prenotazioni = prenotazioni;
        this.puntoVenditaRegistrato = puntoVenditaRegistrato;
        this.canalePreferito = canalePreferito;
        this.banned = banned;
    }

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
    public boolean isBanned() {
        return banned;
    }
    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
