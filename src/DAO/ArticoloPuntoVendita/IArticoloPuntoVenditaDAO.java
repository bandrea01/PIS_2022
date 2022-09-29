package DAO.ArticoloPuntoVendita;

import DAO.PuntoVendita.PuntoVenditaDAO;
import Model.Articolo;
import Model.PuntoVendita;

import java.util.ArrayList;

public interface IArticoloPuntoVenditaDAO {
    ArrayList<Articolo> findByPunto (PuntoVendita puntoVendita);
    int add (Articolo articolo, PuntoVendita puntoVendita);
    int remove (Articolo articolo, PuntoVendita puntoVendita);
    int removeArticoloFromAll(Articolo articolo);
    int removeByPunto (PuntoVendita puntoVendita);
}
