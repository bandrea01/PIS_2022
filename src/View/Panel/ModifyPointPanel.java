package View.Panel;

import Business.PuntoVenditaBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Servizio.ServizioDAO;
import DAO.Utente.UtenteDAO;
import Model.Articolo;
import Model.Prodotto;
import Model.PuntoVendita;
import Model.Servizio;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifyPointPanel extends JPanel{

    PuntoVendita punto;

    public ModifyPointPanel(MainLayout window, PuntoVendita punto){
        this.punto = punto;
        this.setLayout(new BorderLayout(10,10));
        UtenteDAO utenteDAO = UtenteDAO.getInstance();

        //Pannelli
        JPanel infoPanel = new JPanel();
        JPanel articoliPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(2, 2));
        articoliPanel.setLayout(new GridLayout(0,2));

        //Info panel
        JLabel pointName = new JLabel("Sale point name: ");
        JLabel managerUsername = new JLabel("Manager email: ");
        JTextField pointNameField = new JTextField(punto.getName());
        JTextField managerUsernameField = new JTextField(utenteDAO.getManagerById(punto.getIdManager()).getEmail());

        infoPanel.add(pointName);
        infoPanel.add(pointNameField);
        infoPanel.add(managerUsername);
        infoPanel.add(managerUsernameField);

        //Articoli panel
        ArrayList<JCheckBox> articoliBox = getArticoliCheckBox(punto);
        for (JCheckBox c : articoliBox) {
            articoliPanel.add(c);
            articoliPanel.repaint(); articoliPanel.validate();
        }
        add(infoPanel, BorderLayout.NORTH);
        add(articoliPanel, BorderLayout.CENTER);

        //Buttons panel
        PuntoVenditaBusiness puntoVenditaBusiness = PuntoVenditaBusiness.getInstance();
        buttonsPanel.add(ButtonCreator.createButton("Conferma", true, ButtonCreator.LILLE, e -> puntoVenditaBusiness.modifyPunto(punto.getName(), pointNameField.getText(), managerUsernameField.getText(), getSelectedArticles(articoliPanel), window), null));
        buttonsPanel.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(articoliPanel), null));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.managePoints(), null));

        add(buttonsPanel, BorderLayout.SOUTH);

        repaint();
        validate();
    }
    public ArrayList<JCheckBox> getArticoliCheckBox(PuntoVendita punto){
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();

        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
        ArrayList<Servizio> servizi = servizioDAO.findAll();

        //Creo check box
        ArrayList<JCheckBox> boxes = new ArrayList<>();
        for (Prodotto p : prodotti){
            JCheckBox checkBox = new JCheckBox(p.getName());
            checkBox.setBorderPaintedFlat(true);
            checkBox.setSelected(false);
            boxes.add(checkBox);
        }
        for (Servizio s : servizi){
            JCheckBox checkBox = new JCheckBox(s.getName());
            checkBox.setBorderPaintedFlat(true);
            checkBox.setForeground(Color.BLUE);
            checkBox.setSelected(false);
            boxes.add(checkBox);
        }

        ArrayList<Articolo> articoliPunto = articoloPuntoVenditaDAO.findByPunto(punto);
        for (Articolo a : articoliPunto){
            for (JCheckBox c : boxes){
                if (a.getName().equalsIgnoreCase(c.getText())){
                    c.setSelected(true);
                }
            }
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
