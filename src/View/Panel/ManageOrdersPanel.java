package View.Panel;

import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;

public class ManageOrdersPanel extends JPanel {
    public ManageOrdersPanel(MainLayout window) {
        JPanel buttonsPanel = new JPanel();
        this.setLayout(new BorderLayout());
        buttonsPanel.setLayout(new GridLayout(2, 10));

        buttonsPanel.add(ButtonCreator.createButton("Pay orders", true, ButtonCreator.LILLE, e ->window.pagaOrdine(), null));
        //bottone per i feedback

        this.add(buttonsPanel, BorderLayout.CENTER);
        this.repaint();
        this.validate();

        setVisible(true);
    }
}
