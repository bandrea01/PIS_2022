package DAO.Articolo;

import Business.ImmagineBusiness;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;
import Model.Immagine;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImmagineDAO implements IImmagineDAO{
    private final static ImmagineDAO instance = new ImmagineDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ImmagineDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ImmagineDAO getInstance(){
        return instance;
    }

    @Override
    public ArrayList<Immagine> findAll() {
        String sql = "SELECT * FROM immagine";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Immagine> immagini = new ArrayList<>();
        try {
            while (rs.next()) {
                Immagine immagine = new Immagine();
                immagine.setIdImmagine(rs.getInt("idImmagine"));
                immagine.setIdArticolo(rs.getInt("idArticolo"));
                immagine.setPathImmagine(rs.getString("immagine"));
                immagini.add(immagine);
            }
            return immagini;
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
    public int add(Immagine immagine) {
        int rowCount;
        sql = "INSERT INTO immagine (idImmagine, idArticolo, immagine) VALUES ('" + immagine.getIdImmagine() + "','" + immagine.getIdArticolo() + "','" + immagine.getPathImmagine() + "');";
        executor = new DbOperationExecutor();
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);

        return rowCount;
    }

    @Override
    public int add(ArrayList<Immagine> immagini) {
        int rowCount = 0;
        for (Immagine i : immagini) {
            sql = "INSERT INTO immagine (idImmagine, idArticolo, immagine) VALUES ('" + i.getIdImmagine() + "','" + i.getIdArticolo() + "','" + ImmagineBusiness.getInstance().getBlobByImage(i.getImage(), "png") + "');";
            executor = new DbOperationExecutor();
            dbOperation = new WriteOperation(sql);
            rowCount += executor.executeOperation(dbOperation).getRowsAffected();
            executor.close(dbOperation);
        }
        return rowCount;
    }

    @Override
    public String getPathByIdArticolo(int idArticolo) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM immagine WHERE idArticolo = '" + idArticolo + "';";
        dbOperation = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(dbOperation).getResultSet();
        Immagine immagine = new Immagine();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                immagine.setIdImmagine(rs.getInt("idImmagine"));
                immagine.setIdArticolo(rs.getInt("idArticolo"));
                immagine.setPathImmagine(rs.getString("immagine"));
            }
            return immagine.getPathImmagine();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Resultset: " + e.getMessage());
        }
        finally {
            executor.close(dbOperation);
        }
        return null;
    }

    @Override
    public Immagine findImageById(int idImmagine) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM immagine WHERE idImmagine = '" + idImmagine + "';";
        dbOperation = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(dbOperation).getResultSet();
        Immagine immagine = new Immagine();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                immagine.setIdImmagine(rs.getInt("idImmagine"));
                immagine.setIdArticolo(rs.getInt("idArticolo"));
                immagine.setPathImmagine(rs.getString("immagine"));
            }
            return immagine;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Resultset: " + e.getMessage());
        }
        finally {
            executor.close(dbOperation);
        }
        return null;
    }

    @Override
    public ArrayList<Immagine> findImagesByArticoloId(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM immagine WHERE idArticolo = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArrayList<Immagine> immagini = new ArrayList<>();
        try {
            while (rs.next()) {
                Immagine immagine = new Immagine();
                immagine.setIdImmagine(rs.getInt("idImmagine"));
                immagine.setIdArticolo(rs.getInt("idArticolo"));
                immagine.setImage(ImmagineBusiness.getInstance().getImageByBlob(rs.getBlob("immagine")));
                immagini.add(immagine);
            }
            return immagini;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Resultset: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executor.close(dbOperation);
        }
        return null;
    }

    @Override
    public int removeSingleImageById (int idImmagine) {
        executor = new DbOperationExecutor();
        int rowCount;
        try {
            String sql = "DELETE FROM immagine WHERE idImmagine = '" + idImmagine + "';";
            dbOperation = new WriteOperation(sql);
            rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        } finally {
            executor.close(dbOperation);
        }
        return rowCount;
    }
}
