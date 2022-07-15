package Business;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.*;

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







}
