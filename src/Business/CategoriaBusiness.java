package Business;

import DAO.Categoria.CategoriaDAO;
import Model.Categoria;

public class CategoriaBusiness {
    private static CategoriaBusiness instance;

    public static synchronized CategoriaBusiness getInstance() {
        if (instance == null) {
            instance = new CategoriaBusiness();
        }
        return instance;
    }

    public int addCategory(String nome, int idCategoriaPadre) {
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        Categoria categoria = new Categoria();
        int id = categoriaDAO.findAll().size() + 1;

        if (nome.isEmpty()) {
            return 0;
        }

        //CONTROLLI SUL DAO
        if (categoriaDAO.categoriaExist(nome)) {
            return 1;
        }

        categoria.setName(nome);
        if (idCategoriaPadre == 0) {
            categoria.setId(id);
            categoria.setCategoriaPadre(null);
            categoriaDAO.add(categoria);
            return 2;
        }
        categoria.setId(id);
        categoria.setCategoriaPadre(categoriaDAO.findById(idCategoriaPadre));

        categoriaDAO.add(categoria);
        return 2;
    }
}
