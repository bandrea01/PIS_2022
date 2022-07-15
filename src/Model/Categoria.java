package Model;

import java.util.ArrayList;
import java.util.List;

public class Categoria implements ICategoria{
    private int id;
    private String nome;
    private List<ICategoria> sottocategorie;
    private ICategoria categoriaPadre;

    public Categoria(int id, String nome, ICategoria categoriaPadre) {
        this.id = id;
        this.nome = nome;
        this.categoriaPadre = categoriaPadre;
    }

    public Categoria() {
        this.id = -1;
        this.nome = null;
        this.categoriaPadre = null;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getName() {
        return nome;
    }
    @Override
    public void setName(String nome) {
        this.nome = nome;
    }
    @Override
    public ICategoria getCategoriaPadre () {
        return categoriaPadre;
    }
    @Override
    public void setCategoriaPadre (ICategoria categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }
    @Override
    public List<ICategoria> getSubCategories() {
        return sottocategorie;
    }
    @Override
    public void addSubCategory (ICategoria categoria) {
        this.sottocategorie.add(categoria);
    }
    @Override
    public void addSubCategories (List<ICategoria> categorie) {
        this.sottocategorie.addAll(categorie);
    }


}
