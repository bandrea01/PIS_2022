package Test;

import DAO.Acquirente.AcquirenteDAO;
import DAO.Acquirente.IAcquirenteDAO;
import Model.Acquirente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AcquirenteDAOTest {
    @Before
    public void setUp() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        acquirenteDAO.add(new Acquirente("Roberto", "Baggio", "rbaggio", "robertobaggio@gmail.com", "111", 111, 50, "Brescia", "calciatore"));
    }
    @After
    public void tearDown() {
        IAcquirenteDAO utenteDAO = AcquirenteDAO.getInstance();
        utenteDAO.removeByUsername("rbaggio");
    }
    @Test
    public void findByUsernameTest() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        Acquirente acquirente = acquirenteDAO.findByUsername("rbaggio");
        Assert.assertEquals("Roberto", acquirente.getName());
    }
    @Test
    public void findByIdTest() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        Acquirente acquirente = acquirenteDAO.findById(1);
        Assert.assertEquals("Andrea", acquirente.getName());
    }
    @Test
    public void getIdByUsername(){
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        int id = acquirenteDAO.getIdByUsername("abarone");
        Assert.assertEquals(1, id);
    }
    @Test
    public void findAllTest() {
        IAcquirenteDAO utenteDAO = AcquirenteDAO.getInstance();
        ArrayList<Acquirente> acquirenti = utenteDAO.findAll();
        Assert.assertEquals(11, acquirenti.size());
    }
    @Test
    public void removeByUsernameTest() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        int rowCount = acquirenteDAO.removeByUsername("rbaggio");
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void updateTest() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        Acquirente acquirente = new Acquirente("Roberto", "Baggio", "rbaggio", "robertobaggio@gmail.com", "111", 111111, 50, "Brescia", "calciatore");
        int rowCount = acquirenteDAO.update(acquirente);
        acquirente = acquirenteDAO.findByUsername("rbaggio");
        Assert.assertEquals("Brescia", acquirente.getCity());
        Assert.assertEquals(50, acquirente.getAge());
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void removeByIdTest() {
        IAcquirenteDAO acquirenteDAO = AcquirenteDAO.getInstance();
        int rowCount = acquirenteDAO.removeById(acquirenteDAO.getIdByUsername("rbaggio"));
        Assert.assertEquals(1, rowCount);
    }
}