package View.MenuDecorator;

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
        JButton magazzino = ButtonCreator.createButton("Supply products", true, ButtonCreator.LIGHT_BLUE, e -> window.manageStore(), null);
        JButton clienti = ButtonCreator.createButton("My Customers", true, ButtonCreator.LIGHT_BLUE, e -> window.manageClienti(), null);
        JButton feedback = ButtonCreator.createButton("Feedbacks", true, ButtonCreator.LIGHT_BLUE, e -> window.mostraFeedback(), null);

        buttons.add(magazzino);
        buttons.add(clienti);
        buttons.add(feedback);

        return buttons;
    }
}

