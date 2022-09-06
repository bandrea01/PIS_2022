package Business;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtenteBusiness {
    private static UtenteBusiness instance;

    /**
     * SINGLETON
     * Synchronized per evitare che più classi accedano alla stessa istanza
     * @return instance
     */
    public static synchronized UtenteBusiness getInstance() {
        if (instance == null) {
            instance = new UtenteBusiness();
        }
        return instance;
    }

    public LoginResult login(String username, String password) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        LoginResult result = new LoginResult();

        //TODO
        //1. Controllare se esiste l'utente
        boolean userExist = utenteDAO.userExist(username);
        if (!userExist) {
            result.setResult(LoginResult.Result.WRONG_USERNAME);
            result.setMessage("Wrong username!");
            return result;
        }
        //2. Controllare se username e password coincidono
        boolean credentialsOk = utenteDAO.checkCredentials(username, password);
        if(!credentialsOk) {
            result.setResult(LoginResult.Result.WRONG_PASSWORD);
            result.setMessage("Wrong password!");
            return result;
        }
        //3. Controllare tipo Utente
        boolean isCliente = utenteDAO.isCliente(username);
        boolean isManager = utenteDAO.isManager(username);
        boolean isAdmin = utenteDAO.isAdmin(username);

        //4. Caricare oggetto Utente (a seconda della tipologia)
        if (isCliente) {
            Utente utente = utenteDAO.findByUsername(username);
            //Usiamo sessione manager, inseriamo informazione LOGGATO-UTENTE
            SessionManager.getSession().put(SessionManager.LOGGED_USER, utente);
            result.setMessage("Welcome " + utente.getName() + "!");
        } else if (isManager) {
            Manager manager = utenteDAO.getManagerByUsername(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, manager);
            result.setMessage("Welcome " + manager.getName() + "!");
            return null;
        } else if (isAdmin) {
            Amministratore admin = utenteDAO.getAdminByUsername(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, admin);
            result.setMessage("Welcome " + admin.getName() + "!");
            return null;
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
        if (password.length() != 8){
            JOptionPane.showMessageDialog(null, "Password length must be of 8 alphanumeric characters");
            return false;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
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
        Pattern pattern2 = Pattern.compile("[^0-9]");
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
        utente.setPassword(password);
        utente.setPhone(telefono);
        utente.setAge(eta);
        utente.setCity(residenza);
        utente.setJob(professione);

        return utenteDAO.addUtente(utente) == 1;
    }






}
