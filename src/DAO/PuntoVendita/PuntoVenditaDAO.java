package DAO.PuntoVendita;


import DAO.Categoria.CategoriaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.Categoria;
import Model.ICategoria;
import Model.Manager;
import Model.PuntoVendita;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PuntoVenditaDAO implements IPuntoVenditaDAO {
    private final static PuntoVenditaDAO instance = new PuntoVenditaDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private PuntoVenditaDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static PuntoVenditaDAO getInstance(){
        return instance;
    }

    @Override
    public PuntoVendita findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT idPuntoVendita, nome, idManager FROM puntovendita WHERE idPuntoVendita = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        PuntoVendita puntoVendita = new PuntoVendita();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setName(rs.getString("nome"));
                puntoVendita.setidMan(rs.getInt("idManager"));
            }
            return puntoVendita;
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
    }

    @Override
    public PuntoVendita findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT idPuntoVendita, nome, idManager FROM puntovendita WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        PuntoVendita puntoVendita = new PuntoVendita();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setName(rs.getString("nome"));
                puntoVendita.setidMan(rs.getInt("idManager"));
            }
            return puntoVendita;
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
    }

    @Override
    public PuntoVendita findByIdManager(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT idPuntoVendita, nome, idManager FROM puntovendita WHERE idManager = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        PuntoVendita puntoVendita = new PuntoVendita();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita.setId(rs.getInt("idPuntoVendita"));
                puntoVendita.setName(rs.getString("nome"));
                puntoVendita.setidMan(rs.getInt("idManager"));
            }
            return puntoVendita;
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
    }

    @Override
    public ArrayList<PuntoVendita> findAll() {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM puntovendita;";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArrayList<PuntoVendita> punti = new ArrayList<>();
        try {
            while(rs.next()){
                PuntoVendita punto = new PuntoVendita();
                punto.setId(rs.getInt("idPuntoVendita"));
                punto.setName(rs.getString("nome"));
                punto.setidMan(rs.getInt("idManager"));
                punti.add(punto);
            }
            return punti;
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
    }

    @Override
    public int add(PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO puntovendita (idPuntoVendita, nome, idManager) VALUES ('" + puntoVendita.getId() + "','" + puntoVendita.getName() + "','" + puntoVendita.getIdMan() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM puntovendita WHERE idPuntoVendita = '" + puntoVendita.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "UPDATE puntovendita SET idPuntoVendita = '" + puntoVendita.getId() + "', nome = '" + puntoVendita.getName() + "', idManager = '" +puntoVendita.getIdMan() + "' WHERE idPuntoVendita = '" + puntoVendita.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }



}

