package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Collocazione.CollocazioneDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
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

    static PuntoVendita punto = new PuntoVendita(1,"bbb",1);
    static Manager manager = new Manager(1,"aaa","aaa","aaa","aaa","123","123",0,"aaa","aaa", punto);
    static Produttore produttore = new Produttore(1,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");
    static ICategoria categoria = new Categoria(1,"Attrezzi", null);
    static Collocazione collocazione = new Collocazione(20,1,1);
    static Prodotto prodotto = new Prodotto(1, "Chiave inglese", 150F, "un frigorifero", categoria, produttore);

    static Magazzino magazzino = new Magazzino(1,punto);
    static ProdottiMagazzino prodottoMagazzino = new ProdottiMagazzino(magazzino, collocazione, prodotto, 13);


    @Before
    public void setUp(){
        collocazioneDAO.add(collocazione);
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore);
        utenteDAO.addManager(manager);
        puntoVenditaDAO.add(punto);
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
        Assert.assertEquals(20, prodottiMagazzinoDAO.findCollocazioneProdotto(magazzino, prodotto).getIdCollocazione());
    }

    @Test
    public void findQuantita(){
        Assert.assertEquals(13, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @Test
    public void update(){
        ProdottiMagazzino newProdottoMagazzino = new ProdottiMagazzino(magazzino, collocazione, prodotto, 1);
        prodottiMagazzinoDAO.update(newProdottoMagazzino);
        Assert.assertEquals(1, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @Test
    public void addQuantita(){
        prodottiMagazzinoDAO.addQuantita(magazzino, prodotto, 3);
        Assert.assertEquals(16, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @Test
    public void reduceQuantita(){
        prodottiMagazzinoDAO.reduceQuantita(magazzino, prodotto, 3);
        Assert.assertEquals(10, prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodotto));
    }

    @After
    public void tearDown(){
        prodottiMagazzinoDAO.remove(prodottoMagazzino);
        magazzinoDAO.remove(magazzino);
        articoloDAO.removeById(prodotto.getId());
        puntoVenditaDAO.remove(punto);
        utenteDAO.removeById(manager.getId());
        produttoreDAO.remove(produttore);
        categoriaDAO.remove(categoria);
        collocazioneDAO.remove(collocazione);
    }
}
