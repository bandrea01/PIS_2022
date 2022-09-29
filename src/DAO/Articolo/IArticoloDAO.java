package DAO.Articolo;

import Model.Articolo;
import Model.Prodotto;
import Model.Servizio;

import java.awt.*;
import java.util.ArrayList;

public interface IArticoloDAO {
    Articolo findById (int id);
    Articolo findByName (String name);
    ArrayList<Articolo> findAll();
    boolean isProdotto(int id);
    boolean isServizio(int id);
    int addProdotto (Prodotto prodotto);
    int addServizio (Servizio servizio);
    int updateProdotto (Prodotto prodotto);
    int updateServizio (Servizio servizio);
    int removeById (int id);
    int removeByName (String name);
}
