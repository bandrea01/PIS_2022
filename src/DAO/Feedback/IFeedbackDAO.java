package DAO.Feedback;

import Model.Articolo;
import Model.Feedback;
import Model.Utente;

import java.util.ArrayList;
import java.util.List;

public interface IFeedbackDAO {
    ArrayList<Feedback> findAllOfArticolo (Articolo articolo);
    ArrayList<Feedback> findAllOfUtente (Utente utente);
    int add (Feedback feedback);
    int update (Feedback feedback);
    int remove (Feedback feedback);

    ArrayList<Feedback> findAll();
}
