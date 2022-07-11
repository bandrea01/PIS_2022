package View;

import javax.swing.*;
import java.awt.*;

public class MyFlowLayout extends JFrame {

    public MyFlowLayout(){
        super ("FlowLayout");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container c = this.getContentPane();
        c.setLayout(new FlowLayout());

        //c.add(new JButton("Pulsante 1"));
        //c.add(new JButton("Pulsante 2"));
        //c.add(new JButton("Pulsante 3"));
        //c.add(new JButton("Pulsante 4"));

        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton login = new JButton("Login");

        c.add(username);
        c.add(password);
        c.add(login);

        this.setVisible(true);
    }
}
