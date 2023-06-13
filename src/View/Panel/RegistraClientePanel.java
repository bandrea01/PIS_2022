package View.Panel;

import Business.SessionManager;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.PuntoVendita;
import Model.Utente;
import View.Listener.ManageClientiListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegistraClientePanel extends JPanel {
    public RegistraClientePanel(MainLayout window) {
        Utente utente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        String username = utente.getUsername();

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(2, 0));

        JLabel puntoVenditaLabel = new JLabel("Seleziona il punto vendita: ");

        String[] puntiVendita = getPuntiVendita();
        WideComboBox puntiVenditaChooses = new WideComboBox(puntiVendita);
        puntiVenditaChooses.setPreferredSize(new Dimension(7,7));
        puntiVenditaChooses.setWide(true);

        JLabel canalePreferitoLabel = new JLabel("Canale preferito: ");

        String[] canalePreferito = {"EMAIL", "SMS", "PUSH"};
        WideComboBox canalePreferitoChooses = new WideComboBox(canalePreferito);
        canalePreferitoChooses.setPreferredSize(new Dimension(7,7));
        canalePreferitoChooses.setWide(true);

        gridPanel.add(puntoVenditaLabel); gridPanel.add(puntiVenditaChooses);
        gridPanel.add(canalePreferitoLabel); gridPanel.add(canalePreferitoChooses);

        ManageClientiListener listener = new ManageClientiListener(username, puntiVenditaChooses, canalePreferitoChooses);
        south.add(ButtonCreator.createButton("Registrati", true, ButtonCreator.LILLE, listener, ManageClientiListener.SIGN_IN_BTN));
        south.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.showProfile(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
        this.repaint(); this.validate();

        setVisible(true);

    }

    public String[] getPuntiVendita() {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> puntiVendita = puntoVenditaDAO.findAll();
        String[] nomiPuntiVendita = new String[puntiVendita.size()];
        for (int i = 0; i < puntiVendita.size(); i++) {
            nomiPuntiVendita[i] = puntiVendita.get(i).getName();
        }
        return nomiPuntiVendita;
    }
}
