package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaProdotto extends Categoria implements ICategoria{

    public CategoriaProdotto(int id, String nome, ICategoria categoriaPadre) {
        super(id, nome, categoriaPadre);
    }

    public CategoriaProdotto() {
    }
}
