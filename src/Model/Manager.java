package Model;

public class Manager extends Utente{
    private Float salary;

    public Manager(String name, String surname, String username, String email, String pwd, Float salary) {
        super(name, surname, username, email, pwd);
        this.salary = salary;
    }

    public Float getSalary() {
        return salary;
    }
    public void setSalary(Float salary) {
        this.salary = salary;
    }
}
