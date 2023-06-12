package View.Listener;

import Business.UtenteBusiness;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageClientiListener implements ActionListener {

    public final static String BAN_BTN = "ban_btn";
    public final static String UNBAN_BTN = "unban_btn";
    public final static String REMOVE_BTN = "remove_cliente_btn";

    private WideComboBox utente;
    private String puntoVendita;

    public ManageClientiListener(WideComboBox utente, String puntoVendita) {
        this.utente = utente;
        this.puntoVendita = puntoVendita;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (BAN_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleziona un cliente");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.banClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non esiste");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non è un cliente");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Il cliente selezionato è gia disabilitato");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Il cliente " + cliente + " è stato disabilitato");
                    break;
            }
        } else if (UNBAN_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleziona un cliente");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.unbanClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non esiste");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non è un cliente");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Il cliente selezionato è gia abilitato");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Il cliente " + cliente + " è stato abilitato");
                    break;
            }
        } else if (REMOVE_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleziona un cliente");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.removeClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non esiste");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "L'utente selezionato non è un cliente");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Il cliente " + cliente + " è stato cancellato correttamente");
                    break;
            }
        }
    }
}
