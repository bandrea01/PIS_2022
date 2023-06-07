package Business;

import DAO.Produttore.ProduttoreDAO;
import Model.Produttore;

import java.util.ArrayList;

public class ProduttoreBusiness {
    private static ProduttoreBusiness instance;

    public static synchronized ProduttoreBusiness getInstance() {
        if (instance == null) {
            instance = new ProduttoreBusiness();
        }
        return instance;
    }

    public int addProduttore(String nome, String sito, String citta, String nazione) {

        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        Produttore produttore = new Produttore();

        //Controllo se i campi sono vuoti
        if (nome.isEmpty() || sito.isEmpty() || citta.isEmpty() || nazione.isEmpty()) {
            return 0;
        }

        //Controlli sul DAO (se il produttore già esiste)
        if (produttoreDAO.produttoreExist(produttoreDAO.findByName(nome).getId())) {
            return 1;
        }

        ArrayList<Produttore> produttori = produttoreDAO.findAll();
        int idProduttore = produttori.size() + 1;

        produttore.setId(idProduttore);
        produttore.setNome(nome);
        produttore.setSito(sito);
        produttore.setCitta(citta);
        produttore.setNazione(nazione);

        produttoreDAO.add(produttore);
        return 2;
    }
}
