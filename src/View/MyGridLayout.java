package View;

import javax.swing.*;
import java.awt.*;

public class MyGridLayout extends JFrame {

    public MyGridLayout(){
        super ("GridLayout");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container c = this.getContentPane();
        c.setLayout(new GridLayout(3,5));

        for (int i= 0; i < 25; i++){
            c.add(new JButton("Pulsante " + i));
        }

        this.setVisible(true);
    }
}
