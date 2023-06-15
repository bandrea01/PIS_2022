package DAO.Ordine;

import Model.*;

import java.util.ArrayList;

public interface IOrdineDAO {
    Ordine findOrdineById (int id);
    ArrayList<Ordine> findAllOfUtente (Utente utente);
    ArrayList<ProdottoOrdine> findProdottiByOrdineId(int id);
    ArrayList<Servizio> findServiziByOrdineId(int id);
    boolean isPagato(Ordine ordine);
    int paga(Ordine ordine);
    int add(Ordine ordine);
    int addProdottiOrdine(int idOrdine, ArrayList<ProdottoOrdine> prodottiOrdine);
    int addServiziOrdine(int idOrdine, ArrayList<Servizio> servizi);
    int removeProdottiOrdine(int idOrdine);
    int removeServiziOrdine(int idOrdine);
    int remove(Ordine ordine);

    int removeById(int id);

    ArrayList<Ordine> findAll();
}
