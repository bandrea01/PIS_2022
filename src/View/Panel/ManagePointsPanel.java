package View.Panel;

import Business.PuntoVenditaBusiness;
import Business.UtenteBusiness;
import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.PuntoVendita;
import View.Listener.ManagePointsListener;
import View.MainLayout;
import View.ViewModel.ButtonCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManagePointsPanel extends JPanel {
    MainLayout window;
    JPanel centre;
    JPanel south;
    ManagePointsListener listener;
    public ManagePointsPanel(MainLayout window) {
        this.setLayout(new BorderLayout(20,20));
        this.window = window;
        this.listener = new ManagePointsListener(window);

        //Pannello punti centre
        centre = new JPanel();
        centre.setLayout(new GridLayout(0,2));
        for (JButton b : getPointsButtons()){
            centre.add(b);
        }

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

    public ArrayList<JButton> getPointsButtons(){
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
        ArrayList<JButton> buttons = new ArrayList<>();
        PuntoVenditaBusiness puntoVenditaBusiness = PuntoVenditaBusiness.getInstance();
        for (PuntoVendita p : punti){
            buttons.add(ButtonCreator.createButton(p.getName(), true, ButtonCreator.SLIME, e -> puntoVenditaBusiness.showInformations(p) , null));
        }
        return buttons;
    }
}
