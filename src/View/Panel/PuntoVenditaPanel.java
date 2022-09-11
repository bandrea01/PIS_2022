package View.Panel;

import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.PuntoVendita;
import View.MainLayout;

import javax.swing.*;

public class PuntoVenditaPanel extends JPanel{

    public PuntoVenditaPanel (MainLayout window){
        new SpringLayout();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        for (PuntoVendita p : puntoVenditaDAO.findAll()){
            JButton button = new JButton(p.getName());
        }

    }
}
