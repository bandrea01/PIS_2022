package View.Listener;

import Business.MagazzinoBusiness;
import Business.PuntoVenditaBusiness;
import Business.UtenteBusiness;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Manager;
import Model.PuntoVendita;
import View.MainLayout;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagePointsListener implements ActionListener {
    public final static String MODIFY_BTN = "modify-btn";
    public final static String DELETE_BTN = "delete-btn";
    public final static String DELETE_MAN_BTN = "delete-man-btn";
    public final static String ADD_MAG = "addmagazzino-btn";
    public final static String MODIFY_MAG = "modifymagazzino-btn";

    private MainLayout window;

    private WideComboBox puntoVendita;
    private ArrayList<JCheckBox> prodottiBox;
    private JTextField[] corsia;
    private JTextField[] scaffale;
    private JTextField[] quantita;

    public ManagePointsListener(MainLayout window) {
        this.window = window;
    }

    public ManagePointsListener(WideComboBox puntoVendita, ArrayList<JCheckBox> prodottiBox, JTextField[] corsia, JTextField[] scaffale, JTextField[] quantita) {
        this.puntoVendita = puntoVendita;
        this.prodottiBox = prodottiBox;
        this.corsia = corsia;
        this.scaffale = scaffale;
        this.quantita = quantita;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (MODIFY_BTN.equalsIgnoreCase(action)) {
            PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
            ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
            Object[] options = new Object[punti.size()];
            for (int i = 0; i < punti.size(); i++) {
                options[i] = punti.get(i).getName();
            }
            String input = (String) JOptionPane.showInputDialog(
                    null, "Select sale points to modify", null,
                    JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (input == null){
                return;
            }
            window.modifyPointPanel(puntoVenditaDAO.findByName(input));
        } else if (DELETE_BTN.equalsIgnoreCase(action)){
            PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
            ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
            Object[] options = new Object[punti.size()];
            for (int i = 0; i < punti.size(); i++){
                options[i] = punti.get(i).getName();
            }
            String input = (String) JOptionPane.showInputDialog(
                    null, "Select sale points you want to delete", null ,
                    JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (input == null){
                return;
            }
            PuntoVenditaBusiness puntoVenditaBusiness = new PuntoVenditaBusiness();
            puntoVenditaBusiness.deletePunto(puntoVenditaDAO.findByName(input));
            window.managePoints();
        } else if (DELETE_MAN_BTN.equalsIgnoreCase(action)){
            UtenteDAO utenteDAO = UtenteDAO.getInstance();
            ArrayList<Manager> managers = utenteDAO.findAllManagers();
            Object[] options = new Object[managers.size()];
            for (int i = 0; i < managers.size(); i++){
                options[i] = managers.get(i).getUsername();
            }
            String input = (String) JOptionPane.showInputDialog(
                    null, "Select managers you want to delete", null ,
                    JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (input == null){
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            utenteBusiness.deleteManager(input);
        } else if (ADD_MAG.equals(action)) {
            ArrayList<Integer> selectedIndex = new ArrayList<>();
            for (int i = 0; i < selectedIndex.size(); i++) {
                selectedIndex.remove(i);
            }
            for (int i = 0; i < prodottiBox.size(); i++) {
                if (prodottiBox.get(i).isSelected()) {
                    selectedIndex.add(i);
                }
            }

            if (puntoVendita.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insert sale point");
                return;
            }
            String nomePuntoVendita = puntoVendita.getSelectedItem().toString();
            String[] nomeProdotti = new String[selectedIndex.size()];
            int[] nCorsia = new int[selectedIndex.size()];
            int[] nScaffale = new int[selectedIndex.size()];
            int[] nQuantita = new int[selectedIndex.size()];
            for (int i = 0; i < selectedIndex.size(); i++) {
                if (corsia[selectedIndex.get(i)].getText().isEmpty()|| scaffale[selectedIndex.get(i)].getText().isEmpty() || quantita[selectedIndex.get(i)].getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Insert number of ward, shelf and quantity for selected products.");
                    return;
                }
                nomeProdotti[i] = prodottiBox.get(selectedIndex.get(i)).getText();
                nCorsia[i] = Integer.parseInt(corsia[selectedIndex.get(i)].getText());
                nScaffale[i] = Integer.parseInt(scaffale[selectedIndex.get(i)].getText());
                nQuantita[i] = Integer.parseInt(quantita[selectedIndex.get(i)].getText());
            }
            MagazzinoBusiness magazzinoBusiness = MagazzinoBusiness.getInstance();

            int result = magazzinoBusiness.addMagazzino(nomePuntoVendita, nomeProdotti, nCorsia, nScaffale, nQuantita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Insert number of ward, shelf and quantity for selected products");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Sale point selected has alredy a store");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "This product is not in this sale point");
                    return;
                case 0:
                    JOptionPane.showMessageDialog(null, "Store correctly inserted");
                    break;
            }
        } else if (MODIFY_MAG.equals(action)) {
            ArrayList<Integer> selectedIndex = new ArrayList<>();
            ArrayList<Integer> unselectedIndex = new ArrayList<>();
            for (int i = 0; i < selectedIndex.size(); i++) {
                selectedIndex.remove(i);
            }
            for (int i = 0; i < unselectedIndex.size(); i++) {
                unselectedIndex.remove(i);
            }
            for (int i = 0; i < prodottiBox.size(); i++) {
                if (prodottiBox.get(i).isSelected()) {
                    selectedIndex.add(i);
                } else {
                    unselectedIndex.add(i);
                }
            }

            if (puntoVendita.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insert sale point");
                return;
            }
            String nomePuntoVendita = puntoVendita.getSelectedItem().toString();
            String[] nomeProdotti = new String[selectedIndex.size()];
            int[] nCorsia = new int[selectedIndex.size()];
            int[] nScaffale = new int[selectedIndex.size()];
            int[] nQuantita = new int[selectedIndex.size()];
            for (int i = 0; i < selectedIndex.size(); i++) {
                if (corsia[selectedIndex.get(i)].getText().isEmpty()|| scaffale[selectedIndex.get(i)].getText().isEmpty() || quantita[selectedIndex.get(i)].getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Insert number of ward, shelf and quantity for selected products");
                    return;
                }
                nomeProdotti[i] = prodottiBox.get(selectedIndex.get(i)).getText();
                nCorsia[i] = Integer.parseInt(corsia[selectedIndex.get(i)].getText());
                nScaffale[i] = Integer.parseInt(scaffale[selectedIndex.get(i)].getText());
                nQuantita[i] = Integer.parseInt(quantita[selectedIndex.get(i)].getText());
            }

            String[] nomeProdottiUnselected = new String[unselectedIndex.size()];
            for (int i = 0; i < unselectedIndex.size(); i++) {
                nomeProdottiUnselected[i] = prodottiBox.get(unselectedIndex.get(i)).getText();
            }

            MagazzinoBusiness magazzinoBusiness = MagazzinoBusiness.getInstance();
            int result = magazzinoBusiness.modifyMagazzino(nomePuntoVendita, nomeProdotti, nomeProdottiUnselected, nCorsia, nScaffale, nQuantita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Select a sale point");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Sale point salected has not a store yet");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "This product is not in this sale point");
                    return;
                case 0:
                    JOptionPane.showMessageDialog(null, "Store modified correctly");
                    break;
            }
        }
    }
}
