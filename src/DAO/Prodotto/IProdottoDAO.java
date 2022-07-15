package DAO.Prodotto;

import DAO.Prodotto.ProdottoDAO;
import Model.Fornitore;
import Model.Prodotto;
import Model.Produttore;

import java.util.ArrayList;

public interface IProdottoDAO {
    Prodotto findById (int id);
    Prodotto findByName (String name);
    ArrayList<Prodotto> findAllByProduttore (Produttore Produttore);
    int add (Prodotto prodotto);
    int update (Prodotto prodotto);
    int remove (int id);
    ArrayList<Prodotto> getAllSottoProdotti (Prodotto prodotto);
    int addSottoProdotti(Prodotto prodotto);
    int updateSottoProdotti(Prodotto prodotto);
}

