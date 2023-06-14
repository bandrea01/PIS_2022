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
    static PuntoVendita myShopLecce = new PuntoVendita(9999, "MyShopLecce", 9998);
    static Manager manager = new Manager(9998,"Luca","Mainetti","luca.mainetti@gmail.com","lmainetti","352", "22222222", 40, "Lecce", "Docente", myShopLecce);
    static Utente utente = new Utente(9999,"Andrea","Barone","andrea.barone@gmail.com","bandrea","124", "3333333", 21, "Lecce", "Studente");
    static Cliente cliente = new Cliente(utente, null, null, myShopLecce, NotificationFactory.TipoNotifica.PUSH, false);

    @Before
    public void setUp() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        utenteDAO.addManager(manager);
        utenteDAO.addUtente(utente);
        puntoVenditaDAO.add(myShopLecce);
        clientePuntoVenditaDAO.add(cliente);
    }
    @After
    public void tearDown() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        clientePuntoVenditaDAO.remove(cliente);
        puntoVenditaDAO.remove(myShopLecce);
        utenteDAO.removeById(9999);
        utenteDAO.removeById(manager.getId());
    }

    @Test
    public void isClienteRegistred(){
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        Assert.assertTrue(clientePuntoVenditaDAO.isClienteRegistred(cliente, myShopLecce));
    }

    @Test
    public void isClienteBanned(){
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        Assert.assertFalse(clientePuntoVenditaDAO.isClienteBanned(cliente, myShopLecce));
    }


}