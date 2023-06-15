package View.Listener;

import Business.SessionManager;
import DAO.Articolo.ArticoloDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.Ordine.OrdineDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.*;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ManageOrdersListener implements ActionListener {

    public final static String BUY_BTN = "acquista-btn";

    private ArrayList<JCheckBox> articoliBox;
    private WideComboBox[] quantita;
    private WideComboBox puntoVendita;

    public ManageOrdersListener(ArrayList<JCheckBox> articoliBox, WideComboBox[] quantita, WideComboBox puntoVendita) {
        this.articoliBox = articoliBox;
        this.quantita = quantita;
        this.puntoVendita = puntoVendita;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (BUY_BTN.equals(e.getActionCommand())) {
            Utente cliente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            String nomePunto = puntoVendita.getSelectedItem().toString();
            PuntoVendita salePoint = PuntoVenditaDAO.getInstance().findByName(nomePunto);


            ArrayList<Integer> selectedIndex = new ArrayList<>();
            for (int i = 0; i < selectedIndex.size(); i++) {
                selectedIndex.remove(i);
            }
            for (int i = 0; i < articoliBox.size(); i++) {
                if (articoliBox.get(i).isSelected()) {
                    selectedIndex.add(i);
                }
            }

            ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
            ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
            ServizioDAO servizioDAO = ServizioDAO.getInstance();
            MagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
            Magazzino magazzino = magazzinoDAO.findMagazzinoByPunto(salePoint);
            ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();

            ArrayList<ProdottoOrdine> prodottiOrdine = new ArrayList<>();
            ArrayList<Servizio> serviziOrdine = new ArrayList<>();

            for (int i = 0; i < selectedIndex.size(); i++) {
                String nomeArticolo = articoliBox.get(selectedIndex.get(i)).getText();
                if (articoloDAO.isProdotto(articoloDAO.findByName(nomeArticolo).getId())) {
                    Prodotto p = prodottoDAO.findByName(nomeArticolo);
                    int q = Integer.parseInt(quantita[selectedIndex.get(i)].getSelectedItem().toString());
                    ProdottoOrdine prodottoOrdine = new ProdottoOrdine();
                    prodottoOrdine.setProdotto(p); prodottoOrdine.setQuantita(q);
                    prodottiOrdine.add(prodottoOrdine);
                    prodottiMagazzinoDAO.reduceQuantita(magazzino,p, q);
                } else if (articoloDAO.isServizio(articoloDAO.findByName(nomeArticolo).getId())){
                    Servizio s = servizioDAO.findByName(nomeArticolo);
                    serviziOrdine.add(s);
                }
            }

            OrdineDAO ordineDAO = OrdineDAO.getInstance();
            ArrayList<Ordine> ordini = ordineDAO.findAll();
            Date dataOdierna = new Date();
            Ordine ordine = new Ordine(ordini.size()+1, cliente, prodottiOrdine, serviziOrdine, dataOdierna, Ordine.StatoOrdine.DA_PAGARE);


            ordineDAO.add(ordine);
            JOptionPane.showMessageDialog(null, "Ordine eseguito correttamente");
        }
    }
}
