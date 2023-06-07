package View.Panel;

import DAO.Produttore.ProduttoreDAO;
import Model.Produttore;
import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeleteProductorPanel extends JPanel {
    public DeleteProductorPanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(2, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel seleziona = new JLabel("Seleziona il produttore da eliminare");

        String[] produttori = getProduttori();
        WideComboBox produttoriChooses = new WideComboBox(produttori);
        produttoriChooses.setPreferredSize(new Dimension(7,7));
        produttoriChooses.setWide(true);

        gridPanel.add(seleziona); gridPanel.add(produttoriChooses);

        ManageArticlesListener listener = new ManageArticlesListener(produttoriChooses);
        buttonsPanel.add(ButtonCreator.createButton("Confirm",true,ButtonCreator.LILLE, listener, ManageArticlesListener.DELETE_PRODUCTOR));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

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
}
