package View.Listener;

import Business.LoginResult;
import Business.SessionManager;
import Business.UtenteBusiness;
import View.MainLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener implements ActionListener {
    //Costanti
    public final static String LOGIN_BTN = "login-btn";
    public final static String LOGOUT_BTN = "logout-btn";

    private JTextField username;
    private JPasswordField password;
    private MainLayout frame; //passo tutto il frame

    public LoginListener(JTextField username, JPasswordField password) {
        this.username = username;
        this.password = password;
    }

    public LoginListener(MainLayout frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (LOGIN_BTN.equalsIgnoreCase(action)){
            String user = username.getText();
            String pwd = new String(password.getPassword());

            System.out.println("username: " + user);
            System.out.println("password: " + pwd);
            System.out.println("---------------------");

            if (user.contains(";")){
                JOptionPane.showMessageDialog(null, "Wrong Username");
                return;
            }

            LoginResult result = UtenteBusiness.getInstance().login(user,pwd);

            if(result.getResult() == LoginResult.Result.LOGIN_OK){
                frame.showUserLoggedPanel(result.getMessage());
                //refresh view pulsanti
                frame.updateButtonsMenu();
            }
            else {
                JOptionPane.showMessageDialog(null, result.getMessage());
            }
        }
        else if (LOGOUT_BTN.equalsIgnoreCase(action)) {
            frame.updateButtonsMenu();
            frame.showInitialPanel();
            SessionManager.refresh();
        }

        /*Object source = e.getSource(); //Ritorna istanza dell'oggetto che ha lanciato l'evento
        if (source instanceof JButton) {
            JButton btn = (JButton) source;
            System.out.println(btn.getText());
            String user = username.getText();
            String pwd = new String(password.getPassword());
            System.out.println("username: " + user);
            System.out.println("password: " + pwd);
            System.out.println("---------------------");
        }
        else if (source instanceof JMenuItem) {
            JOptionPane.showMessageDialog(null, "Seleziona File");
        }*/


    }

    public void setFrame(MainLayout frame) {
        this.frame = frame;
    }
}
