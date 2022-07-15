package Model;

public class Utente {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String phone;
    private int age;
    private String city;
    private String job;


    public Utente() {
        id = 0;
        name = "";
        surname = "";
        email = "";
        username = "";
        password = "";
        phone = "";
        age = 0;
        city = "";
        job = "";
    }

    public Utente(int id, String name, String surname, String email, String username, String password, String phone, int age, String city, String job) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.city = city;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
