package View.Panel;

import Business.MailHelper;
import Business.SessionManager;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
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

public class InviaEmailClientePanel extends JPanel {
    public InviaEmailClientePanel(MainLayout window) {
        Manager manager = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(5, 0));

        JLabel utenteLabel = new JLabel("Seleziona il cliente a cui inviare l'email: ");

        String[] clienti = getClienti(punto);
        WideComboBox utentiChooses = new WideComboBox(clienti);
        utentiChooses.setPreferredSize(new Dimension(7,7));
        utentiChooses.setWide(true);

        JLabel oggettoLabel = new JLabel("Oggetto: ");
        JTextField oggetto = new JTextField();

        JLabel testoLabel = new JLabel("Testo: ");
        JTextField testo = new JTextField();


        gridPanel.add(utenteLabel); gridPanel.add(utentiChooses);
        gridPanel.add(oggettoLabel); gridPanel.add(oggetto);
        gridPanel.add(testoLabel); gridPanel.add(testo);

        ManageClientiListener listener = new ManageClientiListener(utentiChooses, oggetto, testo);
        south.add(ButtonCreator.createButton("Invia email", true, ButtonCreator.LILLE, listener, ManageClientiListener.SEND_EMAIL_BTN));
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
