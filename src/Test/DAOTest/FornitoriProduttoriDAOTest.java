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
    static Fornitore fornitore = new Fornitore(1,"Amazon","www.amazon.com","Seattle", "USA");
    static Produttore produttore = new Produttore(1,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");
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

        Fornitore fornitore = fornitoreDAO.findById(1);
        Produttore produttore = produttoreDAO.findById(1);

        Assert.assertEquals("www.amazon.com", fornitore.getSito());
        Assert.assertEquals("www.gazprom.com", produttore.getSito());

    }

    @Test
    public void findByName(){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        Fornitore fornitore = fornitoreDAO.findByName("Amazon");
        Produttore produttore = produttoreDAO.findByName("Gazprom");

        Assert.assertEquals("www.amazon.com", fornitore.getSito());
        Assert.assertEquals("www.gazprom.com", produttore.getSito());
    }

    @Test
    public void update(){
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        Fornitore fornitore = new Fornitore(1,"Amazon","www.amazon.com","NYC", "USA");
        Produttore produttore = new Produttore(1,"Gazprom","www.gazprom.com","Mosca", "Russia");

        fornitoreDAO.update(fornitore);
        produttoreDAO.update(produttore);

        Assert.assertEquals("NYC", fornitore.getCitta());
        Assert.assertEquals("Mosca", produttore.getCitta());
    }

    @After
    public void tearDown() {
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();

        fornitoreDAO.remove(fornitore);
        produttoreDAO.remove(produttore);
    }
}
