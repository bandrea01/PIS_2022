package Business;

import DAO.Collocazione.CollocazioneDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.*;

import java.util.ArrayList;

public class MagazzinoBusiness {
    private static MagazzinoBusiness instance;

    public static synchronized MagazzinoBusiness getInstance() {
        if (instance == null) {
            instance = new MagazzinoBusiness();
        }
        return instance;
    }

    public int addMagazzino(String nomePuntoVendita, String[] prodotti, int[] corsia, int[] scaffale, int[] quantita) {
        MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();
        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
        int idMagazzino = magazzinoDAO.findAllMagazzino().size();
        int idCollocazione = collocazioneDAO.findAll().size();
        if (nomePuntoVendita.isEmpty()) {
            return 1;
        }
        PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findByName(nomePuntoVendita);
        if (magazzinoDAO.existForPunto(puntoVendita)) {
            return 2;
        }
        Magazzino magazzino = new Magazzino();
        magazzino.setId(idMagazzino + 1);
        magazzino.setPuntoVendita(puntoVendita);
        magazzinoDAO.add(magazzino);
        //prodotti corsia e scaffale sono già selezionati nel listener con l'indice selezionato passato dal panel
        for (int i = 0; i < prodotti.length; i++) {

            Collocazione collocazione = new Collocazione();
            ProdottiMagazzino prodottiMagazzino = new ProdottiMagazzino();
            Prodotto prodotto = prodottoDAO.findByName(prodotti[i]);

            if (!collocazioneDAO.exist(corsia[i], scaffale[i])) {
                collocazione.setIdCollocazione(idCollocazione + 1);
                collocazione.setCorsia(corsia[i]);
                collocazione.setScaffale(scaffale[i]);
                idCollocazione += 1;
                collocazioneDAO.add(collocazione);
            } else {
                collocazione = collocazioneDAO.findByPosition(corsia[i], scaffale[i]);
            }

            prodottiMagazzino.setMagazzino(magazzino);
            prodottiMagazzino.setProdotto(prodotto);
            prodottiMagazzino.setQuantita(quantita[i]);
            prodottiMagazzino.setCollocazione(collocazione);
            prodottiMagazzinoDAO.add(prodottiMagazzino);
        }

        return 0;
    }

}
