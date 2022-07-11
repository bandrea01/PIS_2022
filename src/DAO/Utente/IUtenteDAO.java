package DAO.Utente;

import Model.Cliente;
import Model.Utente;

import java.util.ArrayList;

public interface IUtenteDAO {
    Utente findByName(String username);
    ArrayList<Utente> findAll();
    int add(Utente utente);
    int removeByName(String username);
    int update(Utente utente);
    boolean userExist(String username);
    boolean checkCredentials(String username, String password);
    boolean isCliente(String username);
    boolean isManager(String username);
    boolean isAdmin(String username);
    Cliente getCliente(String username);
}
