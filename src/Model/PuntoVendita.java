package Model;

public class PuntoVendita {
    private int idPuntoVendita;
    private String name;
    private int idManager;


    public PuntoVendita() {
        idPuntoVendita = 0;
        name = "";
        idManager = 0;
    }

    public PuntoVendita(int idPuntoVendita, String name, int idManager) {
        this.idPuntoVendita = idPuntoVendita;
        this.name = name;
        this.idManager = idManager;
    }

    public int getIdPuntoVendita() {
        return idPuntoVendita;
    }
    public void setIdPuntoVendita(int idPuntoVendita) {
        this.idPuntoVendita = idPuntoVendita;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIdManager() {
        return idManager;
    }
    public void setManager(int idManager) {
        this.idManager = idManager;
    }

}
