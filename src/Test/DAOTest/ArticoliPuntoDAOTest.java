package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArticoliPuntoDAOTest {
    static Categoria categoria = new Categoria(9999, "categoria",null);
    static Produttore produttore = new Produttore(9999, "produttore", "www.produttore.com", "Tokyo", "Giappone");
    static Prodotto prodotto = new Prodotto(9999,"Forno", 200F, "un forno", categoria, produttore);
    static PuntoVendita punto = new PuntoVendita(9999,"punto",9999);
    static Manager manager = new Manager(9999,"Maria","Ferri","maria.ferri@gmail.com","mferri","12345678", "22222222", 40, "Lecce", "Docente", punto);

    @Before
    public void setUp(){
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();

        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore);
        articoloDAO.addProdotto(prodotto);
        articoloPuntoVenditaDAO.add(prodotto, punto);
    }
    @Test
    public void findByPunto(){
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        Assert.assertEquals(1, articoloPuntoVenditaDAO.findByPunto(punto).size());
    }

    @Test
    public void removeArticoloFromAll(){
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        articoloPuntoVenditaDAO.removeArticoloFromAll(prodotto);
        Assert.assertEquals(0, articoloPuntoVenditaDAO.findByPunto(punto).size());
    }

    @After
    public void tearDown(){
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();

        articoloPuntoVenditaDAO.remove(prodotto, punto);
        articoloDAO.removeById(prodotto.getId());
        produttoreDAO.remove(produttore);
        categoriaDAO.remove(categoria);
        puntoVenditaDAO.remove(punto);
        utenteDAO.removeById(manager.getId());
    }
}
