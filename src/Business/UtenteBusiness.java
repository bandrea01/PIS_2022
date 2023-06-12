package Business;

import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtenteBusiness {
    private static UtenteBusiness instance;

    public static synchronized UtenteBusiness getInstance() {
        if (instance == null) {
            instance = new UtenteBusiness();
        }
        return instance;
    }

    public static void regainCredentials() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        String email = JOptionPane.showInputDialog(null, "Insert your email, you will recive a token:");
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[5];
        secureRandom.nextBytes(tokenBytes);

        if (!utenteDAO.emailExist(email)){
            JOptionPane.showMessageDialog(null,"Unknown email! Please insert a valid email");
        }
        else {
            MailHelper mailHelper = MailHelper.getInstance();
            EmailSessionManager sessione = new EmailSessionManager(5); // Creazione di una sessione di 5 minuti
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

            while (!sessione.isScaduta()) {
                System.out.println("Sessione attiva...");
                mailHelper.send(email, "Richiesta credenziali", "" +
                        "Ecco il codice per accedere alla procedura per il recupero delle credenziali:\n" + token, null);
                String input = JOptionPane.showInputDialog("Insert the token you recived in your email");
                String newPassword;
                if (input.equals(token)){
                    while(true) {
                        newPassword = JOptionPane.showInputDialog("Insert a password (at least 8 alphanumeric characters)");

                        //Controllo password
                        if (newPassword.length() < 8) {
                            JOptionPane.showMessageDialog(null, "Password length must be at least 8 alphanumeric characters");
                        } else {
                            Pattern pattern = Pattern.compile("[^a-zA-Z\\d]");
                            Matcher matcher = pattern.matcher(newPassword);
                            boolean specialFound = matcher.find();
                            if (specialFound) {
                                JOptionPane.showMessageDialog(null, "Password length must be of 8 alphanumeric characters");
                            } else{
                                break;
                            }
                        }
                    }
                    //Inserimento password nel database
                    UtenteDAO utenteDAO1 = UtenteDAO.getInstance();
                    Utente utente = utenteDAO1.findByEmail(email);
                    utente.setPassword(HashingBusiness.encrypt(newPassword));
                    utenteDAO1.update(utente);
                    JOptionPane.showMessageDialog(null, "Password changed succesfully!");
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid Token!");
                }
                try {
                    Thread.sleep(1000); // Aspetta 1 secondo prima di ripetere il ciclo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Sessione scaduta.");
        }
    }

    public LoginResult login(String username, String password) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        LoginResult result = new LoginResult();

        //1. Controllare se esiste l'utente
        boolean userExist = utenteDAO.userExist(username);
        if (!userExist) {
            result.setResult(LoginResult.Result.WRONG_USERNAME);
            result.setMessage("Wrong username!");
            return result;
        }
        //2. Controllare se username e password coincidono
        String hashed = utenteDAO.findByUsername(username).getPassword();
        HashingBusiness hashingBusiness = new HashingBusiness();
        boolean credentialsOk = hashingBusiness.checkPassword(password, hashed);
        if(!credentialsOk) {
            result.setResult(LoginResult.Result.WRONG_PASSWORD);
            result.setMessage("Wrong password!");
            return result;
        }
        //3. Controllare tipo Utente
        boolean isManager = utenteDAO.isManager(username);
        boolean isAdmin = utenteDAO.isAdmin(username);

        //4. Caricare oggetto Utente (a seconda della tipologia)
        if (isManager) {
            Manager manager = utenteDAO.getManagerByUsername(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, manager);
            result.setMessage("Welcome " + manager.getName() + " manager!");
        } else if (isAdmin) {
            Amministratore admin = utenteDAO.getAdminByUsername(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, admin);
            result.setMessage("Welcome " + admin.getName() + "!");
        } else {
            Utente utente = utenteDAO.findByUsername(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, utente);
            result.setMessage("Welcome " + utente.getName() + "!");
        }

        result.setResult(LoginResult.Result.LOGIN_OK);

        return result;

    }
    public boolean signIn(String nome, String cognome, String username, String email, String password, String telefono, int eta, String residenza, String professione){
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = new Utente();

        //Controllo campi vuoti
        if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                telefono.isEmpty() || eta == 0 || residenza.isEmpty() || professione.isEmpty()){
            JOptionPane.showMessageDialog(null, "All field are required");
            return false;
        }
        //Controllo password
        if (password.length() < 8){
            JOptionPane.showMessageDialog(null, "Password length must be at least 8 alphanumeric characters");
            return false;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z\\d]");
        Matcher matcher = pattern.matcher(password);
        boolean specialFound = matcher.find();
        if (specialFound){
            JOptionPane.showMessageDialog(null, "Password length must be of 8 alphanumeric characters");
            return false;
        }
        //Controllo username
        if (username.length() > 10){
            JOptionPane.showMessageDialog(null, "Username length must be of 5-10 alphanumeric characters");
            return false;
        }
        //Controllo Telefono
        Pattern pattern2 = Pattern.compile("\\D");
        Matcher matcher2 = pattern2.matcher(telefono);
        boolean notDigit = matcher2.find();
        if(notDigit){
            JOptionPane.showMessageDialog(null, "Insert a valid phone number");
            return false;
        }
        //Controllo eta
        if (eta > 120 || eta < 18){
            JOptionPane.showMessageDialog(null, "Age insert not valid");
            return false;
        }

        //Controlli sul DAO
        if (utenteDAO.userExist(username)){
            JOptionPane.showMessageDialog(null, "Username alredy use");
            return false;
        }
        if (utenteDAO.phoneExist(telefono)){
            JOptionPane.showMessageDialog(null, "Phone number alredy use");
            return false;
        }
        if (utenteDAO.emailExist(email)){
            JOptionPane.showMessageDialog(null, "Email alredy use");
            return false;
        }

        //Inserimento dati database
        utente.setName(nome);
        utente.setSurname(cognome);
        utente.setUsername(username);
        utente.setEmail(email);
        utente.setPassword(HashingBusiness.encrypt(password));
        utente.setPhone(telefono);
        utente.setAge(eta);
        utente.setCity(residenza);
        utente.setJob(professione);

        return utenteDAO.addUtente(utente) == 1;
    }
    public void deleteManager(String username){
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = utenteDAO.findByUsername(username);
        PuntoVendita puntoVendita = puntoVenditaDAO.findByIdManager(utente.getId());
        if (!"".equalsIgnoreCase(puntoVendita.getName())){
            JOptionPane.showMessageDialog(null,"Can't delete manager: change manager in sale point " + puntoVendita.getName() + " first");
            return;
        }
        if (1 != utenteDAO.removeManagerById(utente.getId())){
            JOptionPane.showMessageDialog(null, "System error! Can't delete manager");
            return;
        }
        JOptionPane.showMessageDialog(null, "Manager successfully deleted!");
    }
    public void createManager() {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        String input = JOptionPane.showInputDialog(null, "Insert user's email to be made manager");
        if(!utenteDAO.emailExist(input)){
            JOptionPane.showMessageDialog(null, "Email not valid");
            return;
        }
        if (utenteDAO.isManager(utenteDAO.findByEmail(input).getUsername())){
            JOptionPane.showMessageDialog(null, "The user is alredy a manager");
            return;
        }
        JOptionPane.showMessageDialog(null, "Manager successfully created!");
        Utente utente = utenteDAO.findByEmail(input);
        utenteDAO.addManager(utente);
    }

    public int removeClient(String nomeCliente, String punto) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName(punto);
        //utente esiste?
        if (!utenteDAO.userExist(nomeCliente)) {
            return 1;
        }
        //utente è cliente?
        if (!utenteDAO.isCliente(nomeCliente)) {
            return 2;
        }

        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        Utente utente = utenteDAO.findByUsername(nomeCliente);

        clientePuntoVenditaDAO.removeClienteFromPuntoVendita(utente, puntoVendita);
        return 0;
    }

    public int unbanClient(String nomeCliente, String punto) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        //utente esiste?
        if (!utenteDAO.userExist(nomeCliente)) {
            return 1;
        }
        //utente è cliente?
        if (!utenteDAO.isCliente(nomeCliente)) {
            return 2;
        }

        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        Utente utente = utenteDAO.findByUsername(nomeCliente);
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName(punto);

        //cliente è già bannato?
        if (!clientePuntoVenditaDAO.isClienteBanned(utente, puntoVendita)) {
            return 3;
        }

        clientePuntoVenditaDAO.unbanCliente(utente, puntoVendita);
        return 0;
    }

    public int banClient(String nomeCliente, String punto) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        //utente esiste?
        if (!utenteDAO.userExist(nomeCliente)) {
            return 1;
        }
        //utente è cliente?
        if (!utenteDAO.isCliente(nomeCliente)) {
            return 2;
        }

        ClientePuntoVenditaDAO clientePuntoVenditaDAO = ClientePuntoVenditaDAO.getInstance();
        Utente utente = utenteDAO.findByUsername(nomeCliente);
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName(punto);

        //cliente è già bannato?
        if (clientePuntoVenditaDAO.isClienteBanned(utente, puntoVendita)) {
            return 3;
        }

        clientePuntoVenditaDAO.banCliente(utente, puntoVendita);
        return 0;
    }
}
