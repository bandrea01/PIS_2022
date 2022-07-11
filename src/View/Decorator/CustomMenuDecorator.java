package View.Decorator;

import javax.swing.*;
import java.util.List;

public abstract class CustomMenuDecorator extends MyMenu {
    protected MyMenu menu;

    @Override
    public abstract List<JButton> getButtons();
}
