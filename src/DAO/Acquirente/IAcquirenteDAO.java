package DAO.Acquirente;

import Model.Acquirente;

import java.util.ArrayList;

public interface IAcquirenteDAO {
    Acquirente findByUsername(String username);
    Acquirente findById(int id);
    ArrayList<Acquirente> findAll();
    int add(Acquirente acquirente);
    int removeByUsername(String username);
    int removeById(int id);
    int update(Acquirente acquirente);
    int getIdByUsername(String username);
}
