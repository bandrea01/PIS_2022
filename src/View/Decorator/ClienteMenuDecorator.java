package View.Decorator;

import javax.swing.*;
import java.util.List;

public abstract class ClienteMenuDecorator extends Menu {
    protected Menu menu;

    public abstract List<JButton> getButtons();


}
