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

public class DeleteCategoryPanel extends JPanel {
    public DeleteCategoryPanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(2, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel categoriaLabel = new JLabel("Categoria");
        String[] categorie = getCategorie();
        WideComboBox categoriaChooses = new WideComboBox(categorie);
        categoriaChooses.setPreferredSize(new Dimension(7,7));
        categoriaChooses.setWide(true);

        gridPanel.add(categoriaLabel); gridPanel.add(categoriaChooses);

        ManageArticlesListener listener = new ManageArticlesListener(categoriaChooses);
        buttonsPanel.add(ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageArticlesListener.DELETE_CATEGORY));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.validate(); this.repaint();

        setVisible(true);
    }

    private String[] getCategorie() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size()];
        for (int i = 0; i < categorie.size(); i++){
            nomiCategorie[i] = categorie.get(i).getName();
        }
        return nomiCategorie;
    }
}
