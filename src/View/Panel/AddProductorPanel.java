package View.Panel;

import View.Listener.ManageArticlesListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;

public class AddProductorPanel extends JPanel {
    public AddProductorPanel(MainLayout window) {

        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10, 2));
        south.setLayout(new GridLayout(2, 0));


        JLabel nomeLabel = new JLabel("Nome: ");
        JLabel sitoLabel = new JLabel("Sito: ");
        JLabel cittaLabel = new JLabel("Città: ");
        JLabel nazioneLabel = new JLabel("Nazione: ");

        JTextField nome = new JTextField(15);
        JTextField sito = new JTextField();
        JTextField citta = new JTextField();
        JTextField nazione = new JTextField();

        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(sitoLabel); gridPanel.add(sito);
        gridPanel.add(cittaLabel); gridPanel.add(citta);
        gridPanel.add(nazioneLabel); gridPanel.add(nazione);

        ManageArticlesListener listener = new ManageArticlesListener(nome, sito, citta, nazione);
        JButton confirm = ButtonCreator.createButton("Confirm", true, ButtonCreator.LILLE, listener, ManageArticlesListener.ADD_PRODUCTOR);
        south.add(confirm);
        JButton back = ButtonCreator.createButton("Go Back", true, ButtonCreator.LILLE, e -> window.manageArticles(), null);
        south.add(back);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        setVisible(true);
    }
}
