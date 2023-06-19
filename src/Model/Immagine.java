package Model;

import java.awt.*;

public class Immagine {
    private int idImmagine;
    private int idArticolo;
    private String pathImmagine;

    public Immagine(){
        this.idImmagine = 0;
        this.idArticolo = 0;
    }

    public Immagine(int idImmagine, int idArticolo, String pathImmagine) {
        this.idImmagine = idImmagine;
        this.idArticolo = idArticolo;
        this.pathImmagine = pathImmagine;
    }

    public Immagine(int idArticolo, int idImmagine, Image image){
        this.idImmagine = idImmagine;
        this.idArticolo = idArticolo;
    }

    public int getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(int idImmagine) {
        this.idImmagine = idImmagine;
    }

    public int getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(int idArticolo) {
        this.idArticolo = idArticolo;
    }


    public String getPathImmagine() {
        return pathImmagine;
    }

    public void setPathImmagine(String pathImmagine) {
        this.pathImmagine = pathImmagine;
    }
}
