package DAO.Magazzino;

import Model.Magazzino;
import Model.PuntoVendita;

import java.util.ArrayList;

public interface IMagazzinoDAO {
    ArrayList<Magazzino> findAllMagazzino();
    Magazzino findMagazzinoById(int id);
    Magazzino findMagazzinoByPunto(PuntoVendita punto);
    int update(Magazzino magazzino);
    int add(Magazzino magazzino);
    int remove(Magazzino magazzino);

    boolean existForPunto(PuntoVendita puntoVendita);
}
