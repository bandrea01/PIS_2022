package Model;

public class Utente {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String pwd;

    public Utente() {
        name = "";
        surname = "";
        username = "";
        pwd = "";
        email = "";
    }
    public Utente(String name, String surname, String username, String pwd, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.pwd = pwd;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String toString(){
        return name + " " + surname + " " + username + " " + pwd + " " + email;}
}
