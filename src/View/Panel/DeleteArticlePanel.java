package View.Panel;

import DAO.Articolo.ArticoloDAO;
import Model.Articolo;
import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeleteArticlePanel extends JPanel {

    public DeleteArticlePanel(MainLayout window) {

        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(3, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 10));

        JLabel seleziona = new JLabel("Seleziona l'articolo da eliminare");

        String[] articoli = getArticoli();
        WideComboBox articoliChooses = new WideComboBox(articoli);
        articoliChooses.setPreferredSize(new Dimension(7,7));
        articoliChooses.setWide(true);

        gridPanel.add(seleziona); gridPanel.add(articoliChooses);

        ManageArticlesListener listener = new ManageArticlesListener(articoliChooses);
        buttonsPanel.add(ButtonCreator.createButton("Confirm",true,ButtonCreator.LILLE, listener, ManageArticlesListener.DELETE_ARTICLE));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));


        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
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
