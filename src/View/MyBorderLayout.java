package View;

import javax.swing.*;
import java.awt.*;

public class MyBorderLayout extends JFrame {
    private JButton nord = new JButton("NORD");
    private JButton sud = new JButton("SUD");
    private JButton est = new JButton("EST");
    private JButton ovest = new JButton("OVEST");
    private JButton centro = new JButton("CENTRO");

    public MyBorderLayout(){
        super ("MyBorderLayout");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(nord, BorderLayout.NORTH);
        //c.add(est, BorderLayout.WEST);
        //c.add(ovest, BorderLayout.EAST);
        //c.add(sud, BorderLayout.SOUTH);
        c.add(centro, BorderLayout.CENTER);


        this.setVisible(true);
    }
}
