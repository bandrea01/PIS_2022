package DAO.Articolo;

import Model.Articolo;
import Model.Immagine;

import java.awt.*;
import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;

public interface IImmagineDAO {
    int add(Immagine immagine);
    int add(ArrayList<Immagine> immagini);
    Immagine findImageById(int idImmagine);
    ArrayList<Immagine> findImagesByArticoloId (int id);
    int removeSingleImageById (int idImmagine);

    ArrayList<Immagine> findAll();

    String getPathByIdArticolo(int idArticolo);
}
