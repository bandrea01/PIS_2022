package Model;

public class PuntoVendita {
    private String name;
    private String city;
    private int idMan;
    private int durata;

    public PuntoVendita() {
        name = "";
        city = "";
        idMan = 0;
        durata = 0;
    }

    public PuntoVendita(String name, String city, int idMan, int durata) {
        this.name = name;
        this.city = city;
        this.idMan = idMan;
        this.durata = durata;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getIdMan() {
        return idMan;
    }
    public void setidMan(int idMan) {
        this.idMan = idMan;
    }
    public int getDurata() {
        return durata;
    }
    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String toString(){
        return name + " " + city + " " + idMan + " " + durata;
    }

}
