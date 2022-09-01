package Model;

public class Magazzino {

    private int id;
    private PuntoVendita punto;

    public Magazzino(){
        this.id = 0;
        this.punto = null;
    }

    public Magazzino(int idMagazzino, PuntoVendita punto) {
        this.id = idMagazzino;
        this.punto = punto;
    }

    public int getId() {
        return id;
    }

    public void setId (int idMagazzino) {
        this.id = idMagazzino;
    }

    public PuntoVendita getPunto() {
        return punto;
    }

    public void setPuntoVendita(PuntoVendita punto) {
        this.punto = punto;
    }
}
