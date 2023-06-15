package Test.DAOTest;

import Business.Bridge.DocumentoOrdine;
import Business.Bridge.PdfAPI;
import Business.Bridge.PdfBoxAPI;
import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Collocazione.CollocazioneDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.Ordine.OrdineDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class InvioOrdineTest {

    static Ordine ordine;
    static Categoria categoria = new Categoria(1001, "Cucina", null);
    static Fornitore fornitore = new Fornitore(1001,"Amazon", "www.amazon.com", "Seattle", "USA");
    static Produttore produttore1 = new Produttore(1001,"Gazprom","www.gazprom.com","San Pietroburgo", "Russia");
    static Prodotto prodotto = new Prodotto(9991, "Frigorifero", 150F, "un frigorifero", categoria, produttore1);
    static Servizio servizio = new Servizio(9992,"Montaggio", 300F, "montaggio cucina", categoria, fornitore);
   //
    static PuntoVendita puntoVendita = new PuntoVendita(9999, "Punto test",9992);
    static Magazzino magazzino = new Magazzino(9991,puntoVendita);
//
    public ArrayList<ProdottoOrdine> arrayProdotti = new ArrayList<>(1);
    public ArrayList<Servizio> arrayServizio = new ArrayList<>(1);

    static Collocazione collocazione = new Collocazione(999,999,999);

    static Utente utente = new Utente(9991,"Francesco","Rossi","francesco.rossi@gmail.com","frossi","12345678","11111111", 21, "Roma", "Studente");
    //
    static Utente manager = new Utente(9992, "Manager test", "Test", "test@gmail.com", "test", "12345678", "121212121212", 23, "Roma", "Studente");
    @Before
    public void setUp(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        OrdineDAO ordineDAO = OrdineDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
        CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();

        arrayProdotti.add(new ProdottoOrdine(prodotto, 3));
        arrayServizio.add(servizio);

        Date data = Date.valueOf(LocalDate.now());

        ordine = new Ordine(9999, utente, arrayProdotti, arrayServizio, data, Ordine.StatoOrdine.DA_PAGARE);

        utenteDAO.addUtente(utente);
        utenteDAO.addUtente(manager);
        categoriaDAO.add(categoria);
        produttoreDAO.add(produttore1);
        fornitoreDAO.add(fornitore);
        articoloDAO.addProdotto(prodotto);
        articoloDAO.addServizio(servizio);
        ordineDAO.add(ordine);
        puntoVenditaDAO.add(puntoVendita);
        magazzinoDAO.add(magazzino);
    }

    @Test
    public void invioOrdineEmail(){
        PdfAPI api = new PdfBoxAPI();
        DocumentoOrdine documentoOrdine = new DocumentoOrdine (ordine, api);
        documentoOrdine.invia("andrea.barone1401@gmail.com");
    }
    @After
    public void tearDown(){
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        OrdineDAO ordineDAO = OrdineDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();

        ordineDAO.removeById(9999);
        articoloDAO.removeById(prodotto.getId());
        articoloDAO.removeById(servizio.getId());
        produttoreDAO.remove(produttore1);
        fornitoreDAO.remove(fornitore);
        categoriaDAO.remove(categoria);
        utenteDAO.removeById(9991);
    }
}
