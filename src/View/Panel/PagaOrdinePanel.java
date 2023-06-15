package View.Panel;

import Business.SessionManager;
import DAO.Ordine.OrdineDAO;
import Model.Ordine;
import Model.Utente;
import View.Listener.ManageOrdersListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PagaOrdinePanel extends JPanel {
    public PagaOrdinePanel(MainLayout window) {
        Utente utente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        if (OrdineDAO.getInstance().findAllOfUtente(utente).size() == 0) {
            JOptionPane.showMessageDialog(null, "You have not created a shopping list yet");
            return;
        }
        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10,1));
        south.setLayout(new GridLayout(2,0));

        JLabel ordineLabel = new JLabel("Select the order's number to pay");
        JLabel ordineLabel2 = new JLabel("due to the purchase list number:");

        String[] ordini = getOrdini(utente);
        WideComboBox ordineChooses = new WideComboBox(ordini);
        ordineChooses.setPreferredSize(new Dimension(7,7));
        ordineChooses.setWide(true);

        gridPanel.add(ordineLabel); gridPanel.add(ordineLabel2); gridPanel.add(ordineChooses);

        ManageOrdersListener listener = new ManageOrdersListener(ordineChooses);
        south.add(ButtonCreator.createButton("Pay", true, ButtonCreator.LILLE, listener, ManageOrdersListener.PAY_BTN));
        south.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageOrders(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        this.repaint(); this.validate();

    }

    public String[] getOrdini(Utente utente) {
        OrdineDAO ordineDAO = OrdineDAO.getInstance();
        ArrayList<Ordine> ordini = ordineDAO.findAllOfUtente(utente);
        String[] listaOrdini = new String[ordini.size()];
        for (int i = 0; i < ordini.size(); i++) {
            listaOrdini[i] = "" + ordini.get(i).getIdOrdine();
        }
        return listaOrdini;
    }
}
