package Test.DAOTest;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import Model.Amministratore;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class UtenteDAOTest {
    static PuntoVendita myShopLecce = new PuntoVendita(1, "MyShopLecce", 3);
    static Utente utente = new Utente(1,"Cristian","Scarciglia","cristian.scarciglia@gmail.com","cristians","111","11111111", 21, "Nociglia", "Studente");
    static Manager manager = new Manager(3,"Luca","Mainetti","luca.mainetti@gmail.com","lmainetti","352", "22222222", 40, "Lecce", "Docente", myShopLecce);
    static Amministratore amministratore = new Amministratore(2,"Andrea","Barone","andrea.barone@gmail.com","bandrea","123","3202944654", 21, "Poggiardo", "Studente");

    @Before
    public void setUp() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();

        utenteDAO.addUtente(utente);
        utenteDAO.addManager(manager);
        utenteDAO.addAdmin(amministratore);
        puntoVenditaDAO.add(myShopLecce);
    }
    @After
    public void tearDown() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();

        puntoVenditaDAO.remove(myShopLecce);
        utenteDAO.removeById(2);
        utenteDAO.removeById(3);
        utenteDAO.removeByUsername("cristians");
    }

    @Test
    public void findByIdTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente1 = utenteDAO.findById(1);
        Assert.assertEquals("Scarciglia", utente1.getSurname());
        Utente utente2 = utenteDAO.findById(2);
        Assert.assertEquals("Andrea", utente2.getName());
        Utente utente3 = utenteDAO.findById(3);
        Assert.assertEquals("Docente", utente3.getJob());
    }
    @Test
    public void findByUsernameTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = utenteDAO.findByUsername("bandrea");
        Assert.assertEquals("Studente", utente.getJob());
    }
    @Test
    public void findAllTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        ArrayList<Utente> utenti = utenteDAO.findAll();
        Assert.assertEquals(3, utenti.size());
    }
    @Test
    public void updateTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = new Utente(2,"Andrea","Barone","andrea.barone@gmail.com","bandrea","123","111111111", 21, "Poggiardo", "Studente");
        int rowCount = utenteDAO.update(utente);
        utente = utenteDAO.findByUsername("bandrea");
        Assert.assertEquals("andrea.barone@gmail.com", utente.getEmail());
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void userExists(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.userExist("lmainetti"));
    }
    @Test
    public void emailExist(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.emailExist("andrea.barone@gmail.com"));
    }
    @Test
    public void phoneExist(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.phoneExist("11111111"));
    }
    @Test
    public void checkCredentials(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.checkCredentials("bandrea", "123"));
    }
    @Test
    public void getByUsername(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Manager manager = utenteDAO.getManagerByUsername("lmainetti");
        Amministratore admin = utenteDAO.getAdminByUsername("bandrea");
        Assert.assertEquals("123", admin.getPassword());
        Assert.assertEquals("Docente", manager.getJob());
    }
    @Test
    public void getById(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Manager manager = utenteDAO.getManagerById(3);
        Amministratore admin = utenteDAO.getAdminById(2);
        Assert.assertEquals("123", admin.getPassword());
        Assert.assertEquals("Docente", manager.getJob());
    }
}