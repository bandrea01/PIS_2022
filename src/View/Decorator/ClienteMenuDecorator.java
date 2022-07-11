package View.Decorator;

import javax.swing.*;
import java.util.List;

public class ClienteMenuDecorator extends CustomMenuDecorator{

    public ClienteMenuDecorator(MyMenu menu) {
        this.menu = menu;
    }

    @Override
    public List<JButton> getButtons() {
        // ho tutte le funzioni del guest
        buttons.addAll(this.menu.getButtons());
        // + funzioni cliente
        JButton lists = new JButton("My Lists");
        JButton profile = new JButton("My Profile");
        // TODO action command, listener ...
        buttons.add(lists);
        buttons.add(profile);

        return buttons;
    }
}
