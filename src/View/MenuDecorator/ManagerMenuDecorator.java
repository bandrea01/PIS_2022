package View.MenuDecorator;

import javax.swing.*;
import java.util.List;

public abstract class ManagerMenuDecorator extends Menu {
    protected Menu menu;

    public abstract List<JButton> getButtons();


}
