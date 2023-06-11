package View.Panel;

import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Magazzino;
import Model.ProdottiMagazzino;
import Model.Prodotto;
import Model.PuntoVendita;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifyMagazzinoPanel extends JPanel {
    public ModifyMagazzinoPanel(MainLayout window) {
        this.setLayout(new BorderLayout(10,10));


        //Pannelli
        JPanel infoPanel = new JPanel();
        JPanel prodottiPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel southPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(2, 2));
        prodottiPanel.setLayout(new GridLayout(0,4));
        southPanel.setLayout(new GridLayout(2,2));

        //Info panel
        JLabel pointName = new JLabel("Magazzino del punto vendita: ");
        //JLabel info = new JLabel("Seleziona il punto vendita e poi premi aggiorna");


        String[] puntiVendita = getPuntiVendita();
        WideComboBox puntiVenditaChooses = new WideComboBox(puntiVendita);
        puntiVenditaChooses.setPreferredSize(new Dimension(7,7));
        puntiVenditaChooses.setWide(true);

        infoPanel.add(pointName); infoPanel.add(puntiVenditaChooses);

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


        puntiVenditaChooses.addActionListener(e -> updateProdottiCheckBox(prodottiBox, puntiVenditaChooses, corsia, scaffale, quantita));


        add(infoPanel, BorderLayout.NORTH);
        add(prodottiPanel, BorderLayout.CENTER);
        this.validate(); this.repaint();

        //Buttons panel

        ManagePointsListener listener = new ManagePointsListener(puntiVenditaChooses, prodottiBox, corsia, scaffale, quantita);
        buttonsPanel.add(ButtonCreator.createButton("Conferma", true, ButtonCreator.LILLE, listener, ManagePointsListener.MODIFY_MAG));
        buttonsPanel.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(prodottiPanel), null));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.managePoints(), null));

        add(buttonsPanel, BorderLayout.SOUTH);

        repaint();
        validate();
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

    public void updateProdottiCheckBox(ArrayList<JCheckBox> prodottiBox, WideComboBox puntiVendita, JTextField[] corsia, JTextField[] scaffale, JTextField[] quantita) {
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByName(puntiVendita.getSelectedItem().toString());
        Magazzino magazzino = MagazzinoDAO.getInstance().findMagazzinoByPunto(punto);
        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();


        ArrayList<ProdottiMagazzino> prodottiMagazzino = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzino);
        for (int i = 0; i < prodottiBox.size(); i++) {
            prodottiBox.get(i).setSelected(false);
            corsia[i].setText("");
            scaffale[i].setText("");
            quantita[i].setText("");
        }
        for (ProdottiMagazzino p : prodottiMagazzino){
            for (int i = 0; i < prodottiBox.size(); i++) {
                if (p.getProdotto().getName().equalsIgnoreCase(prodottiBox.get(i).getText())) {
                    prodottiBox.get(i).setSelected(true);
                    for (int j = 0; j < prodottiMagazzino.size(); j++) {
                        if (prodottiBox.get(i).getText().equalsIgnoreCase(prodottiMagazzino.get(j).getProdotto().getName())) {
                            corsia[i].setText(Integer.toString(prodottiMagazzino.get(j).getCollocazione().getCorsia()));
                            scaffale[i].setText(Integer.toString(prodottiMagazzino.get(j).getCollocazione().getScaffale()));
                            quantita[i].setText(Integer.toString(prodottiMagazzino.get(j).getQuantita()));
                        }
                    }
                }
            }
        }
    }
    public ArrayList<JCheckBox> getProdottiCheckBox(){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();

        //Creo check box
        ArrayList<JCheckBox> boxes = new ArrayList<>();
        for (Prodotto p : prodotti){
            JCheckBox checkBox = new JCheckBox(p.getName());
            checkBox.setBorderPaintedFlat(true);
            checkBox.setSelected(false);
            boxes.add(checkBox);
        }

        return boxes;
    }

    public String[] getPuntiVendita() {
        ArrayList<PuntoVendita> puntiVendita = PuntoVenditaDAO.getInstance().findAll();
        String[] nomiPuntiVendita = new String[puntiVendita.size() + 1];
        nomiPuntiVendita[0] = "Seleziona punto vendita";
        for (int i = 1; i < puntiVendita.size() + 1; i++) {
            nomiPuntiVendita[i] = puntiVendita.get(i-1).getName();
        }
        return nomiPuntiVendita;
    }
}
