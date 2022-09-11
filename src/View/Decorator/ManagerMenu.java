package View.Decorator;

import Model.Manager;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.util.List;


public class ManagerMenu extends ManagerMenuDecorator {

    public ManagerMenu(Menu menu, MainLayout window) {
        this.menu = menu;
        this.window = window;
    }

    @Override
    public List<JButton> getButtons() {
        //Funzioni guest+cliente
        buttons.addAll(this.menu.getButtons());
        //Funzioni manager
        JButton clienti = ButtonCreator.createButton("My Sale Points", true, 150,125,215, e -> window.manageCustoms(), null);
        JButton magazzino = ButtonCreator.createButton("My Profile", true, 150,125,215, e -> window.manageStore(), null);

        buttons.add(clienti);
        buttons.add(magazzino);

        return buttons;
    }
}

