package DAO.Collocazione;

import Model.Collocazione;
import Model.Prodotto;

import java.util.ArrayList;

public interface ICollocazioneDAO {
    Collocazione findCollocazioneById(int id);
    int add (Collocazione collocazione);
    int remove (Collocazione collocazione);
    ArrayList<Collocazione> findAll();
    Boolean exist(int corsia, int scaffale);
    Collocazione findByPosition(int corsia, int scaffale);
}
