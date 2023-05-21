package Test.DAOTest;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Ordine.OrdineDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

public class OrdineDAOTest {
    CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
    ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
    UtenteDAO utenteDAO = UtenteDAO.getInstance();
    OrdineDAO ordineDAO = OrdineDAO.getInstance();
    ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    static ICategoria categoria1 = new Categoria(9998,"categoria", null);
    static ICategoria categoria2 = new Categoria(9999,"Attrezzi", categoria1);
    static Fornitore fornitore = new Fornitore(9999,"fornitore","www.fornitore.com","city", "country");
    static Produttore produttore = new Produttore(9999,"produttore","www.produttore.com","city", "country");
    static Utente utente = new Utente(9999,"Francesco","Rossi","francesco.rossi@gmail.com","frossi","12345678","11111111", 21, "Roma", "Studente");
    static Prodotto sottoProdotto1 = new Prodotto(9996, "Chiave Inglese", 99F, "una chiave", categoria2, produttore);
    static Prodotto sottoProdotto2 = new Prodotto(9997, "Cacciavite", 99F, "un cacciavite", categoria2, produttore);
    static Servizio servizio = new Servizio(9998,"Montaggio", 99F, "montaggio cucina", categoria2, fornitore);
    static Prodotto prodotto;
    static Ordine ordine;

    @Before
    public void setUp(){
        ArrayList<Prodotto> list = new ArrayList<>();
        list.add(sottoProdotto1);
        list.add(sottoProdotto2);
        prodotto = new Prodotto(9999, "Kit", "un kit", categoria2, produttore, list);

        ProdottoOrdine prodottoOrdine1 = new ProdottoOrdine(prodotto,1);
        ProdottoOrdine prodottoOrdine2 = new ProdottoOrdine(sottoProdotto1,2);
        ArrayList<ProdottoOrdine> prodottiOrdine = new ArrayList<>();
        prodottiOrdine.add(prodottoOrdine1);
        prodottiOrdine.add(prodottoOrdine2);
        ArrayList<Servizio> servizi = new ArrayList<>();
        servizi.add(servizio);
        ordine = new Ordine(9999, utente, prodottiOrdine , servizi, Date.valueOf("1000-01-01"), Ordine.StatoOrdine.DA_PAGARE);

        categoriaDAO.add(categoria1);
        categoriaDAO.add(categoria2);
        fornitoreDAO.add(fornitore);
        produttoreDAO.add(produttore);
        utenteDAO.addUtente(utente);
        articoloDAO.addProdotto(sottoProdotto1);
        articoloDAO.addProdotto(sottoProdotto2);
        articoloDAO.addProdotto(prodotto);
        articoloDAO.addServizio(servizio);
        ordineDAO.add(ordine);
    }

    @Test
    public void findOrdineById(){
        Assert.assertEquals(Date.valueOf("1000-01-01"), ordineDAO.findOrdineById(9999).getDataCreazione());
        Assert.assertEquals(utente.getEmail(), ordineDAO.findOrdineById(9999).getUtente().getEmail());
    }
    @Test
    public void findAllOfUtente(){
        ArrayList<Ordine> ordini = ordineDAO.findAllOfUtente(utente);
        Assert.assertEquals(1, ordini.size());
    }
    @Test
    public void isPagato(){
        Assert.assertFalse(ordineDAO.isPagato(ordine));
    }
    @Test
    public void paga(){
        ordineDAO.paga(ordine);
        Assert.assertTrue(ordineDAO.isPagato(ordine));
    }
    @After
    public void tearDown(){
        ordineDAO.remove(ordine);
        articoloDAO.removeById(prodotto.getId());
        articoloDAO.removeById(sottoProdotto1.getId());
        articoloDAO.removeById(sottoProdotto2.getId());
        articoloDAO.removeById(servizio.getId());
        utenteDAO.removeById(utente.getId());
        produttoreDAO.remove(produttore);
        fornitoreDAO.remove(fornitore);
        categoriaDAO.remove(categoria1);
        categoriaDAO.remove(categoria2);

    }
}