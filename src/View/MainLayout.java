package View;

import Business.SessionManager;
import Business.UtenteBusiness;
import Model.Amministratore;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import View.MenuDecorator.*;
import View.Listener.LoginListener;
import View.Panel.*;
import View.ViewModel.ButtonCreator;
import View.ViewModel.MyFont;

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
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/unisalento_logo.jpeg"))).getImage());
        this.setSize(500, 500);
        this.setFont(new Font("Sans",Font.BOLD,15));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Istanzio attributi iniziali
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        up = new JPanel();
        centre = new JPanel();
        right = new JPanel();
        left = new JPanel();
        down = new JPanel();

        //Elementi barra superiore
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        showInitialPanel();
        this.setVisible(true);
    }

    public void showInitialPanel() {
        up.removeAll();
        centre.removeAll();
        down.removeAll();
        left.removeAll();
        right.removeAll();

        //Settaggio layout diverse zone
        up.setLayout(new FlowLayout());
        left.setLayout(new GridLayout(10,1));
        right.setLayout(new FlowLayout());
        down.setLayout(new FlowLayout());

        //Zona username
        JTextField usernameField = new JTextField(10);
        JLabel usernameLabel = new JLabel("Username:");

        //Zona password
        JPasswordField passwordField = new JPasswordField(10);
        JLabel passwordLabel = new JLabel("Password:");

        up.add(usernameLabel);
        up.add(usernameField);
        up.add(passwordLabel);
        up.add(passwordField);

        LoginListener loginListener = new LoginListener(usernameField, passwordField);
        loginListener.setFrame(this);
        up.add(ButtonCreator.createButton("Login", true, ButtonCreator.LIGHT_BLUE, loginListener, LoginListener.LOGIN_BTN));

        //Zona centrale
        JLabel welcome1 = new JLabel("Welcome! Please login to buy in this store");
        welcome1.setFont(new MyFont(14));
        JLabel welcome2 = new JLabel("If you don't have an account please sign in");
        welcome2.setFont(new MyFont(14));
        welcome1.setHorizontalAlignment(SwingConstants.CENTER);
        welcome2.setHorizontalAlignment(SwingConstants.CENTER);
        centre.add(welcome1);
        centre.add(welcome2);
        centre.add(ButtonCreator.createButton("Sign in", true, ButtonCreator.LILLE, e -> this.showSignInPanel(), null));
        centre.add(ButtonCreator.createButton("Forgot credentials", true, ButtonCreator.LILLE, e -> UtenteBusiness.regainCredentials(), null));

        //Zona in basso
        down.add(new JLabel("PIS Project - 2022"));
        down.setBackground(Color.GRAY);

        //Setto pannelli
        container.add(up, BorderLayout.NORTH);
        container.add(centre, BorderLayout.CENTER);
        container.add(down, BorderLayout.SOUTH);
        container.add(left, BorderLayout.WEST);
        container.add(right, BorderLayout.EAST);

        //Page Refreshing
        updateButtonsMenu();
        repaint();
        validate();
    }

    public void showUserLoggedPanel(String message){
        //togliere pannello utente non loggato
        up.removeAll();
        centre.removeAll();
        up.setLayout(new BorderLayout());
        //inserire pannello utente loggato
        JButton logout = ButtonCreator.createButton("Logout", true, ButtonCreator.SALMON, e -> showInitialPanel(), null);
        LoginListener logoutListener = new LoginListener(this);
        JLabel welcome = new JLabel(message);
        welcome.setFont(new MyFont(14));

        logout.addActionListener(logoutListener);
        logout.setActionCommand(LoginListener.LOGOUT_BTN);

        up.add(welcome, BorderLayout.WEST);
        up.add(logout, BorderLayout.EAST);

        centre.add(new PuntoVenditaPanel(this));

        //Page Refreshing
        repaint();
        validate();
    }
    public void showCatalog() {
        centre.removeAll();
        this.setSize(new Dimension(700, 500));
        repaint(); validate();
        this.setLocationRelativeTo(null);
        centre.add(new CatalogPanel());
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
        left.removeAll();

        //Sessione manager
        if (SessionManager.getSession().get(SessionManager.LOGGED_USER) instanceof Manager) {
            // TODO Decoriamo con ManagerMenuDecorator
            //Catena di decorator
        }
        //Sessione admin
        else if (SessionManager.getSession().get(SessionManager.LOGGED_USER) instanceof Amministratore) {
            View.MenuDecorator.Menu guestMenu = new GuestMenu(this);
            AdminMenuDecorator adminMenu = new AdminMenu(guestMenu, this);
            for (JButton b : adminMenu.getButtons()) {
                left.add(b);
            }
        }
        //Sessione cliente
        else if (SessionManager.getSession().get(SessionManager.LOGGED_USER) instanceof Utente) {
            View.MenuDecorator.Menu guestMenu = new GuestMenu(this);
            ClienteMenuDecorator clienteMenu = new ClienteMenu(guestMenu, this);
            for (JButton b : clienteMenu.getButtons()) {
                left.add(b);
            }
        }
        //Sessione guest
        else {
            View.MenuDecorator.Menu menu = new GuestMenu(this);
            for (JButton b : menu.getButtons()) {
                left.add(b);
            }
        }
        //Page refreshing
        repaint();
        validate();
    }
    public void showOrders() {
    }
    public void showProfile() {
    }
    public void managePoints() {
        centre.removeAll();
        centre.add(new ManagePointsPanel(this));
        repaint();
        validate();
    }
    public void showCreatePointPanel(){
        centre.removeAll();
        centre.add(new CreatePointPanel(this));
        repaint();
        validate();
    }
    public void modifyPointPanel(PuntoVendita puntoVendita){
        centre.removeAll();
        centre.add(new ModifyPointPanel(this, puntoVendita));
        repaint();
        validate();
    }

    public void manageCustoms() {
        //TODO
    }

    public void manageStore() {
        //TODO
    }



    public void manageCatalog() {
        //TODO
    }
    public void resizeFrame(){
        this.setSize(new Dimension(500,500));
        this.setLocationRelativeTo(null);
    }

    public void manageArticles() {
        centre.removeAll();
        centre.add(new ManageArticlesPanel(this));
        repaint();
        validate();
    }

    public void addArticle() {
        centre.removeAll();
        centre.add(new AddArticlePanel(this));
        repaint();
        validate();
    }

    public void addCategory() {
        centre.removeAll();
        centre.add(new AddCategoryPanel(this));
        repaint();
        validate();
    }

    public void deleteArticle() {
        centre.removeAll();
        centre.add(new DeleteArticlePanel(this));
        repaint();
        validate();
    }

    public void deleteCategory() {
        centre.removeAll();
        centre.add(new DeleteCategoryPanel(this));
        repaint();
        validate();
    }

    public void modifyArticle() {
        centre.removeAll();
        centre.add(new ModifyArticlePanel(this));
        repaint();
        validate();
    }

    public void modifyCategory() {
        centre.removeAll();
        centre.add(new ModifyCategoryPanel(this));
        repaint();
        validate();
    }

    public void addProductor() {
        centre.removeAll();
        centre.add(new AddProductorPanel(this));
        repaint();
        validate();
    }

    public void addSupplier() {
        centre.removeAll();
        centre.add(new AddSupplierPanel(this));
        repaint();
        validate();
    }

    public void deleteProductor() {
        centre.removeAll();
        centre.add(new DeleteProductorPanel(this));
        repaint();
        validate();
    }

    public void deleteSupplier() {
        centre.removeAll();
        centre.add(new DeleteSupplierPanel(this));
        repaint();
        validate();
    }
}
