package View.Listener;

import Business.MailHelper;
import Business.UtenteBusiness;
import DAO.Utente.UtenteDAO;
import Model.Utente;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageClientiListener implements ActionListener {

    public final static String BAN_BTN = "ban_btn";
    public final static String UNBAN_BTN = "unban_btn";
    public final static String REMOVE_BTN = "remove_cliente_btn";
    public final static String SEND_EMAIL_BTN = "send_email_btn";
    public final static String SIGN_IN_BTN = "sign-in-client-btn";

    private WideComboBox utente;
    private String puntoVendita;
    private JTextField oggetto;
    private JTextField testo;
    private String username;
    private WideComboBox puntiVendita;
    private WideComboBox canalePreferito;

    public ManageClientiListener(String username, WideComboBox puntiVendita, WideComboBox canalePreferito) {
        this.username = username;
        this.puntiVendita = puntiVendita;
        this.canalePreferito = canalePreferito;
    }

    public ManageClientiListener(WideComboBox utente, String puntoVendita) {
        this.utente = utente;
        this.puntoVendita = puntoVendita;
    }

    public ManageClientiListener(WideComboBox utente, JTextField oggetto, JTextField testo) {
        this.utente = utente;
        this.oggetto = oggetto;
        this.testo = testo;
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
        } else if (SEND_EMAIL_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleziona un cliente");
                return;
            }
            MailHelper mailHelper = MailHelper.getInstance();
            Utente cliente = UtenteDAO.getInstance().findByUsername(utente.getSelectedItem().toString());
            String email = cliente.getEmail();
            String object = oggetto.getText();
            String text = testo.getText();
            mailHelper.send(email, object, text, null);
            JOptionPane.showMessageDialog(null, "L'email è stata inviata correttamente al cliente " + cliente.getUsername());
        } else if (SIGN_IN_BTN.equals(e.getActionCommand())) {
            if (puntiVendita.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleziona un punto vendita");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String punto = puntiVendita.getSelectedItem().toString();
            String favouriteChannel = canalePreferito.getSelectedItem().toString();
            int result = utenteBusiness.registraCliente(username, punto, favouriteChannel);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Sei già registrato nel punto vendita: " + punto);
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Registrazione al punto vendita " + punto + " avvenuta correttamente");
                    break;
            }
        }
    }
}
