package Business;

import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.Articolo;
import Model.Manager;
import Model.PuntoVendita;
import Model.Utente;
import View.MainLayout;
import View.ViewModel.WideComboBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PuntoVenditaBusiness {
    private static PuntoVenditaBusiness instance;

    public static synchronized PuntoVenditaBusiness getInstance() {
        if (instance == null) {
            instance = new PuntoVenditaBusiness();
        }
        return instance;
    }

    public void showInformations(PuntoVendita punto) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        Manager manager = utenteDAO.getManagerById(punto.getIdManager());

        JOptionPane.showMessageDialog(null,
                "Point name: " + punto.getName() + "\n" +
                        "Manager username: " + manager.getUsername() + "\n" +
                        "Manager email: " + manager.getEmail() + "\n" +
                        "Manager phone: " + manager.getPhone() + "\n\n" +
                        "Articles in sale point:" + "\n" +
                        getAllNameArticoli(punto));
    }

    private String getAllNameArticoli(PuntoVendita punto){
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        StringBuilder string = new StringBuilder();
        for (Articolo a : articoloPuntoVenditaDAO.findByPunto(punto)){
            string.append("-").append(a.getName()).append("\n");
        }
        return string.toString();
    }
    public void createPuntoVendita (String name, WideComboBox manager, ArrayList<Articolo> articoli, MainLayout window){
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();


        PuntoVendita puntoVendita = new PuntoVendita();
        if (!utenteDAO.emailExist(manager.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null,"Error! Email doesn't exist");
            return;
        }
        Utente utente = utenteDAO.findByEmail(manager.getSelectedItem().toString());
        if (puntoVenditaDAO.hasThisManager(utente.getId())) {
            JOptionPane.showMessageDialog(null, "User select is alredy a manager of a sale point");
            return;
        }
        if (!utenteDAO.isManager(utente.getUsername())){
            JOptionPane.showMessageDialog(null,"Error! User selected is not a manager");
            return;
        }
        if (puntoVenditaDAO.puntoVenditaExist(name)){
            JOptionPane.showMessageDialog(null, "Error! Sale point name alredy exist");
            return;
        }

        puntoVendita.setName(name);
        puntoVendita.setManager(utente.getId());

        if (1 != puntoVenditaDAO.add(puntoVendita)){
            JOptionPane.showMessageDialog(null, "System Error! Sale point not created");
        }
        for (Articolo a : articoli){
            if (1 != articoloPuntoVenditaDAO.add(a, puntoVenditaDAO.findByName(name))){
                puntoVenditaDAO.remove(puntoVendita);
                JOptionPane.showMessageDialog(null, "System Error! Sale point not created");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Sale point created succesfully!");
        window.managePoints();
    }
    public void deletePunto(PuntoVendita punto) {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();

        int check1 = articoloPuntoVenditaDAO.removeByPunto(punto);
        int check2 = puntoVenditaDAO.remove(punto);

        if (check1 == 1 && check2 == 1){
            JOptionPane.showMessageDialog(null, "Sale Point delete succesfully!");
        }
    }
    public void modifyPunto(String oldName, String newName, String newManager, ArrayList<Articolo> newArticoli, MainLayout window) {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArticoloPuntoVenditaDAO articoloPuntoVenditaDAO = ArticoloPuntoVenditaDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();

        String oldManager = utenteDAO.findById(puntoVenditaDAO.findByName(oldName).getIdManager()).getEmail();
        ArrayList<Articolo> oldArticoli = articoloPuntoVenditaDAO.findByPunto(puntoVenditaDAO.findByName(oldName));

        oldArticoli.sort(Comparator.comparingInt(Articolo::getId));
        newArticoli.sort(Comparator.comparingInt(Articolo::getId));

        //Punti identici
        if (oldName.equalsIgnoreCase(newName) && oldManager.equalsIgnoreCase(newManager) && articoliEquals(oldArticoli, newArticoli)){
            JOptionPane.showMessageDialog (null, "No changes detected");
            window.managePoints();
            return;
        }
        //Nome e manager uguali -> articoli diversi
        if (oldName.equalsIgnoreCase(newManager) && oldManager.equalsIgnoreCase(newManager)){
            //Rimuovo articoli vecchi
            if (1 != articoloPuntoVenditaDAO.removeByPunto(puntoVenditaDAO.findByName(oldName))){
                JOptionPane.showMessageDialog(null, "System Error! Sale point not modified");
                window.managePoints();
                return;
            }
            //Inserisco nuovi
            for (Articolo a : newArticoli){
                articoloPuntoVenditaDAO.add(a, puntoVenditaDAO.findByName(oldName));
            }
            return;
        }
        //Nome e manager diversi
        //Articoli uguali -> modifico solo punto
        if (articoliEquals(oldArticoli, newArticoli)){
            if (!utenteDAO.emailExist(newManager)){
                JOptionPane.showMessageDialog(null,"Error! Username doesn't exist");
                return;
            }
            Utente utente = utenteDAO.findByEmail(newManager);
            if (!utenteDAO.isManager(utente.getUsername())){
                JOptionPane.showMessageDialog(null, "Error! User selected is not a manager");
                return;
            }
            PuntoVendita newPunto = puntoVenditaDAO.findByName(oldName);
            newPunto.setName(newName);
            newPunto.setManager(utenteDAO.findByEmail(newManager).getId());
            if (1 != puntoVenditaDAO.update(newPunto)){
                JOptionPane.showMessageDialog(null, "System Error! Sale point not modified");
                window.managePoints();
                return;
            }
            JOptionPane.showMessageDialog(null, "Sale point succesfully update!");
            window.managePoints();
            return;
        }
        //Articoli diversi -> modifico tutto
        //Rimuovo tutti articoli
        if (oldArticoli.size() != articoloPuntoVenditaDAO.removeByPunto(puntoVenditaDAO.findByName(oldName))){
            JOptionPane.showMessageDialog(null, "System Error! Sale point not modified");
            window.managePoints();
            return;
        }
        //Aggiorno punto
        PuntoVendita newPunto = puntoVenditaDAO.findByName(oldName);
        newPunto.setName(newName);
        newPunto.setManager(utenteDAO.findByEmail(newManager).getId());
        if (1 != puntoVenditaDAO.update(newPunto)){
            JOptionPane.showMessageDialog(null, "System Error! Sale point not modified");
            window.managePoints();
            return;
        }
        //Inserisco nuovi articoli
        PuntoVendita puntoVendita = puntoVenditaDAO.findByName(newName);
        for (Articolo a : newArticoli){
            articoloPuntoVenditaDAO.add(a, puntoVendita);
        }
        JOptionPane.showMessageDialog (null, "Sale point successfully updated!");
        window.managePoints();
    }
    private boolean articoliEquals(ArrayList<Articolo> oldArticoli, ArrayList<Articolo> newArticoli) {
        oldArticoli.sort(Comparator.comparingInt(Articolo::getId));
        newArticoli.sort(Comparator.comparingInt(Articolo::getId));
        if (oldArticoli.size() != newArticoli.size()){
            return false;
        }
        for (int i = 0; i < oldArticoli.size(); i++){
            if (oldArticoli.get(i).getId() != newArticoli.get(i).getId()){
                return false;
            }
        }
        return true;
    }

}
