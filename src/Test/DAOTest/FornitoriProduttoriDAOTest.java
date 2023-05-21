package Test.DAOTest;
import DAO.Fornitore.FornitoreDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.Fornitore;
import Model.Produttore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FornitoriProduttoriDAOTest {
    static Fornitore fornitore = new Fornitore(9999,"fornitore","www.fornitore.com","Seattle", "USA");
    static Produttore produttore = new Produttore(9999,"produttore","www.produttore.com","Moscow", "Russia");
    @Before
    public void setUp (){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        fornitoreDAO.add(fornitore);
        produttoreDAO.add(produttore);
    }

    @Test
    public void findById(){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        Fornitore fornitore = fornitoreDAO.findById(9999);
        Produttore produttore = produttoreDAO.findById(9999);

        Assert.assertEquals("www.fornitore.com", fornitore.getSito());
        Assert.assertEquals("www.produttore.com", produttore.getSito());

    }

    @Test
    public void findByName(){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        Fornitore fornitore = fornitoreDAO.findByName("fornitore");
        Produttore produttore = produttoreDAO.findByName("produttore");

        Assert.assertEquals("www.fornitore.com", fornitore.getSito());
        Assert.assertEquals("www.produttore.com", produttore.getSito());
    }

    @Test
    public void update(){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        Fornitore fornitore2 = new Fornitore(9999,"fornitore2","www.fornitore.com","NYC", "USA");
        Produttore produttore2 = new Produttore(9999,"produttore2","www.produttore.com","Saint Petersburg", "Russia");

        fornitoreDAO.update(fornitore2);
        produttoreDAO.update(produttore2);

        Assert.assertEquals("NYC", fornitoreDAO.findById(9999).getCitta());
        Assert.assertEquals("Saint Petersburg", produttoreDAO.findById(9999).getCitta());

        fornitoreDAO.remove(fornitore2);
        produttoreDAO.remove(produttore2);
    }

    @After
    public void tearDown() {
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        fornitoreDAO.remove(fornitore);
        produttoreDAO.remove(produttore);
    }
}
