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
import View.MainLayout;
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

        puntiVenditaChooses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridPanel.removeAll();

                String nomePunto = puntiVenditaChooses.getSelectedItem().toString();

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
                        servizioLabel.setSize(new Dimension(1,1));
                        gridPanel.add(articoliBox.get(i)); gridPanel.add(servizioLabel);
                    } else if (quantitaProdotti[i] == 0) {
                        JTextField prodottoEsaurito = new JTextField("Tale prodotto è esaurito");
                        prodottoEsaurito.setEditable(false);
                        prodottoEsaurito.setSize(new Dimension(1,1));
                        gridPanel.add(articoliBox.get(i)); gridPanel.add(prodottoEsaurito);
                    } else {
                        Integer[] quantita = new Integer[quantitaProdotti[i]];
                        for (int j = 0; j < quantita.length; j++) {
                            quantita[j] = j + 1;
                        }
                        quantitaChooses[i] = new WideComboBox(quantita);
                        quantitaChooses[i].setPreferredSize(new Dimension(1, 1));
                        quantitaChooses[i].setWide(true);
                        gridPanel.add(articoliBox.get(i)); gridPanel.add(quantitaChooses[i]);
                    }
                }

                infoPanel.repaint(); infoPanel.validate();
                gridPanel.repaint();
                gridPanel.validate();
            }
        });
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
        String[] nomiPuntiVendita = new String[puntiVendita.size()];
        for (int i = 0; i < puntiVendita.size(); i++) {
            nomiPuntiVendita[i] = puntiVendita.get(i).getName();
        }
        return nomiPuntiVendita;
    }
}
