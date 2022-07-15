package DAO.Feedback;

import Model.Articolo;
import Model.Feedback;
import Model.Utente;

import java.util.List;

public interface IFeedbackDAO {
    List<Feedback> findAllOfArticolo (Articolo articolo);
    List<Feedback> findAllOfUtente (Utente utente);
    int add (Feedback feedback);
    int update (Feedback feedback);
}
