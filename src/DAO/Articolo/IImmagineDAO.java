package DAO.Articolo;

import Model.Immagine;

import java.util.ArrayList;

public interface IImmagineDAO {
    int add(Immagine immagine);
    int add(ArrayList<Immagine> immagini);
    Immagine findImageById(int idImmagine);
    ArrayList<Immagine> findImagesByArticoloId (int id);
    int removeSingleImageById (int idImmagine);

    int removeImagesByArticleId(int idArticle);

    ArrayList<Immagine> findAll();

    String getPathByIdArticolo(int idArticolo);
}
