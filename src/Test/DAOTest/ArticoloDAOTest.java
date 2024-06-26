package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ArticoloDAOTest {
    static Categoria categoria = new Categoria(9999, "Cucina", null);
    static Fornitore fornitore = new Fornitore(9999,"Amazon", "www.amazon.com", "Seattle", "USA");
    static Produttore produttore1 = new Produttore(9999,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");
    static Prodotto prodotto = new Prodotto(9995, "Frigorifero", 150F, "un frigorifero", categoria, produttore1);
    static Prodotto sottoProdotto1 = new Prodotto(9996, "Fornelli", 75F, "dei fornelli", categoria, produttore1);
    static Prodotto sottoProdotto2 = new Prodotto(9997, "Griglia", 10F, "una griglia", categoria, produttore1);
    public ArrayList<Prodotto> array = new ArrayList<>(2);
    static Prodotto prodotto2;
    static Servizio servizio = new Servizio(9998,"Montaggio", 300F, "montaggio cucina", categoria, fornitore);


    @Before
    public void setUp () throws IOException {
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

        array.add(sottoProdotto1);
        array.add(sottoProdotto2);
        prodotto2 = new Prodotto(9999, "Piano cottura", "un piano cottura", categoria, produttore1, array);

        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore1);
        fornitoreDAO.add(fornitore);
        articoloDAO.addProdotto(prodotto);
        articoloDAO.addProdotto(sottoProdotto1);
        articoloDAO.addProdotto(sottoProdotto2);
        articoloDAO.addProdotto(prodotto2);
        articoloDAO.addServizio(servizio);
    }

    @Test
    public void findById(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Articolo a1 = articoloDAO.findById(9995);
        Assert.assertEquals("un frigorifero", a1.getDescrizione());
        Articolo a2 = articoloDAO.findById(9998);
        Assert.assertEquals("montaggio cucina", a2.getDescrizione());
        Articolo a3 = articoloDAO.findById(9999);
        Assert.assertEquals("un piano cottura", a3.getDescrizione());
    }

    @Test
    public void findByName(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Articolo a1 = articoloDAO.findByName("Frigorifero");
        Assert.assertEquals("un frigorifero", a1.getDescrizione());
        Articolo a2 = articoloDAO.findByName("Montaggio");
        Assert.assertEquals("montaggio cucina", a2.getDescrizione());
        Articolo a3 = articoloDAO.findByName("Griglia");
        Assert.assertEquals(0, Float.compare(10F, a3.getPrezzo()));
    }

    @Test
    public void isProdotto(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Assert.assertTrue(articoloDAO.isProdotto(prodotto.getId()));
        Assert.assertTrue(articoloDAO.isProdotto(sottoProdotto2.getId()));
    }

    @Test
    public void isServizio(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Assert.assertTrue(articoloDAO.isServizio(servizio.getId()));
    }

    @Test
    public void updateProdotto(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Prodotto p = new Prodotto(9995, "Frigorifero","un frigorifero", categoria, produttore1, array);

        articoloDAO.updateProdotto(p);
        Assert.assertEquals(0, Float.compare(85F, articoloDAO.findById(p.getId()).getPrezzo()));
    }

    @Test
    public void updateServizio(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Servizio s = new Servizio(9998,"Montaggio cucina", 300F, "montaggio cucina", categoria, fornitore);

        articoloDAO.updateServizio(s);
        Assert.assertEquals("Montaggio cucina", articoloDAO.findById(s.getId()).getName());
    }

    @Test
    public void getAllSottoProdotti(){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ArrayList<Prodotto> sottoProdotti = prodottoDAO.getAllSottoProdotti(prodotto2);
        Assert.assertEquals(2, sottoProdotti.size());
    }

    @Test
    public void findAllByProduttore(){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ArrayList<Prodotto> prodotti = prodottoDAO.findAllByProduttore(produttore1);
        Assert.assertEquals(4, prodotti.size());
    }
    @After
    public void tearDown() throws IOException {
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        articoloDAO.removeById(prodotto.getId());
        articoloDAO.removeById(sottoProdotto1.getId());
        articoloDAO.removeById(sottoProdotto2.getId());
        articoloDAO.removeById(prodotto2.getId());
        articoloDAO.removeById(servizio.getId());
        produttoreDAO.remove(produttore1);
        fornitoreDAO.remove(fornitore);
        categoriaDAO.remove(categoria);
    }
}
