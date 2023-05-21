package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArticoliPuntoDAOTest {

    private UtenteDAO utenteDAO = UtenteDAO.getInstance();
    private PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    private ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    private ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
    static Categoria categoria = new Categoria(1, "Cucina",null);
    static Produttore produttore = new Produttore(1, "Samsung", "aaa", "Tokyo", "Giappone");
    static Prodotto prodotto = new Prodotto(1,"Forno", 200F, "aaa", categoria, produttore);
    static PuntoVendita punto = new PuntoVendita(1,"bbb",1);
    static Manager manager = new Manager(1,"aaa","aaa","aaa","aaa","123","123",0,"aaa","aaa",punto);

    @Before
    public void setUp(){
        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore);
        articoloDAO.addProdotto(prodotto);
        articoloPuntoVenditaDAO.add(prodotto, punto);
    }
    @Test
    public void findByPunto(){
        Assert.assertEquals(1, articoloPuntoVenditaDAO.findByPunto(punto).size());
    }
    @After
    public void tearDown(){
        articoloPuntoVenditaDAO.remove(prodotto, punto);
        articoloDAO.removeById(prodotto.getId());
        produttoreDAO.remove(produttore);
        categoriaDAO.remove(categoria);
        puntoVenditaDAO.remove(punto);
        utenteDAO.removeById(manager.getId());
    }
}
