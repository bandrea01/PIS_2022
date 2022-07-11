package Business;

import DAO.Utente.UtenteDAO;
import Model.Cliente;

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
            Cliente c = utenteDAO.getCliente(username);

            //Usiamo sessione manager, inseriamo informazione LOGGATO-CLIENTE
            SessionManager.getSession().put(SessionManager.LOGGED_USER, c);
            result.setMessage("Welcome " + c.getName() + "!");

        } else if (isManager) {
            //TODO
            return null;
        } else if (isAdmin) {
            //TODO
            return null;
        }

        result.setResult(LoginResult.Result.LOGIN_OK);

        return result;

    }







}
