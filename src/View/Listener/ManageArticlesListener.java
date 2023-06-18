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
    private JTextField pathImmagine;


    private JPanel panel;

    public ManageArticlesListener(JTextField id, JTextField nome, JTextField prezzo, JTextField descrizione, WideComboBox categoria, WideComboBox isProdottoServizio, WideComboBox produttoreFornitore, WideComboBox sopraProdotto, JTextField pathImmagine) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.isProdottoServizio = isProdottoServizio;
        this.produttoreFornitore = produttoreFornitore;
        this.sopraProdotto = sopraProdotto;
        this.pathImmagine = pathImmagine;
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
                JOptionPane.showMessageDialog(null ,"All field are requested");
                return;
            }
            int idInt =Integer.parseInt(id.getText());
            String name = nome.getText();
            float price = Float.parseFloat(prezzo.getText());
            String description = descrizione.getText();
            String category = categoria.getSelectedItem().toString();
            String isProductService = isProdottoServizio.getSelectedItem().toString();
            String productorSupplier = produttoreFornitore.getSelectedItem().toString();
            String nomeImmagine = pathImmagine.getText();
            String overProduct = null;
            if (sopraProdotto.getSelectedItem() != null) {
                overProduct = sopraProdotto.getSelectedItem().toString();
            }
            String selection = "Select an element";
            ArticoloBusiness articoloBusiness = ArticoloBusiness.getInstance();
            if (category.equalsIgnoreCase(selection) || isProductService.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase("Select a supplier") || productorSupplier.equalsIgnoreCase("Select a productor")) {
                JOptionPane.showMessageDialog(null ,"All field are requested");
                return;
            }
            int result = articoloBusiness.addArticolo(idInt, name, price, description,category, isProductService, productorSupplier, overProduct, nomeImmagine);

            switch (result) {
                case 0:
                    JOptionPane.showMessageDialog(null ,"All fields are requested");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Article alredy exists");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Product correctly inserted");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Service correctly inserted");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Please select an image");
                    return;
                case -1:
                    JOptionPane.showMessageDialog(null, "Error!");
                    break;
            }
        }
        if (ADD_CATEGORY.equals(e.getActionCommand())) {
            if (nome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null ,"All fields are requested");
                return;
            }
            CategoriaBusiness categoriaBusiness = CategoriaBusiness.getInstance();
            String name = nome.getText();
            CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
            String nessunaCategoriaPadre = "Nothing";

            if (nessunaCategoriaPadre.equalsIgnoreCase(categoria.getSelectedItem().toString())) {
                int idCategoriaPadre = 0;
                if (categoriaBusiness.addCategory(name, idCategoriaPadre) == 1) {
                    JOptionPane.showMessageDialog(null, "Category alredy exist");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Category correctly inserted");
                return;
            }

            int idCategoriaPadre = categoriaDAO.findId(categoria.getSelectedItem().toString());
            if (categoriaBusiness.addCategory(name, idCategoriaPadre) == 1) {
                JOptionPane.showMessageDialog(null, "Category alredy exist");
                return;
            }
            JOptionPane.showMessageDialog(null, "Category correctly inserted");
        }
        if (DELETE_ARTICLE.equals(e.getActionCommand())) {
            String nomeArticolo = articoli.getSelectedItem().toString();
            if (nomeArticolo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seelct an article");
                return;
            }
            ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
            int idArticolo = articoloDAO.findByName(nomeArticolo).getId();
            articoloDAO.removeById(idArticolo);
            JOptionPane.showMessageDialog(null, "Article correctly deleted");
        }
        if (DELETE_CATEGORY.equals(e.getActionCommand())) {
            String nomeCategoria = articoli.getSelectedItem().toString();
            if (nomeCategoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a category");
                return;
            }
            CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
            int idCategoria = categoriaDAO.findByName(nomeCategoria).getId();
            ArrayList<ICategoria> sottoCategorie = categoriaDAO.findAllSottoCategorie(idCategoria);
            if (sottoCategorie.size() != 0) {
                JOptionPane.showMessageDialog(null, "First you have to delete a subcategory!");
                return;
            }
            categoriaDAO.remove(categoriaDAO.findById(idCategoria));
            JOptionPane.showMessageDialog(null, "Category correctly deleted");
        }
        if (MODIFY_ARTICLE.equals(e.getActionCommand())) {
            String nomeArticolo = articoli.getSelectedItem().toString();
            if (nomeArticolo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select the article you want to modify");
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
                JOptionPane.showMessageDialog(null, "Article correctly modified");
            }
        }
        if (MODIFY_CATEGORY.equals(e.getActionCommand())) {
            String nomeCategoria = categoria.getSelectedItem().toString();
            if (nomeCategoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select the category you want to modify");
                return;
            }
            String nomeMod = nome.getText();
            String categoriaPadreMod = categoriaPadre.getSelectedItem().toString();
            CategoriaBusiness categoriaBusiness = CategoriaBusiness.getInstance();
            if (categoriaBusiness.modifyCategory(nomeCategoria, nomeMod, categoriaPadreMod) == 1) {
                JOptionPane.showMessageDialog(null, "Category correctly modified");
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
                    JOptionPane.showMessageDialog(null , "All fields are requested");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Inserted productor alredy exists");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Productor correctly inserted");
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
                    JOptionPane.showMessageDialog(null , "All fields are requested");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Inserted supplier alredy exists");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Supplier correctly inserted");
            }
        }
        if (DELETE_PRODUCTOR.equals(e.getActionCommand())) {
            String nomeProduttore = articoli.getSelectedItem().toString();
            if ("Select a productor".equalsIgnoreCase(nomeProduttore)) {
                JOptionPane.showMessageDialog(null, "Select a productor");
                return;
            }
            ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
            int idProduttore = produttoreDAO.findByName(nomeProduttore).getId();
            ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
            ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
            for (int i = 0; i < prodotti.size(); i++) {
                if (prodotti.get(i).getProduttore().getNome().equalsIgnoreCase(nomeProduttore)) {
                    JOptionPane.showMessageDialog(null, "Error! You have to delete all products of: " + nomeProduttore );
                    return;
                }
            }
            produttoreDAO.remove(produttoreDAO.findById(idProduttore));
            JOptionPane.showMessageDialog(null, "Productor correctly deleted");
        }
        if (DELETE_SUPPLIER.equals(e.getActionCommand())) {
            String nomeFornitore = articoli.getSelectedItem().toString();
            if ("Select a supplier".equalsIgnoreCase(nomeFornitore)) {
                JOptionPane.showMessageDialog(null, "Select a supplier");
                return;
            }
            FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
            int idFornitore = fornitoreDAO.findByName(nomeFornitore).getId();
            ServizioDAO servizioDAO = ServizioDAO.getInstance();
            ArrayList<Servizio> servizi = servizioDAO.findAll();
            for (int i = 0; i < servizi.size(); i++) {
                if (servizi.get(i).getFornitore().getNome().equalsIgnoreCase(nomeFornitore)) {
                    JOptionPane.showMessageDialog(null, "Error! You have to delete all services of: " + nomeFornitore );
                    return;
                }
            }
            fornitoreDAO.remove(fornitoreDAO.findById(idFornitore));
            JOptionPane.showMessageDialog(null, "Supplier correctly deleted");
        }
    }
}
