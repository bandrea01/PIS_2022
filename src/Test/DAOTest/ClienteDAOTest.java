package Test.DAOTest;

import Business.FactoryMethod.NotificationFactory;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Cliente;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClienteDAOTest {
    static PuntoVendita punto = new PuntoVendita(9999, "punto", 9991);
    static Manager manager = new Manager(9991,"name","surname","manager@gmail.com","username","12345678","11111111",30,"city","DOcente", punto);
    static Utente utente = new Utente(9992,"Francesco","Rossi","francesco.rossi@gmail.com","frossi","12345678","11111111", 21, "Roma", "Studente");

    static Cliente cliente = new Cliente(utente, null, null, punto, NotificationFactory.TipoNotifica.PUSH, false);

    @Before
    public void setUp() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
        clientePuntoVenditaDAO.add(cliente);

    }
    @After
    public void tearDown() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        clientePuntoVenditaDAO.remove(cliente);
        puntoVenditaDAO.remove(punto);
        utenteDAO.removeById(manager.getId());
    }

    @Test
    public void isClienteRegistred(){
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        Assert.assertTrue(clientePuntoVenditaDAO.isClienteRegistred(cliente, punto));
    }

    @Test
    public void isClienteBanned(){
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        Assert.assertFalse(clientePuntoVenditaDAO.isClienteBanned(cliente, punto));
    }


}