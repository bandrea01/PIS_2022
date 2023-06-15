package View.Panel;


import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.*;
import View.Listener.ManageArticlesListener;
import View.MainLayout;

import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AddArticlePanel extends JPanel {
    public AddArticlePanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 2));
        south.setLayout(new GridLayout(2, 0));


        JLabel idLabel = new JLabel("Id: ");
        JLabel nomeLabel = new JLabel("Name: ");
        JLabel prezzoLabel = new JLabel("Price: ");
        JLabel descrizioneLabel = new JLabel("Description: ");
        JLabel categoriaLabel = new JLabel("Category: ");
        JLabel prodottoServizioLabel = new JLabel("Product or Service: ");
        JLabel produttoreFornitoreLabel = new JLabel("");
        JLabel sopraProdotto = new JLabel("Overproduct: ");


        JTextField id = new JTextField(15);
        JTextField nome = new JTextField();
        JTextField prezzo = new JTextField();
        JTextField descrizione = new JTextField();

        String[] categorie = getCategorie();
        WideComboBox categorieChooses = new WideComboBox(categorie);
        categorieChooses.setPreferredSize(new Dimension(7,7));
        categorieChooses.setWide(true);

        String isProdotto = "Product"; String isServizio = "Service";
        String[] prodottoServizio = {"Select an element", isProdotto, isServizio};
        WideComboBox isProdottoServizio = new WideComboBox(prodottoServizio);
        isProdottoServizio.setPreferredSize(new Dimension(7,7));
        isProdottoServizio.setWide(true);

        String[] produttoriFornitori = {"Select an element"};
        WideComboBox produttoriFornitorichooses = new WideComboBox(produttoriFornitori);
        produttoriFornitorichooses.setPreferredSize(new Dimension(7,7));
        produttoriFornitorichooses.setWide(true);

        String[] prodotti = {""};
        WideComboBox sopraProdottoChooses = new WideComboBox(prodotti);
        sopraProdottoChooses.setPreferredSize(new Dimension(7,7));
        sopraProdottoChooses.setWide(true);

        gridPanel.add(idLabel); gridPanel.add(id);
        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(prezzoLabel); gridPanel.add(prezzo);
        gridPanel.add(descrizioneLabel); gridPanel.add(descrizione);
        gridPanel.add(categoriaLabel); gridPanel.add(categorieChooses);
        gridPanel.add(prodottoServizioLabel); gridPanel.add(isProdottoServizio);

        gridPanel.add(produttoreFornitoreLabel);
        gridPanel.add(produttoriFornitorichooses);


        isProdottoServizio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateProduttoriFornitoriChooses();
                updateProdottiChooses();
            }

            private void updateProdottiChooses() {
                String selectedElement = isProdottoServizio.getSelectedItem().toString();
                sopraProdottoChooses.removeAllItems();
                String[] updatedElements;
                if (isProdotto.equals(selectedElement)) {
                    updatedElements = getProdotti();
                    for (String element : updatedElements) {
                        sopraProdottoChooses.addItem(element);
                    }
                    gridPanel.add(sopraProdotto); gridPanel.add(sopraProdottoChooses);
                }
            }
            private void updateProduttoriFornitoriChooses() {
                String selectedElement = isProdottoServizio.getSelectedItem().toString();
                produttoriFornitorichooses.removeAllItems();
                String[] updatedElements;
                if (isProdotto.equals(selectedElement)) {
                    updatedElements = getProduttori();
                    for (String element : updatedElements) {
                        produttoriFornitorichooses.addItem(element);
                    }
                } else if (isServizio.equals(selectedElement)) {
                        updatedElements = getFornitori();
                        for (String element : updatedElements) {
                            produttoriFornitorichooses.addItem(element);
                        }
                }
                }
        });


        isProdottoServizio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedElement = isProdottoServizio.getSelectedItem().toString();
                if (isProdotto.equals(selectedElement)) {
                    produttoreFornitoreLabel.setText("Productor: ");
                } else if (isServizio.equals(selectedElement)){
                    produttoreFornitoreLabel.setText("Supplier: ");
                }
            }
        });



        ManageArticlesListener listener = new ManageArticlesListener(id, nome, prezzo, descrizione, categorieChooses, isProdottoServizio, produttoriFornitorichooses, sopraProdottoChooses);
        JButton confirm = ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageArticlesListener.ADD_ARTICLE);
        south.add(confirm);
        JButton back = ButtonCreator.createButton("Go Back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null);
        south.add(back);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
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

    private String[] getProdotti() {
        ArrayList<Prodotto> prodotti = ProdottoDAO.getInstance().findAll();
        String[] nomiProdotti = new String[prodotti.size() + 1];
        nomiProdotti[0] = "Nothing";
        for (int i = 1; i < prodotti.size() + 1; i++) {
            nomiProdotti[i] = prodotti.get(i-1).getName();
        }
        return nomiProdotti;
    }


}
