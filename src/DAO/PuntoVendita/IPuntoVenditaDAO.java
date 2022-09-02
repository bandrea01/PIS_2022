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
    int add(PuntoVendita puntoVendita);
    int remove(PuntoVendita puntoVendita);
    int update(PuntoVendita puntoVendita);
}
