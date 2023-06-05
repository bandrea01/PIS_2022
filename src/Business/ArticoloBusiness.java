package Business;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.Servizio.ServizioDAO;
import Model.*;

import java.awt.event.ActionListener;
import java.sql.Blob;

public class ArticoloBusiness {
    private static ArticoloBusiness instance;

    public static synchronized ArticoloBusiness getInstance() {
        if (instance == null) {
            instance = new ArticoloBusiness();
        }
        return instance;
    }
    public ActionListener showImagePopup() {
        return null;
    }


    public int addArticolo(int id, String nome, float prezzo, String descrizione, String nomeCategoria, String isprodottoServizio, String produttoreFornitore) {
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

        //controllo se i campi sono vuoti
        if (id == 0 || nome.isEmpty() || prezzo == 0 || descrizione.isEmpty() || nomeCategoria.isEmpty() || isprodottoServizio.isEmpty() || produttoreFornitore.isEmpty()) {
            return 0;
        }

        //CONTROLLI SUL DAO
        //controllo se esiste già l'articolo
        if (articoloDAO.articoloExist(id)) {
            return 1;
        }
        if (articoloDAO.articoloNameExist(nome)) {
            return 1;
        }

        int idCategoria = categoriaDAO.findId(nomeCategoria);

        Categoria categoria = (Categoria) categoriaDAO.findById(idCategoria);


        //VEDO SE è PROD O SERVIZIO E RIEMPIO IL DAO
        String isProdotto = "Prodotto"; String isServizio = "Servizio";
        Prodotto prodotto = new Prodotto();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        Produttore produttore = new Produttore();

        Servizio servizio = new Servizio();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        Fornitore fornitore = new Fornitore();

        if (isProdotto.equalsIgnoreCase(isprodottoServizio)) {
            prodotto.setName(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setId(id);
            prodotto.setCategoria(categoria);
            prodotto.setDescrizione(descrizione);
            produttore = produttoreDAO.findByName(produttoreFornitore);
            prodotto.setProduttore(produttore);
            articoloDAO.addProdotto(prodotto);
            return 2;
        } else if (isServizio.equalsIgnoreCase(isprodottoServizio)) {
            servizio.setName(nome);
            servizio.setPrezzo(prezzo);
            servizio.setId(id);
            servizio.setCategoria(categoria);
            servizio.setDescrizione(descrizione);
            fornitore = fornitoreDAO.findByName(produttoreFornitore);
            servizio.setFornitore(fornitore);
            articoloDAO.addServizio(servizio);
            return 3;
        }



        return -1;
    }

}
