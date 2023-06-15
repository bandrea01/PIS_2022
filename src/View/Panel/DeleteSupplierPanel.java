package View.Panel;

import DAO.Fornitore.FornitoreDAO;
import Model.Fornitore;
import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeleteSupplierPanel extends JPanel {
    public DeleteSupplierPanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(2, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 10));

        JLabel seleziona = new JLabel("Select supplier you want to delete");

        String[] fornitori = getFornitori();
        WideComboBox fornitoriChooses = new WideComboBox(fornitori);
        fornitoriChooses.setPreferredSize(new Dimension(7,7));
        fornitoriChooses.setWide(true);

        gridPanel.add(seleziona); gridPanel.add(fornitoriChooses);

        ManageArticlesListener listener = new ManageArticlesListener(fornitoriChooses);
        buttonsPanel.add(ButtonCreator.createButton("Confirm",true,ButtonCreator.LILLE, listener, ManageArticlesListener.DELETE_SUPPLIER));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.validate(); this.repaint();

        setVisible(true);
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
}


