package DAO.Fornitore;

import Model.Fornitore;

public interface IFornitoreDAO {
    Fornitore findById (int id);
    Fornitore findByName (String name);
    int add (Fornitore fornitore);
    int update (Fornitore fornitore);
    int remove (Fornitore fornitore);
}
