package Model;

import Business.FactoryMethod.NotificationFactory;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Utente{
    private List<Ordine> ordini;
    private List<Prenotazione> prenotazioni;
    private PuntoVendita puntoVenditaRegistrato;
    private NotificationFactory.TipoNotifica canalePreferito;
    private boolean banned;

    public Cliente() {
        super();
        ordini = null;
        prenotazioni = null;
        puntoVenditaRegistrato = null;
        canalePreferito = null;
        banned = false;
    }

    public Cliente(int id, String name, String surname, String email, String username, String password, String phone, int age, String city, String job, ArrayList<Ordine> ordini, ArrayList<Prenotazione> prenotazioni, PuntoVendita puntoVenditaRegistrato, NotificationFactory.TipoNotifica canalePreferito, boolean banned) {
        super(id, name, surname, email, username, password, phone, age, city, job);
        this.ordini = ordini;
        this.prenotazioni = prenotazioni;
        this.puntoVenditaRegistrato = puntoVenditaRegistrato;
        this.canalePreferito = canalePreferito;
        this.banned = banned;
    }
    public Cliente(Utente utente, ArrayList<Ordine> ordini, ArrayList<Prenotazione> prenotazioni, PuntoVendita puntoVenditaRegistrato, NotificationFactory.TipoNotifica canalePreferito, boolean banned) {
        super(utente.getId(), utente.getName(),utente.getSurname(),utente.getEmail(),utente.getUsername(), utente.getPassword(), utente.getPhone(), utente.getAge(),utente.getCity(),utente.getJob());
        this.ordini = ordini;
        this.prenotazioni = prenotazioni;
        this.puntoVenditaRegistrato = puntoVenditaRegistrato;
        this.canalePreferito = canalePreferito;
        this.banned = banned;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }
    public void setOrdini(ArrayList<Ordine> ordini) {
        this.ordini = ordini;
    }
    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
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
