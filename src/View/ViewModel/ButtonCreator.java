package View.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCreator {
    private static JButton button;
    public static final Color LIGHT_BLUE = new Color(100,149,237);
    public static final Color SALMON = new Color (250,128,114);
    public static final Color LILLE = new Color (150,125,215);
    public static final Color SLIME = new Color (160, 210, 84);

    public static JButton createButton(String text, boolean opaque, int r, int g, int b, ActionListener l, String actionCommand){
        button = new JButton(text);
        button.setOpaque(opaque);
        button.setBackground(new Color(r,g,b));
        button.addActionListener(l);
        button.setActionCommand(actionCommand);
        return button;
    }
    public static JButton createButton(String text, boolean opaque, Color c, ActionListener l, String actionCommand){
        button = new JButton(text);
        button.setOpaque(opaque);
        button.setBackground(c);
        button.addActionListener(l);
        button.setActionCommand(actionCommand);
        return button;
    }


}
