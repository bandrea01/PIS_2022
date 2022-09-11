package View.Panel;

import Business.PuntoVenditaBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.Articolo;
import Model.Prodotto;
import Model.PuntoVendita;
import Model.Servizio;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreatePointPanel extends JPanel {


    private MainLayout window;

    public CreatePointPanel(MainLayout window) {
        this.window = window;
        this.setLayout(new BorderLayout(10,10));

        //Pannelli
        JPanel infoPanel = new JPanel();
        JPanel articoliPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(2, 2));
        articoliPanel.setLayout(new GridLayout(0,2));

        //Info panel
        JLabel pointName = new JLabel("Sale point name: ");
        JLabel managerUsername = new JLabel("Manager username: ");
        JTextField pointNameField = new JTextField();
        JTextField managerUsernameField = new JTextField();

        infoPanel.add(pointName);
        infoPanel.add(pointNameField);
        infoPanel.add(managerUsername);
        infoPanel.add(managerUsernameField);

        //Articoli panel
        ArrayList<JCheckBox> articoliBox = getArticoliCheckBox();
        for (JCheckBox c : articoliBox) {
            articoliPanel.add(c);
            articoliPanel.repaint(); articoliPanel.validate();
        }
        add(infoPanel, BorderLayout.NORTH);
        add(articoliPanel, BorderLayout.CENTER);

        //Buttons panel
        PuntoVenditaBusiness puntoVenditaBusiness = new PuntoVenditaBusiness();
        buttonsPanel.add(ButtonCreator.createButton("Conferma", true, ButtonCreator.LILLE, e -> puntoVenditaBusiness.createPuntoVendita(pointNameField.getText(), managerUsernameField.getText(), getSelectedArticles(articoliPanel), window) , null));
        buttonsPanel.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(articoliPanel), null));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.managePoints(), null));

        add(buttonsPanel, BorderLayout.SOUTH);

        repaint();
        validate();
    }

    public ArrayList<JCheckBox> getArticoliCheckBox(){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();

        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
        ArrayList<Servizio> servizi = servizioDAO.findAll();

        ArrayList<JCheckBox> boxes = new ArrayList<>();

        for (Prodotto p : prodotti){
            JCheckBox checkBox = new JCheckBox(p.getName());
            checkBox.setBorderPaintedFlat(true);
            boxes.add(checkBox);
        }
        for (Servizio s : servizi){
            JCheckBox checkBox = new JCheckBox(s.getName());
            checkBox.setBorderPaintedFlat(true);
            checkBox.setForeground(Color.BLUE);
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
    public ArrayList<Articolo> getSelectedArticles(JPanel panel){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Articolo> articoli = new ArrayList<>();
        for (Component c : panel.getComponents()){
            if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()){
                articoli.add(articoloDAO.findByName(((JCheckBox) c).getText()));
            }
        }
        return articoli;
    }
}
