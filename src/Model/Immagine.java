package Model;

import java.awt.*;

public class Immagine {
    private int idImmagine;
    private int idArticolo;
    private Image image;

    public Immagine(){
        this.idImmagine = 0;
        this.idArticolo = 0;
        this.image = null;
    }

    public Immagine(int idArticolo, int idImmagine, Image image){
        this.idImmagine = idImmagine;
        this.idArticolo = idArticolo;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
