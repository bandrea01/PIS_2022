package View.Panel;

import Business.SessionManager;
import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.*;
import View.Listener.ManageOrdersListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.RowCatalog;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AcquistaPanel extends JPanel {

    public AcquistaPanel(MainLayout window) {

        Utente utente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        JPanel infoPanel = new JPanel();
        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        infoPanel.setLayout(new GridLayout(10, 1));
        gridPanel.setLayout(new GridLayout(0, 2));
        south.setLayout(new GridLayout(2, 0));

        JLabel puntiVenditaLabel = new JLabel("Seleziona il punto vendita da cui acquistare: ");

        String[] nomiPuntiVendita = getPuntiVendita(utente);
        WideComboBox puntiVenditaChooses = new WideComboBox(nomiPuntiVendita);
        puntiVenditaChooses.setPreferredSize(new Dimension(7,7));
        puntiVenditaChooses.setWide(true);

        infoPanel.add(puntiVenditaLabel); infoPanel.add(puntiVenditaChooses);
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        puntiVenditaChooses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nomePunto = puntiVenditaChooses.getSelectedItem().toString();
                if (!"Seleziona un punto vendita".equalsIgnoreCase(nomePunto)) {

                    gridPanel.removeAll();

                    south.removeAll();


                    if (!MagazzinoDAO.getInstance().existForPunto(PuntoVenditaDAO.getInstance().findByName(nomePunto))) {
                        JOptionPane.showMessageDialog(null, "Il magazzino del punto vendita " + nomePunto + " è vuoto");
                        return;
                    }

                    ArrayList<JCheckBox> articoliBox = getArticoliCheckBox(nomePunto);

                    int[] quantitaProdotti = getQuantita(articoliBox, nomePunto);
                    WideComboBox[] quantitaChooses = new WideComboBox[quantitaProdotti.length];
                    for (int i = 0; i < quantitaChooses.length; i++) {
                        if (quantitaProdotti[i] == -1) {
                            JTextField servizioLabel = new JTextField("Tale articolo è un servizio");
                            servizioLabel.setEditable(false);
                            servizioLabel.setSize(new Dimension(1, 1));
                            gridPanel.add(articoliBox.get(i));
                            gridPanel.add(servizioLabel);
                        } else if (quantitaProdotti[i] == 0) {
                            JTextField prodottoEsaurito = new JTextField("Tale prodotto è esaurito");
                            prodottoEsaurito.setEditable(false);
                            prodottoEsaurito.setSize(new Dimension(1, 1));
                            articoliBox.get(i).setEnabled(false);
                            gridPanel.add(articoliBox.get(i));
                            gridPanel.add(prodottoEsaurito);
                        } else {
                            Integer[] quantita = new Integer[quantitaProdotti[i]];
                            for (int j = 0; j < quantita.length; j++) {
                                quantita[j] = j + 1;
                            }
                            quantitaChooses[i] = new WideComboBox(quantita);
                            quantitaChooses[i].setPreferredSize(new Dimension(1, 1));
                            quantitaChooses[i].setWide(true);
                            gridPanel.add(articoliBox.get(i));
                            gridPanel.add(quantitaChooses[i]);
                        }
                    }

                    ManageOrdersListener listener = new ManageOrdersListener(articoliBox, quantitaChooses, puntiVenditaChooses);
                    south.add(ButtonCreator.createButton("Acquista", true, ButtonCreator.LILLE, listener, ManageOrdersListener.BUY_BTN));
                    south.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(gridPanel), null));

                    window.repaint();
                    window.validate();
                }
            }
        });
    }

    public void selectAll(JPanel panel){
        for (Component c : panel.getComponents()){
            if (c instanceof JCheckBox){
                ((JCheckBox) c).setSelected(true);
            }
        }
        panel.repaint();
        panel.validate();
    }

    private int[] getQuantita(ArrayList<JCheckBox> boxes, String nomePunto) {
        int[] quantita = new int[boxes.size()];

        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
        PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findByName(nomePunto);
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        Magazzino magazzino = MagazzinoDAO.getInstance().findMagazzinoByPunto(puntoVendita);
        ArrayList<ProdottiMagazzino> prodottiMagazzino = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzino);

        for (int i = 0; i < prodottiMagazzino.size(); i++) {
            quantita[i] = prodottiMagazzinoDAO.findQuantitaProdotto(magazzino, prodottoDAO.findByName(boxes.get(i).getText()));
        }
        for (int i = prodottiMagazzino.size(); i < boxes.size(); i++) {
            quantita[i] = -1;
        }
        return quantita;
    }
    private ArrayList<JCheckBox> getArticoliCheckBox(String nomePunto) {
        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
        PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findByName(nomePunto);
        Magazzino magazzino = MagazzinoDAO.getInstance().findMagazzinoByPunto(puntoVendita);
        ArrayList<ProdottiMagazzino> prodottiMagazzino = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzino);
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        ArrayList<Prodotto> prodotti = new ArrayList<>();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

        ArrayList<JCheckBox> boxes = new ArrayList<>();
        for (int i = 0; i < prodottiMagazzino.size(); i++) {
            prodotti.add(prodottoDAO.findById(prodottiMagazzino.get(i).getProdotto().getId()));
        }
        for (Prodotto p : prodotti) {
            JCheckBox checkBox = new JCheckBox(p.getName());
            checkBox.setBorderPaintedFlat(true);
            checkBox.setSelected(false);
            boxes.add(checkBox);
        }
        ArrayList<Articolo> articoli = ArticoloPuntoVenditaDAO.getInstance().findByPunto(puntoVendita);
        for (Articolo a : articoli) {
            if (articoloDAO.isServizio(a.getId())) {
                Servizio s = ServizioDAO.getInstance().findById(a.getId());
                JCheckBox checkBox = new JCheckBox(s.getName());
                checkBox.setBorderPaintedFlat(true);
                checkBox.setSelected(false);
                boxes.add(checkBox);
            }
        }
        return boxes;
    }

    String[] getPuntiVendita(Utente utente) {
        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> puntiVendita = clientePuntoVenditaDAO.findAllbyCliente(utente);
        String[] nomiPuntiVendita = new String[puntiVendita.size() + 1];
        nomiPuntiVendita[0] = "Seleziona un punto vendita";
        for (int i = 1; i < puntiVendita.size() + 1; i++) {
            nomiPuntiVendita[i] = puntiVendita.get(i-1).getName();
        }
        return nomiPuntiVendita;
    }
}
