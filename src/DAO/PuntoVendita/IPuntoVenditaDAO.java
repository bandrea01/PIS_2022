package DAO.PuntoVendita;

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
    int removeById(int id);
    int update(PuntoVendita puntoVendita);
}
