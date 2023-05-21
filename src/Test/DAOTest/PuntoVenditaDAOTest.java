package Test.DAOTest;

import DAO.PuntoVendita.IPuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import Model.Manager;
import Model.PuntoVendita;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PuntoVenditaDAOTest {
    static PuntoVendita myShopLecce = new PuntoVendita(9990, "MyShopTest", 9991);
    static Manager manager = new Manager(9991,"Name","Surname","manager@gmail.com","user","12345678", "22222222", 40, "Lecce", "Docente", myShopLecce);

    @Before
    public void setUp() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();

        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(myShopLecce);

    }
    @After
    public void tearDown() {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();

        puntoVenditaDAO.removeById(9990);
        utenteDAO.removeById(9991);
    }
    @Test
    public void findByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName("MyShopTest");
        Assert.assertEquals("MyShopTest", puntoVendita.getName());
    }
    @Test
    public void findAllTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
        Assert.assertEquals(2, punti.size());
    }
    @Test
    public void remove() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        int rowCount = puntoVenditaDAO.remove(myShopLecce);
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void updateTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = new PuntoVendita(9990, "MyShopTestUpdate", 9991);
        int rowCount = puntoVenditaDAO.update(puntoVendita);
        puntoVendita = puntoVenditaDAO.findByName("MyShopTestUpdate");
        Assert.assertEquals(9990, puntoVendita.getIdPuntoVendita());
        Assert.assertEquals("MyShopTestUpdate", puntoVendita.getName());
        Assert.assertEquals(9991, puntoVendita.getIdManager());
    }
}