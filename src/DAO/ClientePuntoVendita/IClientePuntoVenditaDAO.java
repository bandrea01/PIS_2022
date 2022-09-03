package DAO.ClientePuntoVendita;

import Model.Cliente;
import Model.PuntoVendita;

import java.util.ArrayList;

public interface IClientePuntoVenditaDAO {

    boolean isClienteBanned (Cliente cliente, PuntoVendita puntoVendita);
    boolean isClienteRegistred (Cliente cliente, PuntoVendita puntoVendita);
    int add (Cliente cliente);
    int update (Cliente cliente);
    int remove (Cliente cliente);

}
