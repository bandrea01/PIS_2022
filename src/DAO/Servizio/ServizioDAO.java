package DAO.Servizio;

import DAO.Articolo.ArticoloDAO;
import DAO.Fornitore.FornitoreDAO;
import DAO.Produttore.ProduttoreDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;
import Model.Fornitore;
import Model.Prodotto;
import Model.Servizio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServizioDAO implements IServizioDAO {
    private final static ServizioDAO instance = new ServizioDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ServizioDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ServizioDAO getInstance(){
        return instance;
    }


    @Override
    public Servizio findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM servizio WHERE idServizio = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        Articolo articolo = articoloDAO.findById(id);
        Servizio servizio = new Servizio();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                servizio.setId(rs.getInt("idServizio"));
                servizio.setName(articolo.getName());
                servizio.setPrezzo(articolo.getPrezzo());
                servizio.setDescrizione(articolo.getDescrizione());
                servizio.setImmagini(articolo.getImmagini());
                servizio.setFornitore(fornitoreDAO.findById(rs.getInt("idFornitore")));
            }
            return servizio;
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
    public Servizio findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM servizio WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        Articolo articolo = articoloDAO.findByName(name);
        Servizio servizio = new Servizio();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                servizio.setId(rs.getInt("idServizio"));
                servizio.setName(articolo.getName());
                servizio.setPrezzo(articolo.getPrezzo());
                servizio.setDescrizione(articolo.getDescrizione());
                servizio.setImmagini(articolo.getImmagini());
                servizio.setFornitore(fornitoreDAO.findById(rs.getInt("idFornitore")));
            }
            return servizio;
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
    public Servizio findByFornitore(Fornitore fornitore) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM servizio WHERE idFornitore = '" + fornitore.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        Servizio servizio = new Servizio();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                Articolo articolo = articoloDAO.findById(rs.getInt("idServizio"));
                servizio.setId(rs.getInt("idServizio"));
                servizio.setName(articolo.getName());
                servizio.setPrezzo(articolo.getPrezzo());
                servizio.setDescrizione(articolo.getDescrizione());
                servizio.setImmagini(articolo.getImmagini());
                servizio.setFornitore(fornitoreDAO.findById(rs.getInt("idFornitore")));
            }
            return servizio;
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
    public int add(Servizio servizio) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO servizio (idServizio, idFornitore) VALUES ('" + servizio.getId() + "','" + servizio.getFornitore().getId() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(Servizio servizio) {
        executor = new DbOperationExecutor();
        sql = "UPDATE servizio SET idServizio = '" + servizio.getId() + "', idFornitore = '" + servizio.getFornitore().getId() + "' WHERE idServizio = '" + servizio.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(int id) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM servizio WHERE idServizio = '" + id + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    public ArrayList<Servizio> findAll() {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM servizio";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        FornitoreDAO fornitoreDAO = FornitoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Servizio> servizi = new ArrayList<>();
        Servizio servizio = new Servizio();
        try {
            while (rs2.next()) {
                if (rs2.getRow() == 0){
                    return null;
                }
                Articolo articolo = articoloDAO.findById(rs2.getInt("idServizio"));
                servizio.setId(rs2.getInt("idServizio"));
                servizio.setName(articolo.getName());
                servizio.setPrezzo(articolo.getPrezzo());
                servizio.setDescrizione(articolo.getDescrizione());
                servizio.setImmagini(articolo.getImmagini());
                servizio.setFornitore(fornitoreDAO.findById(rs2.getInt("idFornitore")));
                servizi.add(servizio);
            }
            return servizi;
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
}
