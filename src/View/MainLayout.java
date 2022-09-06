package View;

import Business.SessionManager;
import Model.Amministratore;
import Model.Cliente;
import Model.Manager;
import Model.Utente;
import View.Decorator.GuestMenu;
import View.Decorator.GuestMenuDecorator;
import View.Listener.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainLayout extends JFrame {
    private JPanel panel = new JPanel();
    private Container container;
    private JPanel up;
    private JPanel centre;
    private JPanel left;
    private JPanel right;

    private JPanel down;
    private JMenuBar menuBar;


    public MainLayout(){
        //Settaggio finestra principale applicazione
        super ("MyShop");
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("unisalento_logo.jpeg"))).getImage());
        this.setSize(500, 500);
        this.setFont(new Font("Sans",Font.BOLD,15));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Istanzio attributi iniziali
        container = this.getContentPane();
        up = new JPanel();
        centre = new JPanel();
        right = new JPanel();
        left = new JPanel();
        down = new JPanel();

        //Settaggio layout diverse zone
        container.setLayout(new BorderLayout());
        up.setLayout(new FlowLayout());
        centre.setLayout(new FlowLayout());
        down.setLayout(new FlowLayout());

        //Elementi barra superiore
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        panel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        showInitialPanel();
        this.setVisible(true);
    }

    public void showInitialPanel() {
        centre.removeAll();
        up.removeAll();
        left.removeAll();
        right.removeAll();
        down.removeAll();
        panel.removeAll();

        //Zona username
        JTextField usernameField = new JTextField(10);
        JLabel usernameLabel = new JLabel("Username:");

        //Zona password
        JPasswordField passwordField = new JPasswordField(10);
        JLabel passwordLabel = new JLabel("Password:");

        //Bottone login
        JButton login = new JButton("Login"); //Bottone login
        login.setActionCommand(LoginListener.LOGIN_BTN);

        up.add(usernameLabel);
        up.add(usernameField);
        up.add(passwordLabel);
        up.add(passwordField);
        up.add(login);

        //Login Listener
        LoginListener loginListener = new LoginListener(usernameField, passwordField);
        loginListener.setFrame(this);
        login.addActionListener(loginListener);

        //Zona centrale
        centre.add(new JLabel("Welcome! Please login to buy in this store"));
        GuestMenu guestMenu = new GuestMenu(this);
        centre.add(guestMenu.getBrowse());
        centre.add(new JLabel("If you don't have an account "));
        centre.add(guestMenu.getSignIn());

        //Zona in basso
        down.add(new JLabel("PIS Project - 2022"));
        down.setBackground(Color.GRAY);

        //Setto pannelli
        container.add(up, BorderLayout.NORTH);
        container.add(centre, BorderLayout.CENTER);
        container.add(down, BorderLayout.SOUTH);
        container.add(left, BorderLayout.WEST);
        container.add(right, BorderLayout.EAST);
        panel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));


        //Page Refreshing
        //updateButtonsMenu();
        repaint();
        validate();
    }

    public void showUserLoggedPanel(String message){
        //togliere pannello utente non loggato
        remove(up);
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
    public void showSignInPanel(){
        centre.removeAll();
        centre.add(new SignInPanel(this));
        repaint();
        validate();
    }
    public void updateButtonsMenu() {
        /*left.removeAll();

        //...
        Utente u = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        if (u instanceof Cliente) {
            // Decoriamo con ClienteMenuDecorator
            GuestMenuDecorator guestMenu = new GuestMenu(this);
            GuestMenuDecorator clienteMenu = new ClienteMenuDecorator(guestMenu);
            for (JButton b : clienteMenu.getButtons()) {
                left.add(b);
            }

        } else if (u instanceof Manager) {
            // TODO Decoriamo con ManagerMenuDecorator
            //Catena di decorator
        } else if (u instanceof Amministratore) {
            // TODO Decoriamo con AdminMenuDecorator
        } else {
            GuestMenuDecorator menu = new GuestMenu(this);
            for (JButton b : menu.getButtons()) {
                left.add(b);
            }
        }*/
        //Page refreshing
        repaint();
        validate();
    }
}
