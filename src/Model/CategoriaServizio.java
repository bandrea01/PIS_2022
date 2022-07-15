package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaServizio extends Categoria implements ICategoria{

    public CategoriaServizio(int id, String nome, ICategoria categoriaPadre) {
        super(id, nome, categoriaPadre);
    }

    public CategoriaServizio() {
    }
}
