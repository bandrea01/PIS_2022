package View.Listener;

import Business.ArticoloBusiness;
import Business.CategoriaBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import Model.ICategoria;
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


    private JTextField id;
    private JTextField nome;
    private JTextField prezzo;
    private JTextField descrizione;
    private WideComboBox categoria;
    private WideComboBox articoli;

    private WideComboBox isProdottoServizio;
    private WideComboBox produttoreFornitore;
    private WideComboBox categoriaPadre;


    private JPanel panel;

    public ManageArticlesListener(JTextField id, JTextField nome, JTextField prezzo, JTextField descrizione, WideComboBox categoria, WideComboBox isProdottoServizio, WideComboBox produttoreFornitore) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.isProdottoServizio = isProdottoServizio;
        this.produttoreFornitore = produttoreFornitore;
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

            String selection = "Seleziona un elemento";
            ArticoloBusiness articoloBusiness = ArticoloBusiness.getInstance();
            if (category.equalsIgnoreCase(selection) || isProductService.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase(selection) || productorSupplier.equalsIgnoreCase("Seleziona un fornitore") || productorSupplier.equalsIgnoreCase("Seleziona un produttore")) {
                JOptionPane.showMessageDialog(null ,"Sono richiesti tutti i campi");
                return;
            }
            int result = articoloBusiness.addArticolo(idInt, name, price, description,category, isProductService, productorSupplier);

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
                return;
            }
        }
    }
}
