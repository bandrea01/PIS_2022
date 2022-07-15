package DAO.Categoria;

import Model.ICategoria;

import java.util.ArrayList;

public interface ICategoriaDAO {
    ICategoria findById(int id);
    ICategoria findByName(String name);
    ArrayList<ICategoria> findAll();
    ArrayList<ICategoria> findAllSottoCategorie(int id);
    int add(ICategoria categoria);
    int update(ICategoria categoria);
    int remove(int id);
}
