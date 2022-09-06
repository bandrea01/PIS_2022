package View.Decorator;

import View.MainLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class GuestMenuDecorator {
    protected MainLayout window;
    protected JButton signIn;
    protected JButton browse;

    public JButton createButton(String text, boolean opaque, int r, int g, int b, ActionListener l){
        return new JButton();
    }
    public JButton getSignIn(){
        return signIn;
    }
    public JButton getBrowse(){
        return browse;
    }
}
