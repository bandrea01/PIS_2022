package Model;

public class Manager extends Utente{
    private PuntoVendita puntoVendita;

    public Manager() {
        super();
        this.puntoVendita = null;
    }

    public Manager(int id, String name, String surname, String email, String username, String password, String phone, int age, String city, String job, PuntoVendita puntoVendita) {
        super(id, name, surname, email, username, password, phone, age, city, job);
        this.puntoVendita = puntoVendita;
    }

    public PuntoVendita getPuntoVendita() {
        return puntoVendita;
    }
    public void setPuntoVendita(PuntoVendita puntoVendita) {
        this.puntoVendita = puntoVendita;
    }
}
