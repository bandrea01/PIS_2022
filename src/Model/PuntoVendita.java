package Model;

public class PuntoVendita {
    private int id;
    private String name;
    private int idMan;


    public PuntoVendita() {
        id = 0;
        name = "";
        idMan = 0;
    }

    public PuntoVendita(int id, String name, int idMan) {
        this.id = id;
        this.name = name;
        this.idMan = idMan;
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
    public int getIdMan() {
        return idMan;
    }
    public void setidMan(int idMan) {
        this.idMan = idMan;
    }

}
