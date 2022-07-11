package View.Decorator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MyMenu {
    protected List <JButton> buttons = new ArrayList<>();

    public List<JButton> getButtons() {
        return buttons;
    }
}
