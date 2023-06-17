package View.Listener;

import Business.MailHelper;
import Business.UtenteBusiness;
import DAO.Feedback.FeedbackDAO;
import DAO.Utente.UtenteDAO;
import Model.Feedback;
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
    public final static String REPLY_FEEDBACK_BTN = "reply-btn";

    private WideComboBox utente;
    private String puntoVendita;
    private JTextField oggetto;
    private JTextField testo;
    private String username;
    private WideComboBox puntiVendita;
    private WideComboBox canalePreferito;
    private WideComboBox id;

    public ManageClientiListener(JTextField testo, WideComboBox id) {
        this.testo = testo;
        this.id = id;
    }

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
                JOptionPane.showMessageDialog(null, "Select a customer");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.banClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Selected user doesn't exist");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Selected user is not your customer");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Selected user is alredy banned");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Client " + cliente + " is now banned");
                    break;
            }
        } else if (UNBAN_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a customer");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.unbanClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Selected user doesn't exist");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Selected user is not your customer");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Selected customer is not banned");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Customer " + cliente + " is now unbanned");
                    break;
            }
        } else if (REMOVE_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a customer");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String cliente = utente.getSelectedItem().toString();
            int result = utenteBusiness.removeClient(cliente, puntoVendita);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Selected user doesn't exist");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Selected user is not your customer");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Customer " + cliente + " canceled correctly");
                    break;
            }
        } else if (SEND_EMAIL_BTN.equals(e.getActionCommand())) {
            if (utente.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a customer");
                return;
            }
            MailHelper mailHelper = MailHelper.getInstance();
            Utente cliente = UtenteDAO.getInstance().findByUsername(utente.getSelectedItem().toString());
            String email = cliente.getEmail();
            String object = oggetto.getText();
            String text = testo.getText();
            mailHelper.send(email, object, text, null);
            JOptionPane.showMessageDialog(null, "Email send correctly to : " + cliente.getUsername());
        } else if (SIGN_IN_BTN.equals(e.getActionCommand())) {
            if (puntiVendita.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a sale point");
                return;
            }
            UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
            String punto = puntiVendita.getSelectedItem().toString();
            String favouriteChannel = canalePreferito.getSelectedItem().toString();
            int result = utenteBusiness.registraCliente(username, punto, favouriteChannel);
            switch (result) {
                case 1:
                    JOptionPane.showMessageDialog(null, "You alredy signed in sale point: " + punto);
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "You are now signed in:  " + punto + " !");
                    break;
            }
        } else if (REPLY_FEEDBACK_BTN.equals(e.getActionCommand())) {
            int idFeedback = Integer.parseInt(id.getSelectedItem().toString());
            FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
            Feedback feedback = feedbackDAO.findById(idFeedback);
            feedback.setRisposta(testo.getText());
            feedbackDAO.update(feedback);

            MailHelper mailHelper = MailHelper.getInstance();
            String text = "Commento: \"" + feedback.getCommento() + "\"\nRisposta: \"" + feedback.getRisposta() + "\"";
            mailHelper.send(feedback.getUtente().getEmail(), "RISPOSTA AL FEEDBACK SULL'ARTICOLO: " + feedback.getArticolo().getName(), text, null);
            JOptionPane.showMessageDialog(null, "Your reply has been successfully sent to the user " + feedback.getUtente().getUsername());
        }
    }
}
