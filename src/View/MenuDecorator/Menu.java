package View.MenuDecorator;

import View.MainLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    protected MainLayout window;
    protected List<JButton> buttons = new ArrayList<>();

    public abstract List<JButton> getButtons();
}
