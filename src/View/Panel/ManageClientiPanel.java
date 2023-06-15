package View.Panel;

import Business.SessionManager;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Manager;
import Model.PuntoVendita;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;

public class ManageClientiPanel extends JPanel {
    public ManageClientiPanel(MainLayout window) {
        Manager manager = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());
        if (ClientePuntoVenditaDAO.getInstance().findAllByPunto(punto).size() == 0) {
            JOptionPane.showMessageDialog(null, "Il punto vendita " + punto.getName() + " non ha clienti");
        } else {
            JPanel buttonsPanel = new JPanel();
            this.setLayout(new BorderLayout());
            buttonsPanel.setLayout(new GridLayout(2, 10));

            buttonsPanel.add(ButtonCreator.createButton("Ban customer", true, ButtonCreator.LILLE, e -> window.disabilitaCliente(), null));
            buttonsPanel.add(ButtonCreator.createButton("Delete customer", true, ButtonCreator.LILLE, e -> window.cancellaCliente(), null));
            buttonsPanel.add(ButtonCreator.createButton("Send email to a customer", true, ButtonCreator.LILLE, e -> window.inviaEmailCliente(), null));

            this.add(buttonsPanel, BorderLayout.CENTER);
            this.repaint();
            this.validate();

            setVisible(true);
        }
    }
}
