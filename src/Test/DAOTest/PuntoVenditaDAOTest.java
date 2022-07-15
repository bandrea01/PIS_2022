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
    static PuntoVendita myShopLecce = new PuntoVendita(1, "MyShopLecce", 1);
    static Manager manager = new Manager(1,"Luca","Mainetti","luca.mainetti@gmail.com","lmainetti","352", "22222222", 40, "Lecce", "Docente", myShopLecce);

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

        puntoVenditaDAO.removeById(1);
        utenteDAO.removeById(1);
    }
    @Test
    public void findByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName("MyShopLecce");
        Assert.assertEquals("MyShopLecce", puntoVendita.getName());
    }
    @Test
    public void findAllTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
        Assert.assertEquals(1, punti.size());
    }
    @Test
    public void removeByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        int rowCount = puntoVenditaDAO.removeByName("MyShopLecce");
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void removeByIdTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        int rowCount = puntoVenditaDAO.removeById(1);
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void updateTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = new PuntoVendita(1, "MyShopBari", 1);
        int rowCount = puntoVenditaDAO.update(puntoVendita);
        puntoVendita = puntoVenditaDAO.findByName("MyShopBari");
        Assert.assertEquals(1, puntoVendita.getId());
        Assert.assertEquals("MyShopBari", puntoVendita.getName());
        Assert.assertEquals(1, puntoVendita.getIdMan());
    }
}