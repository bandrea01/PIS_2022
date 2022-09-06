package View;

import Business.UtenteBusiness;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignInPanel extends JPanel {
    public SignInPanel(MainLayout frame) {
        setLayout(new GridLayout(10,2));
        JLabel nomeLabel = new JLabel("Name: ");
        JLabel cognomeLabel = new JLabel("Surname: ");
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel telefonoLabel = new JLabel("Phone: ");
        JLabel etaLabel = new JLabel("Age: ");
        JLabel residenzaLabel = new JLabel("City: ");
        JLabel professioneLabel = new JLabel("Job: ");

        JTextField nome = new JTextField();
        JTextField cognome = new JTextField();
        JTextField username = new JTextField();
        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();
        JTextField telefono = new JTextField();
        JTextField eta = new JTextField();
        JTextField residenza = new JTextField();
        JTextField professione = new JTextField();

        add(nomeLabel); add(nome);
        add(cognomeLabel); add(cognome);
        add(usernameLabel); add(username);
        add(emailLabel); add(email);
        add(passwordLabel); add(password);
        add(telefonoLabel); add(telefono);
        add(etaLabel); add(eta);
        add(residenzaLabel); add(residenza);
        add(professioneLabel); add(professione);

        JButton sign = new JButton("Sign In");
        JButton back = new JButton("Go back");
        sign.addActionListener(e -> {
            UtenteBusiness utenteBusiness = new UtenteBusiness();
            try {
                boolean signed = utenteBusiness.signIn(nome.getText(), cognome.getText(), username.getText(), email.getText(),
                        String.valueOf(password.getPassword()), telefono.getText(), Integer.parseInt(eta.getText()), residenza.getText(),
                        professione.getText());
                if (signed){
                    frame.showInitialPanel();
                    JOptionPane.showMessageDialog(null, "You are signed! Browse catalog or log in");
                }
            } catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null, "All fields are required");
            }
        });
        back.addActionListener(e -> frame.showInitialPanel());

        add(sign);
        add(back);


    }
}
