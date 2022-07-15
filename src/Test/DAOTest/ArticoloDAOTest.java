package Test.DAOTest;
import DAO.Articolo.ArticoloDAO;
import DAO.Articolo.ImmagineDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Fornitore.IFornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.IProduttoreDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.*;
import com.mysql.cj.jdbc.Blob;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class ArticoloDAOTest {
    static Categoria cucina = new Categoria(1, "Cucina", null);

    static Fornitore amazon = new Fornitore(1,"Amazon", "www.amazon.com", "Seattle", "USA");
    static Produttore gazprom = new Produttore(1,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");

    static Prodotto prodotto = new Prodotto(1, "Frigorifero", 150F, "un frigorifero", cucina, gazprom);

    static Prodotto sottoProdotto1 = new Prodotto(4, "Fornelli", 75F, "dei fornelli", cucina, gazprom);
    static Prodotto sottoProdotto2 = new Prodotto(5, "Griglia", 10F, "una griglia", cucina, gazprom);
    public ArrayList<Prodotto> array = new ArrayList<>(2);
    static Prodotto prodotto2;

    static Servizio servizio = new Servizio(2,"Montaggio", 300F, "montaggio cucina", cucina, amazon);

    static File immagine = new File("frigorifero.jpeg");


    @Before
    public void setUp (){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ImmagineDAO immagineDAO = ImmagineDAO.getInstance();

        array.add(sottoProdotto1);
        array.add(sottoProdotto2);
        prodotto2 = new Prodotto(3, "Piano cottura", "un piano cottura", cucina, gazprom, array);

        categoriaDAO.add(cucina);
        produttoreDAO.add(gazprom);
        fornitoreDAO.add(amazon);
        articoloDAO.addProdotto(prodotto);
        articoloDAO.addProdotto(sottoProdotto1);
        articoloDAO.addProdotto(sottoProdotto2);
        articoloDAO.addProdotto(prodotto2);
        articoloDAO.addServizio(servizio);
        immagineDAO.add(1, immagine);
    }

    @Test
    public void findById(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Articolo a1 = articoloDAO.findById(1);
        Assert.assertEquals("un frigorifero", a1.getDescrizione());
        Articolo a2 = articoloDAO.findById(2);
        Assert.assertEquals("montaggio cucina", a2.getDescrizione());
        Articolo a3 = articoloDAO.findById(3);
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

        Prodotto p = new Prodotto(1, "Frigorifero","un frigorifero", cucina, gazprom, array);

        articoloDAO.updateProdotto(p);
        Assert.assertEquals(0, Float.compare(85F, articoloDAO.findById(p.getId()).getPrezzo()));
    }

    @Test
    public void updateServizio(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        Servizio s = new Servizio(2,"Montaggio cucina", 300F, "montaggio cucina", cucina, amazon);

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
        ArrayList<Prodotto> prodotti = prodottoDAO.findAllByProduttore(gazprom);
        Assert.assertEquals(4, prodotti.size());
    }

    @Test
    public void getImmagini(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<File> immagini = articoloDAO.getImmagini(1);
        Assert.assertEquals("frigorifero.jpeg", immagini.get(0).getPath());
    }

    @After
    public void tearDown() {
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ImmagineDAO immagineDAO = ImmagineDAO.getInstance();

        immagineDAO.remove(immagine);
        articoloDAO.removeById(prodotto.getId());
        articoloDAO.removeById(sottoProdotto1.getId());
        articoloDAO.removeById(sottoProdotto2.getId());
        articoloDAO.removeById(prodotto2.getId());
        articoloDAO.removeById(servizio.getId());
        produttoreDAO.remove(gazprom.getId());
        fornitoreDAO.remove(amazon.getId());
        categoriaDAO.remove(cucina.getId());
    }
}
