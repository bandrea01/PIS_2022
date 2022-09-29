package View.Panel;

import Business.PuntoVenditaBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import DAO.Utente.UtenteDAO;
import Model.*;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreatePointPanel extends JPanel {


    private MainLayout window;

    public CreatePointPanel(MainLayout window) {
        this.window = window;
        this.setLayout(new BorderLayout());

        //Pannelli
        JPanel infoPanel = new JPanel();
        JPanel articoliPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        infoPanel.setLayout(new GridLayout(2, 2));
        articoliPanel.setLayout(new GridLayout(0,2));

        //Info panel
        JLabel pointName = new JLabel("Sale point name: ");
        JLabel managerUsername = new JLabel("Manager email: ");
        JTextField pointNameField = new JTextField();

        String[] managerEmails = getManagerEmails();
        WideComboBox emailChooses = new WideComboBox(managerEmails);
        emailChooses.setPreferredSize(new Dimension(7,7));
        emailChooses.setWide(true);

        infoPanel.add(pointName);
        infoPanel.add(pointNameField);
        infoPanel.add(managerUsername);
        infoPanel.add(emailChooses);

        //Articoli panel
        ArrayList<JCheckBox> articoliBox = getArticoliCheckBox();
        for (JCheckBox c : articoliBox) {
            articoliPanel.add(c);
        }
        add(infoPanel, BorderLayout.NORTH);
        add(articoliPanel, BorderLayout.CENTER);

        //Buttons panel
        PuntoVenditaBusiness puntoVenditaBusiness = new PuntoVenditaBusiness();
        String selectedEmail = (String) emailChooses.getSelectedItem();
        buttonsPanel.add(ButtonCreator.createButton("Conferma", true, ButtonCreator.LILLE, e -> puntoVenditaBusiness.createPuntoVendita(pointNameField.getText(), selectedEmail, getSelectedArticles(articoliPanel), window) , null));
        buttonsPanel.add(ButtonCreator.createButton("Select all", true, ButtonCreator.LILLE, e -> selectAll(articoliPanel), null));
        buttonsPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.managePoints(), null));

        add(buttonsPanel, BorderLayout.SOUTH);

        repaint();
        validate();
    }

    private String[] getManagerEmails() {
        ArrayList<Manager> managers = UtenteDAO.getInstance().findAllManagers();
        String[] emails = new String[managers.size()];
        for (int i = 0; i < managers.size(); i++){
            emails[i] = managers.get(i).getEmail();
        }
        return emails;
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
