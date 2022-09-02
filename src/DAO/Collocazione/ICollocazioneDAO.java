package DAO.Collocazione;

import Model.Collocazione;

public interface ICollocazioneDAO {
    Collocazione findCollocazioneById(int id);
    int add (Collocazione collocazione);
    int remove (Collocazione collocazione);
}
