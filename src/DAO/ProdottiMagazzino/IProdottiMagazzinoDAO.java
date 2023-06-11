package DAO.ProdottiMagazzino;

import Model.Collocazione;
import Model.Magazzino;
import Model.ProdottiMagazzino;
import Model.Prodotto;

import java.util.ArrayList;

public interface IProdottiMagazzinoDAO {
    ArrayList<ProdottiMagazzino> findAllProdottiByMagazzino(Magazzino magazzino);
    Collocazione findCollocazioneProdotto(Magazzino magazzino, Prodotto prodotto);
    int findQuantitaProdotto(Magazzino magazzino, Prodotto prodotto);
    int addQuantita (Magazzino magazzino, Prodotto prodotto, int quantita);
    int reduceQuantita (Magazzino magazzino, Prodotto prodotto, int quantita);
    int add(ProdottiMagazzino prodottoMagazzino);
    int update(ProdottiMagazzino prodottoMagazzino);
    int remove(ProdottiMagazzino prodottoMagazzino);
    int removeProdotto(Prodotto prodotto);
    boolean magazzinoHasProdotto(Magazzino magazzino, Prodotto prodotto);
}
