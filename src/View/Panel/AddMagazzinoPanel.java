package View.Panel;

import Business.PuntoVenditaBusiness;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.Prodotto;
import Model.PuntoVendita;
import Model.Servizio;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddMagazzinoPanel extends JPanel {

    public AddMagazzinoPanel(MainLayout window) {

        this.setLayout(new BorderLayout());

        //Pannelli
        JPanel infoPanel = new JPanel();
        JPanel prodottiPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(2, 2));
        prodottiPanel.setLayout(new GridLayout(0,4));



        //Info panel
        JLabel puntoVenditaLabel = new JLabel("Seleziona il punto vendita: ");



        String[] puntiVendita = getPuntiVendita();
        WideComboBox puntiVenditaChooses = new WideComboBox(puntiVendita);
        puntiVenditaChooses.setPreferredSize(new Dimension(7,7));
        puntiVenditaChooses.setWide(true);

        infoPanel.add(puntoVenditaLabel);
        infoPanel.add(puntiVenditaChooses);

        //Prodotti panel

        JLabel prodottiLabel = new JLabel("Prodotti: ");
        JLabel corsiaLabel = new JLabel("Corsia: ");
        JLabel scaffaleLabel = new JLabel("Scaffale: ");
        JLabel quantitaLabel = new JLabel("Quantità: ");

        prodottiPanel.add(prodottiLabel); prodottiPanel.add(corsiaLabel); prodottiPanel.add(scaffaleLabel); prodottiPanel.add(quantitaLabel);

        ArrayList<JCheckBox> prodottiBox = getProdottiCheckBox();
        JTextField[] corsia = new JTextField[prodottiBox.size()];
        JTextField[] scaffale = new JTextField[prodottiBox.size()];
        JTextField[] quantita = new JTextField[prodottiBox.size()];
        for (int i = 0; i < prodottiBox.size(); i++) {
            prodottiPanel.add(prodottiBox.get(i));
            corsia[i] = new JTextField();
            scaffale[i] = new JTextField();
            quantita[i] = new JTextField();
            prodottiPanel.add(corsia[i]);
            prodottiPanel.add(scaffale[i]);
            prodottiPanel.add(quantita[i]);
        }

        //JScrollPane scrollPane = new JScrollPane(prodottiPanel);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(infoPanel, BorderLayout.NORTH);
        add(prodottiPanel, BorderLayout.CENTER);
        //add(scrollPane);


        //Buttons panel
        ManagePointsListener listener = new ManagePointsListener(puntiVenditaChooses, prodottiBox, corsia, scaffale, quantita);
        buttonsPanel.add(ButtonCreator.createButton("Conferma", true, ButtonCreator.LILLE, listener, ManagePointsListener.ADD_MAG));
        buttonsPanel.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(prodottiPanel), null));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.managePoints(), null));

        add(buttonsPanel, BorderLayout.SOUTH);

        repaint();
        validate();
        setVisible(true);
    }

    public ArrayList<Integer> getSelectedIndex(ArrayList<JCheckBox> prodottiBox){
        ArrayList<Integer> selectedIndex = new ArrayList<>();
        for (int i = 0; i < prodottiBox.size(); i++) {
            if (prodottiBox.get(i).isSelected()) {
                selectedIndex.add(i);
            }
        }
        return selectedIndex;
    }

    public ArrayList<Prodotto> getSelectedProducts(ArrayList<JCheckBox> prodottiBox){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        for (int i = 0; i < prodottiBox.size(); i++) {
            if (prodottiBox.get(i).isSelected()) {
                prodotti.add((prodottoDAO.findByName(prodottiBox.get(i).getText())));
            }
        }
        return prodotti;
    }
    public ArrayList<JCheckBox> getProdottiCheckBox(){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();

        ArrayList<JCheckBox> boxes = new ArrayList<>();

        for (Prodotto p : prodotti){
            JCheckBox checkBox = new JCheckBox(p.getName());
            checkBox.setBorderPaintedFlat(true);
            boxes.add(checkBox);
        }

        return boxes;
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
