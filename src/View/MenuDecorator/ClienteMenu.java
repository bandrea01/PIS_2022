package View.MenuDecorator;

import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.util.List;


public class ClienteMenu extends ClienteMenuDecorator {

    public ClienteMenu(Menu menu, MainLayout window) {
        this.menu = menu;
        this.window = window;
    }

    @Override
    public List<JButton> getButtons() {
        //Funzioni guest
        buttons.addAll(this.menu.getButtons());
        //Funzioni cliente
        JButton order = ButtonCreator.createButton("My Orders", true, 100,149,237, e -> window.manageOrders(), null);
        JButton profile = ButtonCreator.createButton("Sign in a sale point", true, 100,149,237, e -> window.registraCliente(), null);
        JButton acquista = ButtonCreator.createButton("Buy", true, 100,149,237, e -> window.acquista(), null);

        buttons.add(order);
        buttons.add(profile);
        buttons.add(acquista);

        return buttons;
    }
}

