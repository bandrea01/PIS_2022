package DAO;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.UtenteDAO;
import Model.PuntoVendita;
import Model.Utente;

import java.util.ArrayList;
import java.util.Scanner;

public class TestDAO {
    public static void main(String[] args) {
        System.out.println("Print all users in Database");
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        ArrayList<Utente> utenti = utenteDAO.findAll();
        for (Utente utente : utenti){
            System.out.println(utente);
        }

        System.out.println("\nPrint all Shops in Database");
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<PuntoVendita> punti = puntoVenditaDAO.findAll();
        for (PuntoVendita p : punti){
            System.out.println(p);
        }

        boolean done = false;
        while (!done) {
            System.out.print("\nInsert username to search (Q for quit): ");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if ("Q".equalsIgnoreCase(input)){
                done = true;
            } else {
                Utente utente = utenteDAO.findByName(input);
                System.out.println(utente);
            }
        }
    }
}
