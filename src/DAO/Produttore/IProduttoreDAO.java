package DAO.Produttore;

import Model.Produttore;

import java.util.ArrayList;

public interface IProduttoreDAO {
    Produttore findById (int id);
    Produttore findByName (String name);
    int add (Produttore produttore);
    int update (Produttore produttore);
    int remove (Produttore produttore);

    ArrayList<Produttore> findAll();
}
