package Business;

import DAO.Fornitore.FornitoreDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.Fornitore;
import Model.Produttore;

import java.util.ArrayList;

public class FornitoreBusiness {
    private static FornitoreBusiness instance;

    public static synchronized FornitoreBusiness getInstance() {
        if (instance == null) {
            instance = new FornitoreBusiness();
        }
        return instance;
    }

    public int addFornitore(String nome, String sito, String citta, String nazione) {

        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        Fornitore fornitore = new Fornitore();

        //Controllo se i campi sono vuoti
        if (nome.isEmpty() || sito.isEmpty() || citta.isEmpty() || nazione.isEmpty()) {
            return 0;
        }

        //Controlli sul DAO (se il produttore già esiste)
        if (fornitoreDAO.fornitoreExist(fornitoreDAO.findByName(nome).getId())) {
            return 1;
        }

        ArrayList<Fornitore> fornitori = fornitoreDAO.findAll();
        int idFornitore = fornitori.size() + 1;

        fornitore.setId(idFornitore);
        fornitore.setNome(nome);
        fornitore.setSito(sito);
        fornitore.setCitta(citta);
        fornitore.setNazione(nazione);

        fornitoreDAO.add(fornitore);
        return 2;
    }

}
