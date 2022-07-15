package DAO.Articolo;

import Model.Articolo;

import java.io.File;

public interface IImmagineDAO {
    int add(int idArticolo, File file);
    int remove(File file);
}
