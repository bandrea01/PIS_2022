package DAO.PuntoVendita;

import Model.PuntoVendita;

import java.util.ArrayList;

public interface IPuntoVenditaDAO {
    PuntoVendita findByName(String name);
    ArrayList<PuntoVendita> findAll();
    int add(PuntoVendita puntoVendita);
    int removeByName(String name);
    int update(PuntoVendita puntoVendita);
    int getIdByName(String name);
}
