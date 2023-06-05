package DAO.Fornitore;

import Model.Fornitore;

import java.util.ArrayList;

public interface IFornitoreDAO {
    Fornitore findById (int id);
    Fornitore findByName (String name);
    int add (Fornitore fornitore);
    int update (Fornitore fornitore);
    int remove (Fornitore fornitore);

    ArrayList<Fornitore> findAll();
}
