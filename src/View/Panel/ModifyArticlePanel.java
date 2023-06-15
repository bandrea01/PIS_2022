package View.Panel;

import DAO.Articolo.ArticoloDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.Articolo;
import Model.Fornitore;
import Model.ICategoria;
import Model.Produttore;
import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModifyArticlePanel extends JPanel {
    public ModifyArticlePanel(MainLayout window) {

        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(8, 2));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel seleziona = new JLabel("Article:");

        String[] articoli = getArticoli();
        WideComboBox articoliChooses = new WideComboBox(articoli);
        articoliChooses.setPreferredSize(new Dimension(7,7));
        articoliChooses.setWide(true);

        gridPanel.add(seleziona); gridPanel.add(articoliChooses);

        JLabel nomeLabel = new JLabel("Name: ");
        JLabel prezzoLabel = new JLabel("Price: ");
        JLabel descrizioneLabel = new JLabel("Description: ");
        JLabel categoriaLabel = new JLabel("Category: ");
        JLabel produttoreFornitoreLabel = new JLabel("");

        JTextField nome = new JTextField();
        JTextField prezzo = new JTextField();
        JTextField descrizione = new JTextField();

        String[] categorie = getCategorie();
        WideComboBox categorieChooses = new WideComboBox(categorie);
        categorieChooses.setPreferredSize(new Dimension(7,7));
        categorieChooses.setWide(true);

        String[] produttoriFornitori = {"Select an element"};
        WideComboBox produttoriFornitorichooses = new WideComboBox(produttoriFornitori);
        produttoriFornitorichooses.setPreferredSize(new Dimension(7,7));
        produttoriFornitorichooses.setWide(true);

        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(prezzoLabel); gridPanel.add(prezzo);
        gridPanel.add(descrizioneLabel); gridPanel.add(descrizione);
        gridPanel.add(categoriaLabel); gridPanel.add(categorieChooses);

        gridPanel.add(produttoreFornitoreLabel);
        gridPanel.add(produttoriFornitorichooses);



        ManageArticlesListener listener = new ManageArticlesListener(articoliChooses, nome, prezzo, descrizione, categorieChooses, produttoriFornitorichooses);
        buttonsPanel.add(ButtonCreator.createButton("Modify",true,ButtonCreator.LILLE, listener, ManageArticlesListener.MODIFY_ARTICLE));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        //ACTION LISTENER PER VEDERE SE ARTICOLO è PRODOTTO O SERVIZIO
        articoliChooses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedElement = articoliChooses.getSelectedItem().toString();
                ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
                if (articoloDAO.isProdotto(articoloDAO.findByName(selectedElement).getId())) {
                    produttoreFornitoreLabel.setText("Productor: ");
                } else if (articoloDAO.isServizio(articoloDAO.findByName(selectedElement).getId())){
                    produttoreFornitoreLabel.setText("Supplier: ");
                }
            }
        });

        articoliChooses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateProduttoriFornitorichooses();
            }

            private void updateProduttoriFornitorichooses() {
                String selectedElement = articoliChooses.getSelectedItem().toString();
                produttoriFornitorichooses.removeAllItems();
                String[] updatedElements;
                ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
                if (articoloDAO.isProdotto(articoloDAO.findByName(selectedElement).getId())) {
                    updatedElements = getProduttori();
                    for (String element : updatedElements) {
                        produttoriFornitorichooses.addItem(element);
                    }
                } else if (articoloDAO.isServizio(articoloDAO.findByName(selectedElement).getId())) {
                    updatedElements = getFornitori();
                    for (String element : updatedElements) {
                        produttoriFornitorichooses.addItem(element);
                    }
                }
            }
        });

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.validate(); this.repaint();
        setVisible(true);

    }

    private String[] getProduttori() {
        ArrayList<Produttore> produttori = ProduttoreDAO.getInstance().findAll();
        String[] nomiProduttori = new String[produttori.size() + 1];
        nomiProduttori[0] = "Select a productor";
        for (int i = 1; i < produttori.size() + 1; i++) {
            nomiProduttori[i] = produttori.get(i - 1).getNome();
        }
        return nomiProduttori;
    }

    private String[] getFornitori() {
        ArrayList<Fornitore> fornitori = FornitoreDAO.getInstance().findAll();
        String[] nomiFornitori = new String[fornitori.size() + 1];
        nomiFornitori[0] = "Select a supplier";
        for (int i = 1; i < fornitori.size() + 1; i++) {
            nomiFornitori[i] = fornitori.get(i - 1).getNome();
        }
        return nomiFornitori;
    }

    private String[] getCategorie() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size() + 1];
        nomiCategorie[0] = "Select an element";
        for (int i = 1; i < categorie.size() + 1; i++){
            nomiCategorie[i] = categorie.get(i - 1).getName();
        }
        return nomiCategorie;
    }
    private String[] getArticoli() {
        ArrayList<Articolo> articoli = ArticoloDAO.getInstance().findAll();
        String[] nomiArticoli = new String[articoli.size()];
        for (int i = 0; i < articoli.size(); i++) {
            nomiArticoli[i] = articoli.get(i).getName();
        }
        return nomiArticoli;
    }
}
