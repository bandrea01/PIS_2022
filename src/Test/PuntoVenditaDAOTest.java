package Test;

import DAO.PuntoVendita.IPuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.PuntoVendita;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PuntoVenditaDAOTest {
    @Before
    public void setUp() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        puntoVenditaDAO.add(new PuntoVendita("JuventusStore", "Torino", 1, 0));
    }
    @After
    public void tearDown() {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        puntoVenditaDAO.removeByName("JuventusStore");
    }
    @Test
    public void findByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName("JuventusStore");
        Assert.assertEquals("JuventusStore", puntoVendita.getName());
    }
    @Test
    public void findAllTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
        Assert.assertEquals(4, punti.size());
    }
    @Test
    public void removeByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        int rowCount = puntoVenditaDAO.removeByName("JuventusStore");
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void updateTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = new PuntoVendita("JuventusStore", "Torino",1, 0);
        int rowCount = puntoVenditaDAO.update(puntoVendita);
        puntoVendita = puntoVenditaDAO.findByName("JuventusStore");
        Assert.assertEquals("Torino", puntoVendita.getCity());
        Assert.assertEquals("JuventusStore", puntoVendita.getName());
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void getIdByNameTest() {
        IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        int id = puntoVenditaDAO.getIdByName("MyShopLecce");
        Assert.assertEquals(1, id);
    }
}