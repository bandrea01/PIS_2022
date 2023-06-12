package DAO.ClientePuntoVendita;

import Model.Cliente;
import Model.PuntoVendita;
import Model.Utente;

import java.util.ArrayList;

public interface IClientePuntoVenditaDAO {

    boolean isClienteBanned (Utente cliente, PuntoVendita puntoVendita);
    boolean isClienteRegistred (Cliente cliente, PuntoVendita puntoVendita);
    int add (Cliente cliente);
    int update (Cliente cliente);
    int remove (Cliente cliente);
    ArrayList<Utente> findAllByPunto(PuntoVendita punto);
    int banCliente(Utente cliente, PuntoVendita puntoVendita);
    int unbanCliente(Utente cliente, PuntoVendita puntoVendita);
    int removeClienteFromPuntoVendita(Utente cliente, PuntoVendita puntoVendita);
}
