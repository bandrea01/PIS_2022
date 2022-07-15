package Model;

import java.util.List;

public interface ICategoria {
    int getId ();
    void setId (int id);
    ICategoria getCategoriaPadre ();
    void setCategoriaPadre (ICategoria categoriaPadre);
    String getName();
    void setName(String nome);
    List<ICategoria> getSubCategories ();
    void addSubCategory (ICategoria categoria);
    void addSubCategories (List<ICategoria> categorie);

}
