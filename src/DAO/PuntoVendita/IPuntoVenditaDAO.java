package DAO.PuntoVendita;

import Model.Manager;
import Model.PuntoVendita;

import java.util.ArrayList;

public interface IPuntoVenditaDAO {
    PuntoVendita findById (int id);
    PuntoVendita findByName(String name);
    PuntoVendita findByIdManager (int id);
    ArrayList<PuntoVendita> findAll();
    int add(PuntoVendita puntoVendita);
    int removeById(int id);
    int removeByName(String name);
    int update(PuntoVendita puntoVendita);
}
