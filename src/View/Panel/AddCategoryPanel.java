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

public class AddCategoryPanel extends JPanel {

    public AddCategoryPanel(MainLayout window) {

        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(2, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel sottoCategoriaLabel = new JLabel("Sottocategoria: ");

        JTextField nome = new JTextField(15);

        String[] sottoCategorie = getCategorie();
        WideComboBox sottoCategoriaChooses = new WideComboBox(sottoCategorie);
        sottoCategoriaChooses.setPreferredSize(new Dimension(7,7));
        sottoCategoriaChooses.setWide(true);

        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(sottoCategoriaLabel); gridPanel.add(sottoCategoriaChooses);

        this.add(gridPanel, BorderLayout.CENTER);

        ManageArticlesListener listener = new ManageArticlesListener(nome, sottoCategoriaChooses);
        buttonsPanel.add(ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageArticlesListener.ADD_CATEGORY));
        buttonsPanel.add(ButtonCreator.createButton("Go Back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        this.add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);



    }

    private String[] getCategorie() {
        ArrayList<ICategoria> categorie = CategoriaDAO.getInstance().findAll();
        String[] nomiCategorie = new String[categorie.size() + 1];
        nomiCategorie[0] = "Nessuna";
        for (int i = 1; i < categorie.size() + 1; i++){
            nomiCategorie[i] = categorie.get(i - 1).getName();
        }
        return nomiCategorie;
    }
}
