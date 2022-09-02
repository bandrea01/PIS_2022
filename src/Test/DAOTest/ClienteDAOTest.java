package Test.DAOTest;

import Business.FactoryMethod.NotificationFactory;
import DAO.Categoria.CategoriaDAO;
import DAO.Categoria.ICategoriaDAO;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ClienteDAOTest {
    static PuntoVendita myShopLecce = new PuntoVendita(1, "MyShopLecce", 1);
    static Manager manager = new Manager(1,"Luca","Mainetti","luca.mainetti@gmail.com","lmainetti","352", "22222222", 40, "Lecce", "Docente", myShopLecce);
    static Cliente cliente = new Cliente(2,"Andrea","Barone","andrea.barone@gmail.com","bandrea","124", "111111", 21, "Lecce", "Studente", null, null, myShopLecce, NotificationFactory.TipoNotifica.PUSH, false);

    @Before
    public void setUp() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();

        utenteDAO.addManager(manager);
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