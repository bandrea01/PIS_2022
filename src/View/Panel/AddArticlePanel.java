package View.Panel;

import DAO.Categoria.CategoriaDAO;
import DAO.Fornitore.FornitoreDAO;
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
        //JLabel sottoCategoriaLabel = new JLabel("Subcategory: ");
        JLabel prodottoServizioLabel = new JLabel("Product or Service: ");
        JLabel produttoreFornitoreLabel = new JLabel("");


        JTextField id = new JTextField(15);
        JTextField nome = new JTextField();
        JTextField prezzo = new JTextField();
        JTextField descrizione = new JTextField();

        String[] categorie = getCategorie();
        WideComboBox categorieChooses = new WideComboBox(categorie);
        categorieChooses.setPreferredSize(new Dimension(7,7));
        categorieChooses.setWide(true);

        /*String[] sottoCategorie = getSottoCategorie(categorieChooses.getSelectedItem());
        WideComboBox sottoCategorieChooses = new WideComboBox(sottoCategorie);
        sottoCategorieChooses.setPreferredSize(new Dimension(7, 7));
        sottoCategorieChooses.setWide(true);
*/

        String isProdotto = "Prodotto"; String isServizio = "Servizio";
        String[] prodottoServizio = {"Seleziona un elemento", isProdotto, isServizio};
        WideComboBox isProdottoServizio = new WideComboBox(prodottoServizio);
        isProdottoServizio.setPreferredSize(new Dimension(7,7));
        isProdottoServizio.setWide(true);

        String[] produttoriFornitori = {"Seleziona un elemento"};
        WideComboBox produttoriFornitorichooses = new WideComboBox(produttoriFornitori);
        produttoriFornitorichooses.setPreferredSize(new Dimension(7,7));
        produttoriFornitorichooses.setWide(true);

        gridPanel.add(idLabel); gridPanel.add(id);
        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(prezzoLabel); gridPanel.add(prezzo);
        gridPanel.add(descrizioneLabel); gridPanel.add(descrizione);
        gridPanel.add(categoriaLabel); gridPanel.add(categorieChooses);
     //   gridPanel.add(sottoCategoriaLabel); gridPanel.add(sottoCategorieChooses);
        gridPanel.add(prodottoServizioLabel); gridPanel.add(isProdottoServizio);

        gridPanel.add(produttoreFornitoreLabel);
        gridPanel.add(produttoriFornitorichooses);

        isProdottoServizio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateProduttoriFornitorichooses();
            }

            private void updateProduttoriFornitorichooses() {
                String selectedElement = isProdottoServizio.getSelectedItem().toString();
                produttoriFornitorichooses.removeAllItems();
                String[] updatedElements;
                if (isProdotto.equals(selectedElement)) {
                    updatedElements = getProduttori();
                    System.out.println(updatedElements);
                    for (String element : updatedElements) {
                        produttoriFornitorichooses.addItem(element);
                    }
                } else if (isServizio.equals(selectedElement)) {
                        updatedElements = getFornitori();
                        for (String element : updatedElements) {
                            produttoriFornitorichooses.addItem(element);
                        }                    }
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



        ManageArticlesListener listener = new ManageArticlesListener(id, nome, prezzo, descrizione, categorieChooses, isProdottoServizio, produttoriFornitorichooses);
        JButton confirm = ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageArticlesListener.ADD_ARTICLE);
        south.add(confirm);
        JButton back = ButtonCreator.createButton("Go Back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null);
        south.add(back);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);


        setVisible(true);
    }

    private String[] getProduttori() {
        ArrayList<Produttore> produttori = ProduttoreDAO.getInstance().findAll();
        String[] nomiProduttori = new String[produttori.size() + 1];
        nomiProduttori[0] = "Seleziona un produttore";
        for (int i = 1; i < produttori.size() + 1; i++) {
            nomiProduttori[i] = produttori.get(i - 1).getNome();
        }
        return nomiProduttori;
    }

    private String[] getFornitori() {
        ArrayList<Fornitore> fornitori = FornitoreDAO.getInstance().findAll();
        String[] nomiFornitori = new String[fornitori.size() + 1];
        nomiFornitori[0] = "Seleziona un fornitore";
        for (int i = 1; i < fornitori.size() + 1; i++) {
            nomiFornitori[i] = fornitori.get(i - 1).getNome();
        }
        return nomiFornitori;
    }


    private String[] getCategorie() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size() + 1];
        nomiCategorie[0] = "Seleziona un elemento";
        for (int i = 1; i < categorie.size() + 1; i++){
            nomiCategorie[i] = categorie.get(i - 1).getName();
        }
        return nomiCategorie;
    }


}


/* private String[] getSottoCategorie(Object name) {
         CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
         int id = categoriaDAO.findId(name.toString());
         ArrayList<ICategoria> sottoCategorie = CategoriaDAO.getInstance().findAllSottoCategorie(id);
         String[] nomiSottoCategorie = new String[sottoCategorie.size()];
         for (int i = 0; i < sottoCategorie.size(); i++) {
             nomiSottoCategorie[i] = sottoCategorie.get(i).getName();
         }
         return nomiSottoCategorie;
     }
 */

