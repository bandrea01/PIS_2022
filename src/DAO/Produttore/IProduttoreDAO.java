package DAO.Produttore;

import Model.Produttore;

public interface IProduttoreDAO {
    Produttore findById (int id);
    Produttore findByName (String name);
    int add (Produttore produttore);
    int update (Produttore produttore);
    int remove (int id);
}
