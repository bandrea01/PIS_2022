package View.MenuDecorator;

import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.util.List;

/**
 * Menu con elementi relativi al primo ingresso per il Guest
 * Passo al costruttore tutta la finestra
 */

public class GuestMenu extends Menu {
    public GuestMenu(MainLayout window){
        this.window = window;
    }

    @Override
    public List<JButton> getButtons() {
        //Browse
        JButton browse = ButtonCreator.createButton("Browse Catalog",true,100,149,237, e -> window.showCatalog(), null);
        buttons.add(browse);
        return buttons;
    }
}
