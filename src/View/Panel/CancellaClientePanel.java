package View.Panel;

import Business.SessionManager;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Cliente;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import Test.DAOTest.ClienteDAOTest;
import View.Listener.ManageClientiListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CancellaClientePanel extends JPanel {
    public CancellaClientePanel(MainLayout window) {
        Manager manager = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(2, 0));

        JLabel utenteLabel = new JLabel("Seleziona il cliente da cancellare");

        String[] clienti = getClienti(punto);
        WideComboBox utentiChooses = new WideComboBox(clienti);
        utentiChooses.setPreferredSize(new Dimension(7,7));
        utentiChooses.setWide(true);

        gridPanel.add(utenteLabel); gridPanel.add(utentiChooses);

        ManageClientiListener listener = new ManageClientiListener(utentiChooses, punto.getName());
        south.add(ButtonCreator.createButton("Cancella", true, ButtonCreator.LILLE, listener, ManageClientiListener.REMOVE_BTN));
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
