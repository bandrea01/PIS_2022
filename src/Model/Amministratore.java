package Model;

public class Amministratore extends Utente{
    public Amministratore() {
        super();
    }
    public Amministratore(int id, String name, String surname, String email, String username, String password, String phone, int age, String city, String job) {
        super(id, name, surname, email, username, password, phone, age, city, job);
    }
}
