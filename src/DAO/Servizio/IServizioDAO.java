package DAO.Servizio;

import Model.Fornitore;
import Model.Servizio;

public interface IServizioDAO {
    Servizio findById (int id);
    Servizio findByName (String name);
    Servizio findByFornitore (Fornitore fornitore);
    int add (Servizio servizio);
    int update (Servizio servizio);
    int remove (int id);
}
