package Business;

import DAO.Articolo.ArticoloDAO;
import DAO.Articolo.ImmagineDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.Servizio.ServizioDAO;
import Model.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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

    public int modifyArticolo(String nome, String nomeMod, float prezzoMod, String descrizioneMod, String categoriaMod, String produttoreFornitoreMod) {
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        int idArticolo = articoloDAO.findByName(nome).getId();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        if (articoloDAO.isProdotto(idArticolo)) {
            ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
            Prodotto prodotto = prodottoDAO.findById(idArticolo);
            if (!nomeMod.isEmpty()) {
                prodotto.setName(nomeMod);
            }
            if (prezzoMod != 0) {
                prodotto.setPrezzo(prezzoMod);
            }
            if (!descrizioneMod.isEmpty()) {
                prodotto.setDescrizione(descrizioneMod);
            }
            if (!categoriaMod.isEmpty()) {
                if ("Select a element".equals(categoriaMod)) {
                    categoriaMod = categoriaDAO.findById(idArticolo).getName();
                }
                prodotto.setCategoria(categoriaDAO.findByName(categoriaMod));
            }
            if (!produttoreFornitoreMod.isEmpty()) {
                if ("Select a productor".equals(produttoreFornitoreMod)) {
                    produttoreFornitoreMod = prodottoDAO.findById(idArticolo).getProduttore().getNome();
                }
                prodotto.setProduttore(produttoreDAO.findByName(produttoreFornitoreMod));
            }
            articoloDAO.updateProdotto(prodotto);
            return 1;
        }
        if (articoloDAO.isServizio(idArticolo)) {
            ServizioDAO servizioDAO = ServizioDAO.getInstance();
            Servizio servizio = servizioDAO.findById(idArticolo);
            if (!nomeMod.isEmpty()) {
                servizio.setName(nomeMod);
            }
            if (prezzoMod != 0) {
                servizio.setPrezzo(prezzoMod);
            }
            if (!descrizioneMod.isEmpty()) {
                servizio.setDescrizione(descrizioneMod);
            }
            if (!categoriaMod.isEmpty()) {
                if ("Select an element".equals(categoriaMod)) {
                    categoriaMod = articoloDAO.findById(idArticolo).getCategoria().getName();
                }
                servizio.setCategoria(categoriaDAO.findByName(categoriaMod));
            }
            if (!produttoreFornitoreMod.isEmpty()) {
                if ("Select a supplier".equals(produttoreFornitoreMod)) {
                    produttoreFornitoreMod = servizioDAO.findById(idArticolo).getFornitore().getNome();
                }
                servizio.setFornitore(fornitoreDAO.findByName(produttoreFornitoreMod));
            }
            articoloDAO.updateServizio(servizio);
            return 1;
        }
        return 0;
    }

    public int addArticolo(int id, String nome, float prezzo, String descrizione, String nomeCategoria, String isprodottoServizio, String produttoreFornitore, String sopraProdotto, String pathImmagine) {
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

        Categoria categoria = categoriaDAO.findById(idCategoria);


        //VEDO SE è PROD O SERVIZIO E RIEMPIO IL DAO
        String isProdotto = "Product"; String isServizio = "Service";
        Prodotto prodotto = new Prodotto();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        Produttore produttore = new Produttore();


        Servizio servizio = new Servizio();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        Fornitore fornitore = new Fornitore();
        ImmagineDAO immagineDAO = ImmagineDAO.getInstance();
        ArrayList<Immagine> immagini = immagineDAO.findAll();
        int idImmagine = immagini.size();

        if (isProdotto.equalsIgnoreCase(isprodottoServizio)) {
            if (pathImmagine.isEmpty()) {
                return 4;
            }
            prodotto.setName(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setId(id);
            prodotto.setCategoria(categoria);
            prodotto.setDescrizione(descrizione);
            prodotto.setPathImmagine(pathImmagine);
            produttore = produttoreDAO.findByName(produttoreFornitore);
            prodotto.setProduttore(produttore);
            Random random = new Random();
            Immagine immagine = new Immagine(idImmagine + random.nextInt(10), prodotto.getId(), pathImmagine);
            articoloDAO.addProdotto(prodotto);
            immagineDAO.add(immagine);
            if (!"Nothing".equalsIgnoreCase(sopraProdotto)) {
                Prodotto prodottoPadre = prodottoDAO.findByName(sopraProdotto);
                if (prodottoPadre.getSottoProdotti() == null) {
                    ArrayList<Prodotto> sottoProdotti = new ArrayList<>();
                    sottoProdotti.add(prodotto);
                    prodottoPadre.setSottoProdotti(sottoProdotti);
                    prodottoDAO.updateSottoProdotti(prodottoPadre);
                } else {
                    prodottoPadre.getSottoProdotti().add(prodotto);
                    prodottoDAO.updateSottoProdotti(prodottoPadre);
                }
            }
            return 2;
        } else if (isServizio.equalsIgnoreCase(isprodottoServizio)) {
            servizio.setName(nome);
            servizio.setPrezzo(prezzo);
            servizio.setId(id);
            servizio.setCategoria(categoria);
            servizio.setDescrizione(descrizione);
            fornitore = fornitoreDAO.findByName(produttoreFornitore);
            servizio.setFornitore(fornitore);
            servizio.setPathImmagine("servizio.png");
            articoloDAO.addServizio(servizio);
            return 3;
        }



        return -1;
    }

}
