package DAO.Magazzino;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MagazzinoDAO implements IMagazzinoDAO{
    private static MagazzinoDAO instance = new MagazzinoDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;
    public MagazzinoDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static MagazzinoDAO getInstance() {
        return instance;
    }

    @Override
    public Magazzino findMagazzinoById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM magazzino WHERE idMagazzino = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Magazzino magazzino = new Magazzino();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                magazzino.setId(rs.getInt("idMagazzino"));
                magazzino.setPuntoVendita(puntoVenditaDAO.findById(rs.getInt("idPuntoVendita")));
            }
            return magazzino;
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
    public ArrayList<Magazzino> findAllMagazzino() {
        String sql = "SELECT * FROM magazzino";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        ArrayList<Magazzino> magazzini = new ArrayList<>();
        try {
            while (rs.next()) {
                Magazzino magazzino = new Magazzino();
                PuntoVendita puntoVendita = new PuntoVendita();
                magazzino.setId(rs.getInt("IdMagazzino"));
                magazzino.setPuntoVendita(puntoVenditaDAO.findById(rs.getInt("idPuntoVendita")));
                magazzini.add(magazzino);
            }
            return magazzini;
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
    public Magazzino findMagazzinoByPunto(PuntoVendita punto) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM magazzino WHERE idPuntoVendita = '" + punto.getIdPuntoVendita() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Magazzino magazzino = new Magazzino();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                magazzino.setId(rs.getInt("idMagazzino"));
                magazzino.setPuntoVendita(puntoVenditaDAO.findById(rs.getInt("idPuntoVendita")));
            }
            return magazzino;
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
    public int add (Magazzino magazzino) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO magazzino (idMagazzino, idPuntoVendita) VALUES ('" + magazzino.getId() + "','" + magazzino.getPunto().getIdPuntoVendita() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(Magazzino magazzino) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM magazzino WHERE idMagazzino = '" + magazzino.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update (Magazzino magazzino){
        executor = new DbOperationExecutor();
        sql = "UPDATE magazzino SET idPuntoVendita = '" + magazzino.getPunto().getIdPuntoVendita() + "' WHERE idMagazzino = '" + magazzino.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public boolean existForPunto(PuntoVendita puntoVendita) {
        String sql = "SELECT count(*) AS count FROM mydb.magazzino AS C WHERE C.idPuntoVendita=" + puntoVendita.getIdPuntoVendita() + ";";
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
}
