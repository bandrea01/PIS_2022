package Business;

import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Collocazione.CollocazioneDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.*;

import javax.swing.*;

public class MagazzinoBusiness {
    private static MagazzinoBusiness instance;

    public static synchronized MagazzinoBusiness getInstance() {
        if (instance == null) {
            instance = new MagazzinoBusiness();
        }
        return instance;
    }

    public void rifornisciProdotto(String nomeProdotto, int quantita, Manager manager) {
        Prodotto prodotto = ProdottoDAO.getInstance().findByName(nomeProdotto);
        PuntoVendita punto = PuntoVenditaDAO.getInstance().findByIdManager(manager.getId());
        Magazzino magazzino = MagazzinoDAO.getInstance().findMagazzinoByPunto(punto);
        ProdottiMagazzinoDAO.getInstance().addQuantita(magazzino, prodotto, quantita);
        JOptionPane.showMessageDialog(null, "Product '"+ nomeProdotto + "' supplied correctly");
    }

    public int modifyMagazzino(String nomePuntoVendita, String[] prodotti,String[] prodottiUnselected, int[] corsia, int[] scaffale, int[] quantita) {
        MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();
        ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
        int idCollocazione = collocazioneDAO.findAll().size();

        if (nomePuntoVendita.isEmpty()) {
            return 1;
        }
        if ("Select a sale point".equalsIgnoreCase(nomePuntoVendita)) {
            return 1;
        }

        PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findByName(nomePuntoVendita);

        //controllo se il punto vendita non ha ancora un magazzino
        if (!magazzinoDAO.existForPunto(puntoVendita)) {
            return 2;
        }

        Magazzino magazzino = magazzinoDAO.findMagazzinoByPunto(puntoVendita);

        //controllo se i prodotti non sono nel punto vendita del magazzino
        for (int i = 0; i < prodotti.length; i++) {
            Prodotto prodotto = prodottoDAO.findByName(prodotti[i]);
            if (!ArticoloPuntoVenditaDAO.getInstance().hasArticolo(puntoVendita, prodotto)) {
                return 3;
            }
        }

        //controllo se i prodotti non selezionati erano già presenti
        for (int i = 0; i < prodottiUnselected.length; i++) {
            Prodotto prodottoUnselected = prodottoDAO.findByName(prodottiUnselected[i]);
            if (prodottiMagazzinoDAO.magazzinoHasProdotto(magazzino ,prodottoUnselected)) {
                prodottiMagazzinoDAO.removeProdotto(prodottoUnselected);
            }
        }

        for (int i = 0; i < prodotti.length; i++) {

            Collocazione collocazione = new Collocazione();
            ProdottiMagazzino prodottiMagazzino = new ProdottiMagazzino();
            Prodotto prodotto = prodottoDAO.findByName(prodotti[i]);

            if (!ArticoloPuntoVenditaDAO.getInstance().hasArticolo(puntoVendita, prodotto)) {
                return 3;
            }

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

            if (prodottiMagazzinoDAO.magazzinoHasProdotto(magazzino, prodotto)) {
                prodottiMagazzinoDAO.update(prodottiMagazzino);
            } else {
                prodottiMagazzinoDAO.add(prodottiMagazzino);
            }
        }

        return 0;

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

        //prodotti corsia e scaffale sono già selezionati nel listener con l'indice selezionato passato dal panel

        //controllo se i prodotti non sono nel punto vendita del magazzino
        for (int i = 0; i < prodotti.length; i++) {
            Prodotto prodotto = prodottoDAO.findByName(prodotti[i]);
            if (!ArticoloPuntoVenditaDAO.getInstance().hasArticolo(puntoVendita, prodotto)) {
                return 3;
            }
        }

        magazzinoDAO.add(magazzino);

        for (int i = 0; i < prodotti.length; i++) {

            Collocazione collocazione = new Collocazione();
            ProdottiMagazzino prodottiMagazzino = new ProdottiMagazzino();
            Prodotto prodotto = prodottoDAO.findByName(prodotti[i]);

            if (!ArticoloPuntoVenditaDAO.getInstance().hasArticolo(puntoVendita, prodotto)) {
                return 3;
            }

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
