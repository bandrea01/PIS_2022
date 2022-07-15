package Test.DAOTest;

import DAO.Categoria.CategoriaDAO;
import DAO.Categoria.ICategoriaDAO;
import Model.Categoria;
import Model.ICategoria;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CategoriaDAOTest {
    ICategoria giardino = new Categoria(1, "Giardino", null);
    ICategoria attrezzi = new Categoria(2,"Attrezzi", giardino);
    @Before
    public void setUp() {
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

        categoriaDAO.add(giardino);
        categoriaDAO.add(attrezzi);
    }
    @After
    public void tearDown() {
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        categoriaDAO.remove(giardino.getId());
        categoriaDAO.remove(attrezzi.getId());
    }
    @Test
    public void findById(){
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        Assert.assertEquals("Giardino", categoriaDAO.findById(1).getName());
    }
    @Test
    public void findByName(){
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        Assert.assertEquals(2, categoriaDAO.findByName("Attrezzi").getId());
    }
    @Test
    public void findAll() {
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ArrayList<ICategoria> categorie = categoriaDAO.findAll();
        Assert.assertEquals(2, categorie.size());
    }
    @Test
    public void findAllSottoCategorie() {
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ArrayList<ICategoria> sottoCategorie = categoriaDAO.findAllSottoCategorie(giardino.getId());
        Assert.assertEquals(1, sottoCategorie.size());
    }
    @Test
    public void update() {
        ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ICategoria newCategoria = new Categoria(1, "Officina", null);
        categoriaDAO.update(newCategoria);
        Assert.assertEquals("Officina", categoriaDAO.findById(1).getName());
    }
}