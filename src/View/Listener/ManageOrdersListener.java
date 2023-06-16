package View.Listener;


import Business.Bridge.DocumentoOrdine;
import Business.Bridge.PdfBoxAPI;
import Business.MailHelper;
import Business.SessionManager;
import DAO.Articolo.ArticoloDAO;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.Feedback.FeedbackDAO;
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
    public final static String PAY_BTN = "payorder-btn";
    public final static String FEEDBACK = "feedback-btn";

    private ArrayList<JCheckBox> articoliBox;
    private WideComboBox[] quantita;
    private WideComboBox puntoVendita;
    private WideComboBox ordine;
    private WideComboBox articolo;
    private JTextField commento;
    private WideComboBox gradimento;

    public ManageOrdersListener(WideComboBox puntoVendita, WideComboBox articolo, JTextField commento, WideComboBox gradimento) {
        this.puntoVendita = puntoVendita;
        this.articolo = articolo;
        this.commento = commento;
        this.gradimento = gradimento;
    }

    public ManageOrdersListener(ArrayList<JCheckBox> articoliBox, WideComboBox[] quantita, WideComboBox puntoVendita) {
        this.articoliBox = articoliBox;
        this.quantita = quantita;
        this.puntoVendita = puntoVendita;
    }

    public ManageOrdersListener(WideComboBox ordine) {
        this.ordine = ordine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (BUY_BTN.equals(e.getActionCommand())) {
            Utente cliente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            String nomePunto = puntoVendita.getSelectedItem().toString();
            PuntoVendita salePoint = PuntoVenditaDAO.getInstance().findByName(nomePunto);

            if (ClientePuntoVenditaDAO.getInstance().isClienteBanned(cliente, salePoint)) {
                JOptionPane.showMessageDialog(null, "You cannot buy in the sale point " + nomePunto + ", you have been banned");
                return;
            }

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

            DocumentoOrdine documentoOrdine = new DocumentoOrdine(ordine, new PdfBoxAPI(), PuntoVenditaDAO.getInstance().findByName(nomePunto));
            documentoOrdine.invia(cliente.getEmail());

            JOptionPane.showMessageDialog(null, "Order executed correctly. We have sent you an email with the purchase list attached. Pay in \"My orders\" section.");
        } else if (PAY_BTN.equals(e.getActionCommand())) {
            int nOrdine = Integer.parseInt(ordine.getSelectedItem().toString());
            OrdineDAO ordineDAO = OrdineDAO.getInstance();
            Ordine order = ordineDAO.findOrdineById(nOrdine);
            if (ordineDAO.isPagato(order)) {
                JOptionPane.showMessageDialog(null, "This order is already paid");
                return;
            }
            ordineDAO.paga(order);
            JOptionPane.showMessageDialog(null, "The order #" + nOrdine + " has been paid");
        } else if (FEEDBACK.equals(e.getActionCommand())) {
            String nomePunto = puntoVendita.getSelectedItem().toString();
            if ("Select a sale point".equalsIgnoreCase(nomePunto)) {
                JOptionPane.showMessageDialog(null, "Please, select a sale point");
                return;
            }
            Utente cliente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            String article = articolo.getSelectedItem().toString();
            String text = commento.getText();
            String rating = gradimento.getSelectedItem().toString();
            Manager manager = PuntoVenditaDAO.getInstance().findManagerOfPunto(PuntoVenditaDAO.getInstance().findByName(nomePunto));

            Feedback feedback = new Feedback();
            FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
            ArrayList<Feedback> feedbacks = feedbackDAO.findAll();


            feedback.setIdFeedback(feedbacks.size() + 1);
            feedback.setArticolo(ArticoloDAO.getInstance().findByName(article));
            feedback.setCommento(text);
            feedback.setRisposta(" ");
            feedback.setManager(manager);
            feedback.setUtente(cliente);
            if ("VERY BAD".equalsIgnoreCase(rating)) {
                feedback.setGradimento(Feedback.Gradimento.PESSIMO);
            } else if ("SCARCE".equalsIgnoreCase(rating)) {
                feedback.setGradimento(Feedback.Gradimento.SCARSO);
            } else if ("NORMAL".equalsIgnoreCase(rating)) {
                feedback.setGradimento(Feedback.Gradimento.NORMALE);
            } else if ("GOOD".equalsIgnoreCase(rating)) {
                feedback.setGradimento(Feedback.Gradimento.BUONO);
            } else if ("EXCELLENT".equalsIgnoreCase(rating)) {
                feedback.setGradimento(Feedback.Gradimento.ECCELLENTE);
            }

            feedbackDAO.add(feedback);

            String commentoMail = "idFeedback: " + feedback.getIdFeedback() + "\nCommento: " + text + "\nGradimento: " + rating + ".";
            MailHelper.getInstance().send(manager.getEmail(), "FEEDBACK ARTICOLO: " + article, commentoMail, null);

            JOptionPane.showMessageDialog(null, "Your feedback has been successfully added and sent to the " + nomePunto + "'s manager");
        }
    }
}
