package View.ViewModel;

import Business.ArticoloBusiness;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class RowCatalog {
    private int idArticolo;
    private String nome;
    private String produttoreFornitore;
    private Float prezzo;
    private String categoria;
    private JCheckBox checkBox;
    private JButton viewImageButton;

    public RowCatalog(){
        ArticoloBusiness articoloBusiness = ArticoloBusiness.getInstance();
        this.checkBox = new JCheckBox();
        this.viewImageButton = ButtonCreator.createButton("", true, Color.WHITE, articoloBusiness.showImagePopup(), null);
        this.viewImageButton.setIcon(new ImageIcon("resources/unisalento_jpeg.jpeg"));
    }

    public int getIdArticolo() {
        return idArticolo;
    }
    public void setIdArticolo(int idArticolo) {
        this.idArticolo = idArticolo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getProduttoreFornitore() {
        return produttoreFornitore;
    }
    public void setProduttoreFornitore(String produttoreFornitore) {
        this.produttoreFornitore = produttoreFornitore;
    }
    public Float getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public boolean getChecked() {
        return checkBox.isSelected();
    }
    public void setChecked(boolean state) {
        this.checkBox.setSelected(state);
    }

    public void setImageIcon(InputStream imageIcon) {
        //this.viewImageButton.setIcon(imageIcon);
    }
    public ImageIcon getImageIcon(){
        return (ImageIcon) viewImageButton.getIcon();
    }
}
