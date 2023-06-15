package View.Panel;

import DAO.Categoria.CategoriaDAO;
import Model.ICategoria;
import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifyCategoryPanel extends JPanel {
    public ModifyCategoryPanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(8, 2));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel seleziona = new JLabel("Category you want to modify:");

        String[] categorie = getCategorie();
        WideComboBox categorieChooses = new WideComboBox(categorie);
        categorieChooses.setPreferredSize(new Dimension(7,7));
        categorieChooses.setWide(true);

        gridPanel.add(seleziona); gridPanel.add(categorieChooses);

        JLabel nomeLabel = new JLabel("Name:");
        JTextField nome = new JTextField();

        JLabel categoriePadreLabel = new JLabel("Super-Category");
        String[] categoriePadre = getCategoriePadre();
        WideComboBox categoriePadreChooses = new WideComboBox(categoriePadre);
        categoriePadreChooses.setPreferredSize(new Dimension(7,7));
        categoriePadreChooses.setWide(true);

        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(categoriePadreLabel); gridPanel.add(categoriePadreChooses);

        ManageArticlesListener listener = new ManageArticlesListener(nome, categorieChooses, categoriePadreChooses);
        buttonsPanel.add(ButtonCreator.createButton("Modify",true,ButtonCreator.LILLE, listener, ManageArticlesListener.MODIFY_CATEGORY));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.validate(); this.repaint();

        setVisible(true);
    }

    private String[] getCategoriePadre() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size() + 1];
        nomiCategorie[0] = "Nothing";
        for (int i = 1; i < categorie.size() + 1; i++){
            nomiCategorie[i] = categorie.get(i - 1).getName();
        }
        return nomiCategorie;
    }
    private String[] getCategorie() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size()];
        for (int i = 0; i < categorie.size() ; i++){
            nomiCategorie[i] = categorie.get(i).getName();
        }
        return nomiCategorie;
    }

}
