package View.Listener;

import Business.ArticoloBusiness;
import Business.CategoriaBusiness;
import Business.FornitoreBusiness;
import Business.ProduttoreBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.Servizio.ServizioDAO;
import Model.ICategoria;
import Model.Prodotto;
import Model.Servizio;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManageArticlesListener implements ActionListener {
    public final static String ADD_ARTICLE = "addarticle-btn";
    public final static String ADD_CATEGORY = "addcategory-btn";
    public final static String DELETE_ARTICLE = "deletearticle-btn";
    public final static String DELETE_CATEGORY = "deletecategory-btn";
    public final static String MODIFY_ARTICLE = "modifyarticle-btn";
    public final static String MODIFY_CATEGORY = "modifycategory-btn";
    public final static String ADD_PRODUCTOR = "addproductor-btn";
    public final static String ADD_SUPPLIER = "addsupplier-btn";
    public final static String DELETE_PRODUCTOR = "deleteproductor-btn";
    public final static String DELETE_SUPPLIER = "deletesupplier-btn";

    private JTextField id;
    private JTextField nome;
    private JTextField prezzo;
    private JTextField descrizione;
    private WideComboBox categoria;
    private WideComboBox sopraProdotto;
    private WideComboBox articoli;

    private WideComboBox isProdottoServizio;
    private WideComboBox produttoreFornitore;
    private WideComboBox categoriaPadre;

    private JTextField sito;
    private JTextField citta;
    private JTextField nazione;


    private JPanel panel;

    public ManageArticlesListener(JTextField id, JTextField nome, JTextField prezzo, JTextField descrizione, WideComboBox categoria, WideComboBox isProdottoServizio, WideComboBox produttoreFornitore, WideComboBox sopraProdotto) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.isProdottoServizio = isProdottoServizio;
        this.produttoreFornitore = produttoreFornitore;
        this.sopraProdotto = sopraProdotto;
    }

    public ManageArticlesListener(JTextField nome, WideComboBox categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public ManageArticlesListener(WideComboBox articoli) {
        this.articoli = articoli;
    }

    //modifica categoria
    public ManageArticlesListener(JTextField nome, WideComboBox categoria, WideComboBox categoriaPadre) {
        this.nome = nome;
        this.categoria = categoria;
        this.categoriaPadre = categoriaPadre;
    }

    public ManageArticlesListener(JPanel panel) {
        this.panel = panel;
    }

    public ManageArticlesListener(WideComboBox articoli, JTextField nome, JTextField prezzo, JTextField descrizione, WideComboBox categoria, WideComboBox produttoreFornitore) {
        this.articoli = articoli;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.produttoreFornitore = produttoreFornitore;
    }

    public ManageArticlesListener(JTextField nome, JTextField sito, JTextField citta, JTextField nazione) {
        this.nome = nome;
        this.sito = sito;
        this.citta = citta;
        this.nazione = nazione;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (ADD_ARTICLE.equalsIgnoreCase(action)) {
            if (id.getText().isEmpty() || prezzo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null ,"Sono richiesti tutti i campi");
                return;
            }
            int idInt =Integer.parseInt(id.getText());
            String name = nome.getText();
            float price = Float.parseFloat(prezzo.getText());
            String description = descrizione.getText();
            String category = categoria.getSelectedItem().toString();
            String isProductService = isProdottoServizio.getSelectedItem().toString();
            String productorSupplier = produttoreFornitore.getSelectedItem().toString();
            String overProduct = null;
            if (sopraProdotto.getSelectedItem() != null) {
                overProduct = sopraProdotto.getSelectedItem().toString();
            }
            String selection = "Seleziona un elemento";
            ArticoloBusiness articoloBusiness = ArticoloBusiness.getInstance();
            if (category.equalsIgnoreCase(selection) || isProductService.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase("Seleziona un fornitore") || productorSupplier.equalsIgnoreCase("Seleziona un produttore")) {
                JOptionPane.showMessageDialog(null ,"Sono richiesti tutti i campi");
                return;
            }
            int result = articoloBusiness.addArticolo(idInt, name, price, description,category, isProductService, productorSupplier, overProduct);

            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(null ,"Sono richiesti tutti i campi");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "L'articolo esiste già");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Il prodotto è stato caricato correttamente");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Il servizio è stato caricato correttamente");
                    break;
                case -1:
                    JOptionPane.showMessageDialog(null, "Errore");
                    break;
            }
        }
        if (ADD_CATEGORY.equals(e.getActionCommand())) {
            if (nome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null ,"Sono richiesti tutti i campi");
                return;
            }
            CategoriaBusiness categoriaBusiness = CategoriaBusiness.getInstance();
            String name = nome.getText();
            CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
            String nessunaCategoriaPadre = "Nessuna";

            if (nessunaCategoriaPadre.equalsIgnoreCase(categoria.getSelectedItem().toString())) {
                int idCategoriaPadre = 0;
                if (categoriaBusiness.addCategory(name, idCategoriaPadre) == 1) {
                    JOptionPane.showMessageDialog(null, "La categoria esiste già");
                    return;
                }
                JOptionPane.showMessageDialog(null, "La categoria è stata inserita correttamente");
                return;
            }

            int idCategoriaPadre = categoriaDAO.findId(categoria.getSelectedItem().toString());
            if (categoriaBusiness.addCategory(name, idCategoriaPadre) == 1) {
                JOptionPane.showMessageDialog(null, "La categoria esiste già");
                return;
            }
            JOptionPane.showMessageDialog(null, "La categoria è stata inserita correttamente");
        }
        if (DELETE_ARTICLE.equals(e.getActionCommand())) {
            String nomeArticolo = articoli.getSelectedItem().toString();
            if (nomeArticolo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Scegli un articolo");
                return;
            }
            ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
            int idArticolo = articoloDAO.findByName(nomeArticolo).getId();
            articoloDAO.removeById(idArticolo);
            JOptionPane.showMessageDialog(null, "L'articolo è stato cancellato correttamente");
        }
        if (DELETE_CATEGORY.equals(e.getActionCommand())) {
            String nomeCategoria = articoli.getSelectedItem().toString();
            if (nomeCategoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Scegli una categoria");
                return;
            }
            CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
            int idCategoria = categoriaDAO.findByName(nomeCategoria).getId();
            ArrayList<ICategoria> sottoCategorie = categoriaDAO.findAllSottoCategorie(idCategoria);
            if (sottoCategorie.size() != 0) {
                JOptionPane.showMessageDialog(null, "Cancella prima le sottocategorie");
                return;
            }
            categoriaDAO.remove(categoriaDAO.findById(idCategoria));
            JOptionPane.showMessageDialog(null, "La categoria è stata eliminata correttamente");
        }
        if (MODIFY_ARTICLE.equals(e.getActionCommand())) {
            String nomeArticolo = articoli.getSelectedItem().toString();
            if (nomeArticolo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Scegli l'articolo da modificare");
                return;
            }
            ArticoloBusiness articoloBusiness = new ArticoloBusiness();

            String name = nome.getText();
            float price = 0;
            if (!prezzo.getText().isEmpty()) {
                price = Float.parseFloat(prezzo.getText());
            }
            String description = descrizione.getText();
            String category = categoria.getSelectedItem().toString();
            String productorSupplier = produttoreFornitore.getSelectedItem().toString();

            if (articoloBusiness.modifyArticolo(nomeArticolo, name, price, description, category, productorSupplier) == 1) {
                JOptionPane.showMessageDialog(null, "L'articolo è stato modificato correttamente");
            }
        }
        if (MODIFY_CATEGORY.equals(e.getActionCommand())) {
            String nomeCategoria = categoria.getSelectedItem().toString();
            if (nomeCategoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Scegli la categoria da modificare");
                return;
            }
            String nomeMod = nome.getText();
            String categoriaPadreMod = categoriaPadre.getSelectedItem().toString();
            CategoriaBusiness categoriaBusiness = CategoriaBusiness.getInstance();
            if (categoriaBusiness.modifyCategory(nomeCategoria, nomeMod, categoriaPadreMod) == 1) {
                JOptionPane.showMessageDialog(null, "La categoria è stata modificata correttamente");
            }
        }
        if (ADD_PRODUCTOR.equals(e.getActionCommand())) {
            String nomeProduttore = nome.getText();
            String website = sito.getText();
            String city = citta.getText();
            String nation = nazione.getText();

            ProduttoreBusiness produttoreBusiness = ProduttoreBusiness.getInstance();
            int result = produttoreBusiness.addProduttore(nomeProduttore, website, city, nation);

            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(null , "Sono richiesti tutti i campi");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Il produttore inserito esiste già");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Il produttore è stato inserito correttamente");
            }
        }
        if (ADD_SUPPLIER.equals(e.getActionCommand())) {
            String nomeFornitore = nome.getText();
            String website = sito.getText();
            String city = citta.getText();
            String nation = nazione.getText();

            FornitoreBusiness fornitoreBusiness = FornitoreBusiness.getInstance();
            int result = fornitoreBusiness.addFornitore(nomeFornitore, website, city, nation);

            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(null , "Sono richiesti tutti i campi");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Il fornitore inserito esiste già");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Il fornitore è stato inserito correttamente");
            }
        }
        if (DELETE_PRODUCTOR.equals(e.getActionCommand())) {
            String nomeProduttore = articoli.getSelectedItem().toString();
            if ("Seleziona un produttore".equalsIgnoreCase(nomeProduttore)) {
                JOptionPane.showMessageDialog(null, "Scegli un produttore");
                return;
            }
            ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
            int idProduttore = produttoreDAO.findByName(nomeProduttore).getId();
            ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
            ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
            for (int i = 0; i < prodotti.size(); i++) {
                if (prodotti.get(i).getProduttore().getNome().equalsIgnoreCase(nomeProduttore)) {
                    JOptionPane.showMessageDialog(null, "Impossibile cancellare! Eliminare prima i prodotti di " + nomeProduttore );
                    return;
                }
            }
            produttoreDAO.remove(produttoreDAO.findById(idProduttore));
            JOptionPane.showMessageDialog(null, "Il produttore è stato eliminato correttamente");
        }
        if (DELETE_SUPPLIER.equals(e.getActionCommand())) {
            String nomeFornitore = articoli.getSelectedItem().toString();
            if ("Seleziona un fornitore".equalsIgnoreCase(nomeFornitore)) {
                JOptionPane.showMessageDialog(null, "Scegli un fornitore");
                return;
            }
            FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
            int idFornitore = fornitoreDAO.findByName(nomeFornitore).getId();
            ServizioDAO servizioDAO = ServizioDAO.getInstance();
            ArrayList<Servizio> servizi = servizioDAO.findAll();
            for (int i = 0; i < servizi.size(); i++) {
                if (servizi.get(i).getFornitore().getNome().equalsIgnoreCase(nomeFornitore)) {
                    JOptionPane.showMessageDialog(null, "Impossibile cancellare! Eliminare prima i servizi di " + nomeFornitore );
                    return;
                }
            }
            fornitoreDAO.remove(fornitoreDAO.findById(idFornitore));
            JOptionPane.showMessageDialog(null, "Il fornitore è stato eliminato correttamente");
        }
    }
}
