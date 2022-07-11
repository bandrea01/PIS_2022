package Model;

public class Acquirente extends Utente{
    private long phone;
    private int age;
    private String city;
    private String job;

    public Acquirente() {
        super();
        phone = 0;
        age = 0;
        city = "";
        job = "";
    }
    public Acquirente(String name, String surname, String username, String email, String pwd, long phone, int age, String city, String job) {
        super(name, surname, username, pwd, email);
        this.phone = phone;
        this.age = age;
        this.city = city;
        this.job = job;
    }

    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
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
