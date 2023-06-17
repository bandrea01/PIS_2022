package View.Panel;

import Business.SessionManager;
import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Feedback.FeedbackDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Articolo;
import Model.Feedback;
import Model.PuntoVendita;
import Model.Utente;
import View.MainLayout;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MostraFeedbackPanel extends JPanel {
    public MostraFeedbackPanel(MainLayout window) {
        Utente manager = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        if (!FeedbackDAO.getInstance().managerHasFeedback(manager)) {
            JOptionPane.showMessageDialog(null, "None of your customers have posted feedbacks yet");
            window.manageClienti();
            return;
        }

        JPanel info = new JPanel();
        JPanel grid = new JPanel();
        this.setLayout(new BorderLayout());
        info.setLayout(new GridLayout(10, 1));
        grid.setLayout(new GridLayout(0, 5));

        JLabel articleLabel = new JLabel("Select the article you want to see the feedbacks");

        String[] articles = getArticoli(manager);
        WideComboBox articleChooses = new WideComboBox(articles);
        articleChooses.setPreferredSize(new Dimension(7,7));
        articleChooses.setWide(true);

        info.add(articleLabel); info.add(articleChooses);

        JLabel idFeedback = new JLabel("idFeedback");
        JLabel commento = new JLabel("Text");
        JLabel gradimento = new JLabel("Approval rating");
        JLabel risposta = new JLabel("Reply");
        JLabel utente = new JLabel("User");

        this.add(info, BorderLayout.NORTH);
        this.add(grid, BorderLayout.CENTER);

        articleChooses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                grid.removeAll();
                String selectedItem = articleChooses.getSelectedItem().toString();
                if (!"Select an article".equalsIgnoreCase(selectedItem)) {

                    window.setSize(1250,500);
                    grid.add(idFeedback); grid.add(commento); grid.add(gradimento); grid.add(risposta); grid.add(utente);

                    Articolo articolo = ArticoloDAO.getInstance().findByName(selectedItem);
                    FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
                    ArrayList<Feedback> feedbacks = feedbackDAO.findAllOfArticolo(articolo);
                    for (int i = 0; i < feedbacks.size(); i++) {
                        JTextArea id = new JTextArea("" + feedbacks.get(i).getIdFeedback());
                        JTextArea text = new JTextArea(feedbacks.get(i).getCommento());
                        JTextArea rating = new JTextArea(feedbacks.get(i).getGradimento().toString());
                        JTextArea reply = new JTextArea();
                        if (feedbacks.get(i).getRisposta() == null) {
                            reply.setText("No reply");
                        } else {
                            reply.setText(feedbacks.get(i).getRisposta());
                        }
                        JTextArea user = new JTextArea(feedbacks.get(i).getUtente().getUsername());
                        id.setEditable(false); text.setEditable(false); rating.setEditable(false); reply.setEditable(false); user.setEditable(false);
                        grid.add(id); grid.add(text); grid.add(rating); grid.add(reply); grid.add(user);
                    }
                }
                window.repaint(); window.validate();
            }
        });

    }

    public String[] getArticoli(Utente manager) {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByIdManager(manager.getId());
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        ArrayList<Articolo> articoli = articoloPuntoVenditaDAO.findByPunto(puntoVendita);
        String[] nomiArticoli = new String[articoli.size() + 1];
        nomiArticoli[0] = "Select an article";
        for (int i = 1; i < articoli.size() + 1; i++) {
            nomiArticoli[i] = articoli.get(i-1).getName();
        }
        return nomiArticoli;
    }
}
