package Test.DAOTest;

import DAO.Magazzino.MagazzinoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Magazzino;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagazzinoDAOTest {
    private UtenteDAO utenteDAO = UtenteDAO.getInstance();
    private PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    static PuntoVendita punto = new PuntoVendita(1,"bbb",1);
    static Manager manager = new Manager(1,"aaa","aaa","aaa","aaa","123","123",0,"aaa","aaa",punto);
    static Magazzino magazzino = new Magazzino(1,punto);
    static Magazzino magazzino2 = new Magazzino(2,punto);

    @Before
    public void setUp(){
        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
        magazzinoDAO.add(magazzino);
        magazzinoDAO.add(magazzino2);
    }
    @Test
    public void findById(){
        Assert.assertEquals(magazzinoDAO.findMagazzinoById(1).getPunto().getName(), "bbb");
    }
    @Test
    public void findAll(){
        Assert.assertEquals(2, magazzinoDAO.findAllMagazzino().size());
    }
    @Test
    public void findByPunto(){
        Assert.assertEquals("bbb", magazzinoDAO.findMagazzinoByPunto(punto).getPunto().getName());
    }
    @After
    public void tearDown(){
        magazzinoDAO.remove(magazzino);
        magazzinoDAO.remove(magazzino2);
        puntoVenditaDAO.remove(punto);
        utenteDAO.removeById(manager.getId());
    }
}
