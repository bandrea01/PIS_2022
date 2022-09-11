package View.Decorator;

import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.util.List;


public class AdminMenu extends AdminMenuDecorator {

    public AdminMenu(Menu menu, MainLayout window) {
        this.menu = menu;
        this.window = window;
    }

    @Override
    public List<JButton> getButtons() {
        //Funzioni guest+cliente
        buttons.addAll(this.menu.getButtons());
        //Funzioni admin
        JButton punti = ButtonCreator.createButton("My Sale Points", true, ButtonCreator.LIGHT_BLUE, e -> window.managePoints(), null);
        JButton catalogo = ButtonCreator.createButton("My Catalog", true, ButtonCreator.LIGHT_BLUE, e -> window.manageCatalog(), null);

        buttons.add(punti);
        buttons.add(catalogo);

        return buttons;
    }
}

