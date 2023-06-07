package View.Panel;

import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;

public class ManageArticlesPanel extends JPanel {
    public ManageArticlesPanel(MainLayout window) {
        JPanel buttonsPanel = new JPanel();
        this.setLayout(new BorderLayout());
        buttonsPanel.setLayout(new GridLayout(2, 10));

        buttonsPanel.add(ButtonCreator.createButton("Add Article", true, ButtonCreator.LILLE, e -> window.addArticle(), null));
        buttonsPanel.add(ButtonCreator.createButton("Add Category", true, ButtonCreator.LILLE, e -> window.addCategory(), null));
        buttonsPanel.add(ButtonCreator.createButton("Delete Article", true, ButtonCreator.LILLE, e -> window.deleteArticle(), null));
        buttonsPanel.add(ButtonCreator.createButton("Delete Category", true, ButtonCreator.LILLE, e -> window.deleteCategory(), null));
        buttonsPanel.add(ButtonCreator.createButton("Modify Article", true, ButtonCreator.LILLE, e -> window.modifyArticle(), null));
        buttonsPanel.add(ButtonCreator.createButton("Modify Category", true, ButtonCreator.LILLE, e -> window.modifyCategory(), null));

        this.add(buttonsPanel, BorderLayout.CENTER);

    }
}
