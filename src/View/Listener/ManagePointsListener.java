package View.Listener;

import Business.PuntoVenditaBusiness;
import Business.UtenteBusiness;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import View.MainLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagePointsListener implements ActionListener {
    public final static String MODIFY_BTN = "modify-btn";
    public final static String DELETE_BTN = "delete-btn";
    private MainLayout window;

    public ManagePointsListener(MainLayout window) {
        this.window = window;
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
        }
    }
}
