package View.Panel;

import Business.SessionManager;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import View.Listener.ManageClientiListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisabilitaClientePanel extends JPanel {
    public DisabilitaClientePanel(MainLayout window) {
        Manager manager = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(2, 0));

        JLabel utenteLabel = new JLabel("Select the customer you want to ban");

        String[] clienti = getClienti(punto);
        WideComboBox utentiChooses = new WideComboBox(clienti);
        utentiChooses.setPreferredSize(new Dimension(7,7));
        utentiChooses.setWide(true);

        gridPanel.add(utenteLabel); gridPanel.add(utentiChooses);


        ManageClientiListener listener = new ManageClientiListener(utentiChooses, punto.getName());
        south.add(ButtonCreator.createButton("Ban", true, ButtonCreator.LILLE, listener, ManageClientiListener.BAN_BTN));
        south.add(ButtonCreator.createButton("Unban", true, ButtonCreator.LILLE, listener, ManageClientiListener.UNBAN_BTN));
        south.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageClienti(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
        this.repaint(); this.validate();

        setVisible(true);
    }

    public String[] getClienti(PuntoVendita punto) {
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        ArrayList<Utente> clienti = clientePuntoVenditaDAO.findAllByPunto(punto);
        String[] nomiUtenti = new String[clienti.size()];
        for (int i = 0; i < clienti.size(); i++) {
            nomiUtenti[i] = clienti.get(i).getUsername();
        }
        return nomiUtenti;
    }
}
