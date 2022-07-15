package DAO.ClientePuntoVendita;

import DAO.Categoria.CategoriaDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Categoria;
import Model.Cliente;
import Model.ICategoria;
import Model.PuntoVendita;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientePuntoVenditaDAO implements IClientePuntoVenditaDAO {

    private final static ClientePuntoVenditaDAO instance = new ClientePuntoVenditaDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ClientePuntoVenditaDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ClientePuntoVenditaDAO getInstance(){
        return instance;
    }

    //TODO
    //Prima creare DAO Cliente
    /*@Override
    public Cliente getClientebyPunto (PuntoVendita puntoVendita) {
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM puntovendita_has_cliente WHERE idPuntoVendita = '" + puntoVendita.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        try {
            rs.next();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return null;
    }*/

    @Override
    public boolean isClienteBanned(Cliente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM puntovendita_has_cliente WHERE idUtente = '" + cliente.getId() + "' AND idPuntoVendita = '" + puntoVendita.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        try {
            rs.next();
            return rs.getBoolean("bannato");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return true;
    }

    @Override
    public boolean isClienteRegistred(Cliente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "SELECT count(*) AS count FROM puntovendita_has_cliente WHERE idUtente = '" + cliente.getId() + "' AND idPuntoVendita = '" + puntoVendita.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1){
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return false;
    }

    @Override
    public int add(Cliente cliente) {
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        utenteDAO.addUtente(cliente);
        executor = new DbOperationExecutor();
        int rowCount;
        String sql1 = "INSERT INTO cliente (idUtente) VALUES ('" + cliente.getId() + "');";
        String sql2 = "INSERT INTO puntovendita_has_cliente (idPuntoVendita, idUtente, canalePreferito, bannato) VALUES ('" + cliente.getPuntoVenditaRegistrato().getId() + "','" + cliente.getId() + "','" + cliente.getCanalePreferito() + "','0');";
        IDbOperation writeOp1 = new WriteOperation(sql1);
        IDbOperation writeOp2 = new WriteOperation(sql2);

        rowCount = executor.executeOperation(writeOp1).getRowsAffected();
        rowCount += executor.executeOperation(writeOp2).getRowsAffected();

        executor.close(writeOp1);
        executor.close(writeOp2);
        return rowCount;
    }

    @Override
    public int update(Cliente cliente) {
        executor = new DbOperationExecutor();
        sql = "UPDATE puntovendita_has_cliente SET idPuntoVendita = '" + cliente.getPuntoVenditaRegistrato().getId() + "', idCliente = '" + cliente.getId() + "', canalePreferito = '" + cliente.getCanalePreferito() + "', bannato = " + cliente.isBanned() + "' WHERE idCliente = '" + cliente.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(Cliente cliente){
        executor = new DbOperationExecutor();
        int rowCount;
        String sql1 =  "DELETE FROM puntovendita_has_cliente WHERE idUtente = '" + cliente.getId() + "';";
        String sql2 = "DELETE FROM cliente WHERE idUtente = '" + cliente.getId() + "';";
        IDbOperation writeOp1 = new WriteOperation(sql1);
        IDbOperation writeOp2 = new WriteOperation(sql2);

        rowCount = executor.executeOperation(writeOp1).getRowsAffected();
        rowCount += executor.executeOperation(writeOp2).getRowsAffected();

        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        utenteDAO.removeById(cliente.getId());

        executor.close(writeOp1);
        executor.close(writeOp2);
        return rowCount;
    }
}
