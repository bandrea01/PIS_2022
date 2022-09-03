package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Feedback.FeedbackDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class FeedbackDAOTest {
    UtenteDAO utenteDAO = UtenteDAO.getInstance();
    CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();

    static Utente utente = new Utente(2,"bbb","bbb","bbb","bbb","321","321",1,"bbb","bbb");
    static PuntoVendita punto = new PuntoVendita(1,"bbb",1);

    static Manager manager = new Manager(1,"aaa","aaa","aaa","aaa","123","123",0,"aaa","aaa", punto);
    static ICategoria categoria = new Categoria(1,"Attrezzi", null);
    static Produttore produttore = new Produttore(1,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");
    static Prodotto prodotto = new Prodotto(1, "Prodotto", 200F, "Descrizione", categoria, produttore);
    static Feedback feedback1 = new Feedback(1,prodotto,utente,"Buono", Feedback.Gradimento.BUONO,"Grazie", manager);
    static Feedback feedback2 = new Feedback(2,prodotto,utente,"Utile", Feedback.Gradimento.ECCELLENTE,"Grazie", manager);

    @Before
    public void setUp(){
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore);
        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
        articoloDAO.addProdotto(prodotto);
        utenteDAO.addUtente(utente);
        feedbackDAO.add(feedback1);
        feedbackDAO.add(feedback2);
    }
    @Test
    public void findAllOfUtente(){
        ArrayList<Feedback> lista = feedbackDAO.findAllOfUtente(utente);
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals(prodotto.getName(), lista.get(0).getArticolo().getName());
        Assert.assertEquals("Grazie", lista.get(1).getRisposta());
    }
    @Test
    public void findAllOfArticolo(){
        ArrayList<Feedback> lista = feedbackDAO.findAllOfArticolo(prodotto);
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals(manager.getName(), lista.get(1).getManager().getName());
        Assert.assertEquals(manager.getName(), lista.get(1).getManager().getName());
    }
    @After
    public void tearDown(){
        feedbackDAO.remove(feedback1);
        feedbackDAO.remove(feedback2);
        puntoVenditaDAO.remove(punto);
        articoloDAO.removeById(prodotto.getId());
        produttoreDAO.remove(produttore);
        categoriaDAO.remove(categoria);
        utenteDAO.removeById(manager.getId());
        utenteDAO.removeById(utente.getId());
    }

}
