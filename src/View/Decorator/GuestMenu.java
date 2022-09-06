package View.Decorator;

import View.MainLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Menu con elementi relativi al primo ingresso per il Guest
 * Passo al costruttore tutta la finestra
 */

public class GuestMenu extends GuestMenuDecorator {
    public GuestMenu(MainLayout window) {
        this.window = window;
        //Browse
        this.browse = createButton("Browse Catalog",true,150,125,215, e -> window.showCatalog());
        //SignIn
        this.signIn = createButton("Sign In", true, 150, 125, 215, e -> window.showSignInPanel());
    }

    @Override
    public JButton createButton(String text, boolean opaque, int r, int g, int b, ActionListener l){
        JButton button = new JButton(text);
        button.setOpaque(opaque);
        button.setBackground(new Color(r,g,b));
        button.addActionListener(l);
        return button;
    }

    @Override
    public JButton getSignIn() {
        return super.getSignIn();
    }

    @Override
    public JButton getBrowse() {
        return super.getBrowse();
    }
}
