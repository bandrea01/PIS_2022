package View.Panel;

import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;

public class ManageProfilePanel extends JPanel {
    public ManageProfilePanel(MainLayout window) {

        JPanel buttonsPanel = new JPanel();
        this.setLayout(new BorderLayout());
        buttonsPanel.setLayout(new GridLayout(2, 10));

        //buttonsPanel.add(ButtonCreator.createButton("Sign in a sale point", true, ButtonCreator.LILLE, e ->window.registraCliente(), null));

        this.add(buttonsPanel, BorderLayout.CENTER);
        this.repaint();
        this.validate();

        setVisible(true);
    }
}
