package View.Panel;

import Business.MagazzinoBusiness;
import Business.SessionManager;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Manager;
import Model.ProdottiMagazzino;
import Model.PuntoVendita;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManageStorePanel extends JPanel {
    public ManageStorePanel(MainLayout window) {
        Manager manager = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());

        if (!MagazzinoDAO.getInstance().existForPunto(punto)) {
            JOptionPane.showMessageDialog(null, "Non esiste un magazzino per il Punto Vendita: " + punto.getName());
        } else {
            JPanel gridPanel = new JPanel();
            JPanel south = new JPanel();
            this.setLayout(new BorderLayout());
            gridPanel.setLayout(new GridLayout(10, 1));
            south.setLayout(new GridLayout(2, 0));

            JLabel puntoVenditaLabel = new JLabel("Supply products in : " + punto.getName());

            JLabel prodottoLabel = new JLabel("Select the product you want to supply");

            String[] prodotti = getProdottibyManager(punto);
            WideComboBox prodottiChooses = new WideComboBox(prodotti);
            prodottiChooses.setPreferredSize(new Dimension(7, 7));
            prodottiChooses.setWide(true);

            JLabel quantitaLabel = new JLabel("Quantity (to add)");

            String[] numeri = new String[30];
            for (int i = 0; i < numeri.length; i++) {
                numeri[i] = "" + (i + 1);
            }
            WideComboBox quantita = new WideComboBox(numeri);
            quantita.setPreferredSize(new Dimension(7, 7));
            quantita.setWide(true);

            gridPanel.add(puntoVenditaLabel);
            gridPanel.add(prodottoLabel);
            gridPanel.add(prodottiChooses);
            {
                gridPanel.add(quantitaLabel);
                gridPanel.add(quantita);

                south.add(ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (prodottiChooses.getSelectedItem() == null){
                            JOptionPane.showMessageDialog(null, "Select a product first!");
                        }
                        MagazzinoBusiness.getInstance().rifornisciProdotto(prodottiChooses.getSelectedItem().toString(), Integer.parseInt(quantita.getSelectedItem().toString()), manager);
                    }
                }, null));

                this.add(gridPanel, BorderLayout.CENTER);
                this.add(south, BorderLayout.SOUTH);
                this.repaint();
                this.validate();

                setVisible(true);
            }
        }


    }
    public String[] getProdottibyManager (PuntoVendita punto){
        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
        MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
        ArrayList<ProdottiMagazzino> prodotti = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzinoDAO.findMagazzinoByPunto(punto));
        String[] nomiProdotti = new String[prodotti.size()];
        for (int i = 0; i < prodotti.size(); i++) {
            nomiProdotti[i] = prodotti.get(i).getProdotto().getName();
        }
        return nomiProdotti;
    }
}