package View.Panel;

import Business.PuntoVenditaBusiness;
import Business.UtenteBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.Prodotto;
import Model.PuntoVendita;
import Model.Servizio;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManageCatalogPanel extends JPanel {
    MainLayout window;
    JPanel centre;
    JPanel south;
    ManagePointsListener listener;
    public ManageCatalogPanel(MainLayout window) {
        this.setLayout(new BorderLayout(20,20));
        this.window = window;
        //this.listener = new ManageCatalogListener(window);

        //Pannello punti centre
        centre = new JPanel();
        centre.setLayout(new GridLayout(0,2));
        //for (JButton b : getPointsButtons()){
          //  centre.add(b);
        //}

        //Pannello bottoni south
        south = new JPanel();
        south.setLayout(new GridLayout(2,2));
        south.setAlignmentX(CENTER_ALIGNMENT);

        //Bottoni centro: crea, modifica, elimina
        UtenteBusiness utenteBusiness = UtenteBusiness.getInstance();
        south.add(ButtonCreator.createButton("Create new", true, ButtonCreator.LILLE, e -> window.showCreatePointPanel(), null));
        south.add(ButtonCreator.createButton("Modify", true, ButtonCreator.LILLE, listener, ManagePointsListener.MODIFY_BTN));
        south.add(ButtonCreator.createButton("Create Manager", true, ButtonCreator.LILLE, e -> utenteBusiness.createManager(), null));
        south.add(ButtonCreator.createButton("Delete Manager", true, ButtonCreator.LILLE, listener, ManagePointsListener.DELETE_MAN_BTN));
        south.add(ButtonCreator.createButton("Delete Point", true, ButtonCreator.LILLE, listener, ManagePointsListener.DELETE_BTN));

        //Aggiungo al pannello principale
        this.add(centre, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
        this.repaint(); this.validate();
    }

    public ArrayList<JButton> getProdottiButtons(){
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();

        ArrayList<Prodotto> prodotti = prodottoDAO.findAll();
        ArrayList<Servizio> servizi = servizioDAO.findAll();

        for (Prodotto p : prodotti){

        }
        ArrayList<JButton> buttons = new ArrayList<>();
        PuntoVenditaBusiness puntoVenditaBusiness = PuntoVenditaBusiness.getInstance();
        //for (PuntoVendita p : punti){
          //  buttons.add(ButtonCreator.createButton(p.getName(), true, ButtonCreator.SLIME, e -> puntoVenditaBusiness.showInformations(p) , null));
        //}
        return buttons;
    }
}
