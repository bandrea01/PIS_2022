package View.Panel;

import Business.UtenteBusiness;
import View.MainLayout;
import View.ViewModel.ButtonCreator;
import View.ViewModel.MyFont;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignInPanel extends JPanel {

    public SignInPanel(MainLayout window) {
        JPanel gridPanel = new JPanel();
        JPanel south = new JPanel();
        this.setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(10,2));
        south.setLayout(new GridLayout(2,0));


        JLabel nomeLabel = new JLabel("Name: ");
        JLabel cognomeLabel = new JLabel("Surname: ");
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel telefonoLabel = new JLabel("Phone: ");
        JLabel etaLabel = new JLabel("Age: ");
        JLabel residenzaLabel = new JLabel("City: ");
        JLabel professioneLabel = new JLabel("Job: ");

        JTextField nome = new JTextField(15);
        JTextField cognome = new JTextField();
        JTextField username = new JTextField();
        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();
        JTextField telefono = new JTextField();
        JTextField eta = new JTextField();
        JTextField residenza = new JTextField();
        JTextField professione = new JTextField();

        gridPanel.add(nomeLabel); gridPanel.add(nome);
        gridPanel.add(cognomeLabel); gridPanel.add(cognome);
        gridPanel.add(usernameLabel); gridPanel.add(username);
        gridPanel.add(emailLabel); gridPanel.add(email);
        gridPanel.add(passwordLabel); gridPanel.add(password);
        gridPanel.add(telefonoLabel); gridPanel.add(telefono);
        gridPanel.add(etaLabel); gridPanel.add(eta);
        gridPanel.add(residenzaLabel); gridPanel.add(residenza);
        gridPanel.add(professioneLabel); gridPanel.add(professione);

        gridPanel.add(ButtonCreator.createButton("Sign in", true, ButtonCreator.SLIME, e -> {
            UtenteBusiness utenteBusiness = new UtenteBusiness();
            try {
                boolean signed = utenteBusiness.signIn(nome.getText(), cognome.getText(), username.getText(), email.getText(),
                        String.valueOf(password.getPassword()), telefono.getText(), Integer.parseInt(eta.getText()), residenza.getText(),
                        professione.getText());
                if (signed){
                    window.showInitialPanel();
                    JOptionPane.showMessageDialog(null, "You are signed! Browse catalog or log in");
                }
            } catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null, "All fields are required");
            }
        }, null));
        gridPanel.add(ButtonCreator.createButton("Go back", true, ButtonCreator.SALMON, e -> window.showInitialPanel(), null));

        JLabel requirements1 = new JLabel("Username: 5-10 alphanumeric characters");
        JLabel requirements2 = new JLabel("Password: at least 8 alphanumeric characters");

        requirements1.setFont(new MyFont(14));
        requirements1.setForeground(Color.RED);
        requirements2.setFont(new MyFont(14));
        requirements2.setForeground(Color.RED);

        south.add(requirements1);
        south.add(requirements2);

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

    }
}
