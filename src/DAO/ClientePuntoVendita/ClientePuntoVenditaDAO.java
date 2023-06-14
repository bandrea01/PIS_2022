package DAO.ClientePuntoVendita;

import DAO.Collocazione.CollocazioneDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

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

    @Override
    public int banCliente(Utente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "UPDATE puntovendita_has_cliente SET bannato =1 WHERE idPuntoVendita =" + puntoVendita.getIdPuntoVendita() + " AND idUtente =" + cliente.getId() + ";";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int unbanCliente(Utente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "UPDATE puntovendita_has_cliente SET bannato =0 WHERE idPuntoVendita =" + puntoVendita.getIdPuntoVendita() + " AND idUtente =" + cliente.getId() + ";";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public ArrayList<Utente> findAllClienti() {
        String sql = "SELECT * FROM cliente";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Utente> clienti = new ArrayList<>();
        try {
            while (rs.next()) {
                Utente utente = UtenteDAO.getInstance().findById(rs.getInt("idUtente"));
                clienti.add(utente);
            }
            return clienti;
        } catch (SQLException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(readOp);
        }
        return null;
    }

    @Override
    public ArrayList<Utente> findAllByPunto(PuntoVendita punto) {
        String sql = "SELECT * FROM puntovendita_has_cliente WHERE idPuntoVendita = " + punto.getIdPuntoVendita() + ";";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        UtenteDAO utenteDAO = UtenteDAO.getInstance();

        ArrayList<Utente> listaUtenti = new ArrayList<>();
        try {
            while (rs.next()) {
                Utente utente = utenteDAO.findById(rs.getInt("idUtente"));

                listaUtenti.add(utente);
            }
            return listaUtenti;
        } catch (SQLException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(readOp);
        }
        return null;
    }



    @Override
    public boolean isClienteBanned(Utente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM puntovendita_has_cliente WHERE idUtente = '" + cliente.getId() + "' AND idPuntoVendita = '" + puntoVendita.getIdPuntoVendita() + "';";
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
        sql = "SELECT count(*) AS count FROM puntovendita_has_cliente WHERE idUtente = '" + cliente.getId() + "' AND idPuntoVendita = '" + puntoVendita.getIdPuntoVendita() + "';";
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
    public boolean isCliente(Cliente cliente) {
        String sql = "SELECT count(*) AS count FROM cliente AS U WHERE U.idUtente='" + cliente.getId() + "';";
        IDbOperation readOp = new ReadOperation(sql);
        DbOperationExecutor executor = new DbOperationExecutor();
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1){
                int count = rs.getInt("count");
                return count == 1;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int add(Cliente cliente) {
        executor = new DbOperationExecutor();
        int rowCount;
        String sql1 = "INSERT INTO cliente (idUtente) VALUES ('" + cliente.getId() + "');";
        String sql2 = "INSERT INTO puntovendita_has_cliente (idPuntoVendita, idUtente, canalePreferito, bannato) VALUES ('" + cliente.getPuntoVenditaRegistrato().getIdPuntoVendita() + "','" + cliente.getId() + "','" + cliente.getCanalePreferito() + "','0');";
        if (!ClientePuntoVenditaDAO.getInstance().isCliente(cliente)) {
            IDbOperation writeOp1 = new WriteOperation(sql1);
            IDbOperation writeOp2 = new WriteOperation(sql2);

            rowCount = executor.executeOperation(writeOp1).getRowsAffected();


            rowCount += executor.executeOperation(writeOp2).getRowsAffected();

            executor.close(writeOp1);
            executor.close(writeOp2);
            return rowCount;
        }
        IDbOperation writeOp2 = new WriteOperation(sql2);

        rowCount = executor.executeOperation(writeOp2).getRowsAffected();


        executor.close(writeOp2);
        return rowCount;
    }

    @Override
    public int update(Cliente cliente) {
        executor = new DbOperationExecutor();
        sql = "UPDATE puntovendita_has_cliente SET idPuntoVendita = '" + cliente.getPuntoVenditaRegistrato().getIdPuntoVendita() + "', idCliente = '" + cliente.getId() + "', canalePreferito = '" + cliente.getCanalePreferito() + "', bannato = " + cliente.isBanned() + "' WHERE idCliente = '" + cliente.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int removeClienteFromPuntoVendita(Utente cliente, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        int rowCount;
        String sql =  "DELETE FROM puntovendita_has_cliente WHERE idUtente = " + cliente.getId() + " AND idPuntoVendita = " + puntoVendita.getIdPuntoVendita() + ";";
        IDbOperation writeOp = new WriteOperation(sql);
        rowCount = executor.executeOperation(writeOp).getRowsAffected();
        executor.close(writeOp);
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

    @Override
    public ArrayList<PuntoVendita> findAllbyCliente(Utente cliente) {
        String sql = "SELECT * FROM puntovendita_has_cliente WHERE idUtente = " + cliente.getId() + ";";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        UtenteDAO utenteDAO = UtenteDAO.getInstance();

        ArrayList<PuntoVendita> listaPuntiVendita = new ArrayList<>();
        try {
            while (rs.next()) {
                PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findById(rs.getInt("idPuntoVendita"));

                listaPuntiVendita.add(puntoVendita);
            }
            return listaPuntiVendita;
        } catch (SQLException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(readOp);
        }
        return null;
    }
}
