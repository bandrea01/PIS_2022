package View;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyFrame extends JFrame {
    public MyFrame(int width, int height, boolean visible){
        super("My Shop");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(visible);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to MyShop");
        JButton button = new JButton("Click me!");


        Container c = this.getContentPane();
        c.add(label);
        c.add(button);
    }
}
