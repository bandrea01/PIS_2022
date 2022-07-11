package View.Decorator;

import View.MyHierarchyLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestMenu extends MyMenu {

    private MyHierarchyLayout window;
    public GuestMenu(MyHierarchyLayout window) {
        this.window = window;
        JButton browse = new JButton("Browse Catalog");
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.showCatalog();
            }
        });

        buttons.add(browse);
    }
}
