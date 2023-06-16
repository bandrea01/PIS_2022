package View.Panel;

import Business.SessionManager;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.Ordine.OrdineDAO;

import Model.*;
import View.Listener.ManageOrdersListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CommentaArticoliPanel extends JPanel {
    public CommentaArticoliPanel(MainLayout window) {
        Utente utente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        if (!OrdineDAO.getInstance().utenteHasBought(utente)) {
            JOptionPane.showMessageDialog(null, "You have not created a shopping list yet");
            window.acquista();
            return;
        }

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(2, 2));

        JLabel puntoVenditaLabel = new JLabel("Select a sale point: ");
        JLabel articoloLabel = new JLabel("Select article: ");
        JLabel commentoLabel = new JLabel("Text: ");
        JLabel gradimentoLabel = new JLabel("Approval rating: ");

        String[] puntiVendita = getPuntiVendita(utente);
        WideComboBox puntiVenditaChooses = new WideComboBox(puntiVendita);
        puntiVenditaChooses.setPreferredSize(new Dimension(7,7));
        puntiVenditaChooses.setWide(true);

        String[] articoliAcquistati = getArticoliAcquistati(utente);
        WideComboBox articoliAcquistatiChooses = new WideComboBox(articoliAcquistati);
        articoliAcquistatiChooses.setPreferredSize(new Dimension(7,7));
        articoliAcquistatiChooses.setWide(true);

        JTextField commento = new JTextField();

        String[] gradimenti = {"VERY BAD", "SCARCE", "NORMAL", "GOOD", "EXCELLENT"};
        WideComboBox gradimentiChooses = new WideComboBox(gradimenti);
        gradimentiChooses.setPreferredSize(new Dimension(7,7));
        gradimentiChooses.setWide(true);

        gridPanel.add(puntoVenditaLabel); gridPanel.add(puntiVenditaChooses);
        gridPanel.add(articoloLabel); gridPanel.add(articoliAcquistatiChooses);
        gridPanel.add(commentoLabel); gridPanel.add(commento);
        gridPanel.add(gradimentoLabel); gridPanel.add(gradimentiChooses);

        ManageOrdersListener listener = new ManageOrdersListener(puntiVenditaChooses, articoliAcquistatiChooses, commento, gradimentiChooses);
        south.add(ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageOrdersListener.FEEDBACK));
        south.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageOrders(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);


        this.repaint(); this.validate();
    }

    String[] getArticoliAcquistati(Utente utente) {
        OrdineDAO ordineDAO = OrdineDAO.getInstance();
        ArrayList<Prodotto> prodotti = ordineDAO.findAllProductsBoughtFromUtente(utente);
        ArrayList<Servizio> servizi = ordineDAO.findAllServicesBoughtFromUtente(utente);
        String[] nomiArticoli = new String[prodotti.size() + servizi.size()];
        for (int i = 0; i < prodotti.size(); i++) {
            nomiArticoli[i] = prodotti.get(i).getName();
        }
        for (int i = prodotti.size(); i < nomiArticoli.length; i++) {
            nomiArticoli[i] = servizi.get(i - prodotti.size()).getName();
        }
        return nomiArticoli;
    }
    String[] getPuntiVendita(Utente utente) {
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> puntiVendita = clientePuntoVenditaDAO.findAllbyCliente(utente);
        String[] nomiPuntiVendita = new String[puntiVendita.size() + 1];
        nomiPuntiVendita[0] = "Select a sale point";
        for (int i = 1; i < puntiVendita.size() + 1; i++) {
            nomiPuntiVendita[i] = puntiVendita.get(i-1).getName();
        }
        return nomiPuntiVendita;
    }
}
