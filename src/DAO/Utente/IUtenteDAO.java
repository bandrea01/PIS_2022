package DAO.Utente;

import Model.Amministratore;
import Model.Cliente;
import Model.Manager;
import Model.Utente;

import java.util.ArrayList;

public interface IUtenteDAO {
    Utente findById(int id);
    Utente findByUsername(String username);
    Utente findByEmail(String email);
    ArrayList<Utente> findAll();
    ArrayList<Manager> findAllManagers();
    int addUtente(Utente utente);
    int addManager(Utente utente);
    int addAdmin(Amministratore amministratore);
    int removeById(int id);
    int removeByUsername(String username);
    int removeManagerById(int id);
    int update(Utente utente);
    boolean userExist(String username);
    boolean emailExist(String email);
    boolean phoneExist(String phone);
    boolean checkCredentials(String username, String password);
    boolean isCliente(String username);
    boolean isManager(String username);
    boolean isAdmin(String username);
    Manager getManagerByUsername(String username);
    Manager getManagerById(int id);

    Amministratore getAdminByUsername(String name);

    Amministratore getAdminById(int id);
}
