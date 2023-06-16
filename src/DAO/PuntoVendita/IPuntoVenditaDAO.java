package DAO.PuntoVendita;

import Model.Categoria;
import Model.Manager;
import Model.PuntoVendita;

import java.util.ArrayList;

public interface IPuntoVenditaDAO {
    PuntoVendita findById (int id);
    PuntoVendita findByName(String name);
    PuntoVendita findByIdManager (int id);
    ArrayList<PuntoVendita> findAll();
    boolean puntoVenditaExist(String name);
    int add(PuntoVendita puntoVendita);
    int remove(PuntoVendita puntoVendita);
    int update(PuntoVendita puntoVendita);

    boolean hasThisManager(int id);

    Manager findManagerOfPunto(PuntoVendita puntoVendita);
}
