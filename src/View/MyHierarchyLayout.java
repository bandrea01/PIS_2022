package View;

import Business.SessionManager;
import Model.Amministratore;
import Model.Cliente;
import Model.Manager;
import Model.Utente;
import View.Decorator.ClienteMenuDecorator;
import View.Decorator.GuestMenu;
import View.Decorator.MyMenu;
import View.Listener.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyHierarchyLayout extends JFrame {
    private JPanel panel = new JPanel();
    private Container c;
    private JPanel nord;
    private JPanel centre;
    private JPanel west;
    private JPanel sud;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuApri;


    public MyHierarchyLayout(){
        super ("MyShop");
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("unisalento_logo.jpeg"))).getImage());
        this.setSize(500, 500);
        this.setFont(new Font("Sans",Font.BOLD,15));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Istanzio attributi iniziali
        c = this.getContentPane();
        nord = new JPanel();
        centre = new JPanel();
        west = new JPanel();
        sud = new JPanel();

        //Layout
        c.setLayout(new BorderLayout());
        nord.setLayout(new FlowLayout());
        centre.setLayout(new GridLayout(2,1));
        west.setLayout(new GridLayout(10, 1));
        sud.setLayout(new FlowLayout());

        //Elementi barra nord
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuFile = new JMenu("File");
        menuApri = new JMenu("Apri");
        menuBar.add(menuFile);
        menuFile.add(menuApri);

        //Elementi barra ovest
        MyMenu guestMenu = new GuestMenu(this);
        for(JButton b : guestMenu.getButtons()) {
            west.add(b);
        }

        c.add(west, BorderLayout.WEST);



        showInitialPanel();
        this.setVisible(true);
    }

    public void showInitialPanel() {
        centre.removeAll();
        nord.removeAll();
        panel.removeAll();

        //Username, password e login button
        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JButton login = new JButton("Login");
        login.setActionCommand(LoginListener.LOGIN_BTN);
        nord.add(username);
        nord.add(password);
        nord.add(login);
        LoginListener loginListener = new LoginListener(username, password);
//      LoginListener loginListener2 = new LoginListener(this);
        loginListener.setFrame(this);
        login.addActionListener(loginListener);

        //Messaggio saluti
        centre.add(new JLabel("Welcome! Please login to buy in this store"));
        sud.add(new JLabel("PIS Project - 2022"));
        sud.setBackground(Color.GRAY);

        //Setto pannelli
        c.add(nord, BorderLayout.NORTH);
        c.add(centre, BorderLayout.CENTER);
        c.add(sud, BorderLayout.SOUTH);

        //Istanzio voce della tendina per la voce FILE e l'aggiungo
        JMenuItem apri = new JMenuItem("Apri...");
        apri.addActionListener(loginListener);
        apri.setActionCommand(loginListener.APRIFILE_MENU);
        menuFile.add(apri);
        JMenu menuRecenti = new JMenu("Recenti");
        menuFile.add(menuRecenti);
        menuRecenti.add(new JMenuItem("/file1"));
        menuRecenti.add(new JMenuItem("/file2"));
        menuRecenti.add(new JMenuItem("/file3"));
        panel.setLayout(new FlowLayout());

        //Page Refreshing
        updateButtonsMenu();
        repaint();
        validate();
    }

    public void showUserLoggedPanel(String message){
        //togliere pannello utente non loggato
        remove(nord);
        remove(centre);
        //inserire pannello utente loggato
        JButton logout = new JButton("Logout");
        LoginListener logoutListener = new LoginListener(this);

        logout.addActionListener(logoutListener);
        logout.setActionCommand(LoginListener.LOGOUT_BTN);
        panel.removeAll();
        panel.add(new JLabel(message));
        panel.add(logout);
        add(panel, BorderLayout.NORTH);

        //Page Refreshing
        repaint();
        validate();
    }
    public void showCatalog() {
        centre.removeAll();
        centre.add(new CatalogoPanel());
        repaint();
        validate();
    }
    public void updateButtonsMenu() {
        west.removeAll();

        //...
        Utente u = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        if (u instanceof Cliente) {
            // Decoriamo con ClienteMenuDecorator
            MyMenu guestMenu = new GuestMenu(this);
            MyMenu clienteMenu = new ClienteMenuDecorator(guestMenu);
            for (JButton b : clienteMenu.getButtons()) {
                west.add(b);
            }

        } else if (u instanceof Manager) {
            // TODO Decoriamo con ManagerMenuDecorator
            //Catena di decorator
        } else if (u instanceof Amministratore) {
            // TODO Decoriamo con AdminMenuDecorator
        } else {
            MyMenu menu = new GuestMenu(this);
            for (JButton b : menu.getButtons()) {
                west.add(b);
            }
        }
        //Page refreshing
        repaint();
        validate();
    }
}
