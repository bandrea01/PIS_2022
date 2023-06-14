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
    static PuntoVendita myShopLecce = new PuntoVendita(9990, "MyShopLecce", 9992);
    static Utente utente = new Utente(9991,"Francesco","Rossi","francesco.rossi@gmail.com","frossi","12345678","3333333", 21, "Roma", "Studente");
    static Manager manager = new Manager(9992,"Maria","Ferri","maria.ferri@gmail.com","mferri","12345678", "22222222", 40, "Lecce", "Docente", myShopLecce);
    static Amministratore amministratore = new Amministratore(9993,"Admin","Admin","admin@gmail.com","adminTest","12345678","3202944654", 21, "Poggiardo", "Studente");

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
        utenteDAO.removeById(9993);
        utenteDAO.removeById(9992);
        utenteDAO.removeById(9991);
    }

    @Test
    public void findByIdTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente1 = utenteDAO.findById(9991);
        Assert.assertEquals("Rossi", utente1.getSurname());
        Utente utente2 = utenteDAO.findById(9992);
        Assert.assertEquals("Maria", utente2.getName());
        Utente utente3 = utenteDAO.findById(9993);
        Assert.assertEquals("Studente", utente3.getJob());
    }
    @Test
    public void findByUsernameTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = utenteDAO.findByUsername("frossi");
        Assert.assertEquals("Studente", utente.getJob());
    }
    @Test
    public void findAllTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        ArrayList<Utente> utenti = utenteDAO.findAll();
        Assert.assertTrue(utenti.size() >= 3);
    }
    @Test
    public void updateTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = new Utente(9991,"New","Utente","user@gmail.com","user","12345678","111111111", 21, "Poggiardo", "Studente");
        int rowCount = utenteDAO.update(utente);
        utente = utenteDAO.findByUsername("user");
        Assert.assertEquals("user@gmail.com", utente.getEmail());
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void userExists(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.userExist("mferri"));
    }
    @Test
    public void emailExist(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.emailExist("admin@gmail.com"));
    }
    @Test
    public void phoneExist(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.phoneExist("3333333"));
    }
    @Test
    public void checkCredentials(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Assert.assertTrue(utenteDAO.checkCredentials("adminTest", "12345678"));
    }
    @Test
    public void getByUsername(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Manager manager = utenteDAO.getManagerByUsername("mferri");
        Amministratore admin = utenteDAO.getAdminByUsername("adminTest");
        Assert.assertEquals("12345678", admin.getPassword());
        Assert.assertEquals("Docente", manager.getJob());
    }
    @Test
    public void getById(){
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Manager manager = utenteDAO.getManagerById(9992);
        Amministratore admin = utenteDAO.getAdminById(9993);
        Assert.assertEquals("12345678", admin.getPassword());
        Assert.assertEquals("Docente", manager.getJob());
    }
}