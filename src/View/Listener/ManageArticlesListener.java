package View.Listener;

import Business.ArticoloBusiness;
import Business.CategoriaBusiness;
import Business.UtenteBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import Model.Articolo;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageArticlesListener implements ActionListener {
    public final static String ADD_ARTICLE = "addarticle-btn";
    public final static String ADD_CATEGORY = "addcategory-btn";
    public final static String DELETE_ARTICLE = "deletearticle-btn";
    public final static String DELETE_CATEGORY = "deletecategory-btn";


    private JTextField id;
    private JTextField nome;
    private JTextField prezzo;
    private JTextField descrizione;
    private WideComboBox categoria;
    private WideComboBox articoli;

    private WideComboBox isProdottoServizio;
    private WideComboBox produttoreFornitore;


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

    public ManageArticlesListener(JPanel panel) {
        this.panel = panel;
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
            articoloDAO.removeByName(nomeArticolo);
            JOptionPane.showMessageDialog(null, "L'articolo è stato cancellato correttamente");
        }
    }
}
