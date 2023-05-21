package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Collocazione.CollocazioneDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class ProdottoMagazzinoDAOTest {
    UtenteDAO utenteDAO = UtenteDAO.getInstance();
    ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();
    ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();

    static PuntoVendita puntoVendita = new PuntoVendita(9990,"MyShopTest",9991);
    static Manager manager = new Manager(9991,"Name","Surname","manager@gmail.com","user","12345678", "22222222", 40, "Lecce", "Docente", puntoVendita );
    static Produttore produttore = new Produttore(9991,"produttore","www.produttore.com","city", "country");
    static ICategoria categoria = new Categoria(9990,"Attrezzi", null);
    static Collocazione collocazione = new Collocazione(9999,9999,9999);
    static Prodotto prodotto = new Prodotto(9999, "Chiave inglese", 150F, "una chiave inglese", categoria, produttore);

    static Magazzino magazzino = new Magazzino(9999,puntoVendita);
    static ProdottiMagazzino prodottoMagazzino = new ProdottiMagazzino(magazzino, collocazione, prodotto, 99);


    @Before
    public void setUp(){
        collocazioneDAO.add(collocazione);
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore);
        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(puntoVendita);
        articoloDAO.addProdotto(prodotto);
        magazzinoDAO.add(magazzino);
        prodottiMagazzinoDAO.add(prodottoMagazzino);
    }

    @Test
    public void findAllProdottiByMagazzino(){
        ArrayList<ProdottiMagazzino> lista = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzino);
        Assert.assertEquals("Chiave inglese", lista.get(0).getProdotto().getName());
    }

    @Test
    public void findCollocazione(){
        Assert.assertEquals(9999, prodottiMagazzinoDAO.findCollocazioneProdotto(magazzino, prodotto).getIdCollocazione());
    }

    @Test
    public void findQuantita(){
        Assert.assertEquals(99, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @Test
    public void update(){
        ProdottiMagazzino newProdottoMagazzino = new ProdottiMagazzino(magazzino, collocazione, prodotto, 98);
        prodottiMagazzinoDAO.update(newProdottoMagazzino);
        Assert.assertEquals(98, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
        prodottiMagazzinoDAO.remove(newProdottoMagazzino);
    }

    @Test
    public void addQuantita(){
        prodottiMagazzinoDAO.addQuantita(magazzino, prodotto, 1);
        Assert.assertEquals(100, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @Test
    public void reduceQuantita(){
        prodottiMagazzinoDAO.reduceQuantita(magazzino, prodotto, 1);
        Assert.assertEquals(98, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @After
    public void tearDown(){
        prodottiMagazzinoDAO.remove(prodottoMagazzino);
        magazzinoDAO.remove(magazzino);
        articoloDAO.removeById(prodotto.getId());
        puntoVenditaDAO.remove(puntoVendita);
        utenteDAO.removeById(manager.getId());
        produttoreDAO.remove(produttore);
        categoriaDAO.remove(categoria);
        collocazioneDAO.remove(collocazione);
    }
}
