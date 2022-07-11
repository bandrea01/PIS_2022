package Business;

import java.util.HashMap;

/**
 * Classe per conservare la sessione login del cliente attuale
 * Mi tiene traccia del nome, ordine fatto ...
 */

public class SessionManager {
    //Informazioni di cui tenere traccia
    public static final String LOGGED_USER = "logged_user";
    //...

    //HashMap
    private static HashMap<String, Object> session = new HashMap<>();

    public static HashMap getSession() {
        return session;
    }

    public static void refresh() {
        session.remove(SessionManager.LOGGED_USER);
    }
}
