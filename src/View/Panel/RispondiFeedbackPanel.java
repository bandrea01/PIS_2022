package View.Panel;

import Business.SessionManager;
import DAO.Feedback.FeedbackDAO;
import Model.Feedback;
import Model.Utente;
import View.Listener.ManageClientiListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RispondiFeedbackPanel extends JPanel {
    public RispondiFeedbackPanel(MainLayout window) {
        Utente manager = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        if (!FeedbackDAO.getInstance().managerHasFeedback(manager)) {
            JOptionPane.showMessageDialog(null, "None of your customers have posted feedbacks yet");
            window.manageClienti();
            return;
        }

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 1));
        south.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("Select the feedback you want to reply to");
        JLabel idLabel1 = new JLabel(" due to the idFeedback received via email");

        String[] id = getIdFeedback(manager);
        WideComboBox idChooses = new WideComboBox(id);
        idChooses.setPreferredSize(new Dimension(7,7));
        idChooses.setWide(true);

        JLabel rispostaLabel = new JLabel("Text: ");
        JTextField risposta = new JTextField();

        gridPanel.add(idLabel); gridPanel.add(idLabel1); gridPanel.add(idChooses);
        gridPanel.add(rispostaLabel); gridPanel.add(risposta);

        ManageClientiListener listener = new ManageClientiListener(risposta, idChooses);
        south.add(ButtonCreator.createButton("Reply", true, ButtonCreator.LILLE, listener, ManageClientiListener.REPLY_FEEDBACK_BTN));
        south.add(ButtonCreator.createButton("Go back", true, ButtonCreator.LILLE, e -> window.manageClienti(), null));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        this.repaint(); this.validate();

        setVisible(true);
    }

    public String[] getIdFeedback(Utente manager) {
        FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
        ArrayList<Feedback> feedbacks = feedbackDAO.findAllOfManager(manager);
        String[] id = new String[feedbacks.size()];
        for (int i = 0; i < feedbacks.size(); i++) {
            id[i] = "" + feedbacks.get(i).getIdFeedback();
        }
        return id;
    }
}
