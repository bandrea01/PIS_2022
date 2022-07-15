package DAO.Produttore;

import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Fornitore;
import Model.Produttore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProduttoreDAO implements IProduttoreDAO{
    private final static ProduttoreDAO instance = new ProduttoreDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ProduttoreDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ProduttoreDAO getInstance(){
        return instance;
    }


    @Override
    public Produttore findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM produttore WHERE idProduttore = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Produttore produttore = new Produttore();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                produttore.setId(rs.getInt("idProduttore"));
                produttore.setNome(rs.getString("nome"));
                produttore.setSito(rs.getString("sito"));
                produttore.setCitta(rs.getString("citta"));
                produttore.setNazione(rs.getString("nazione"));
            }
            return produttore;
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
    public Produttore findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM produttore WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Produttore produttore = new Produttore();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                produttore.setId(rs.getInt("idProduttore"));
                produttore.setNome(rs.getString("nome"));
                produttore.setSito(rs.getString("sito"));
                produttore.setCitta(rs.getString("citta"));
                produttore.setNazione(rs.getString("nazione"));
            }
            return produttore;
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
    public int add(Produttore produttore) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO produttore (idProduttore, nome, sito, citta, nazione) VALUES ('" + produttore.getId() + "','" + produttore.getNome() + "','" + produttore.getSito() + "','" + produttore.getCitta() + "','" + produttore.getNazione() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(Produttore produttore) {
        executor = new DbOperationExecutor();
        sql = "UPDATE produttore SET idProduttore = '" + produttore.getId() + "', nome = '" + produttore.getNome() + "', " +
                " sito = '" + produttore.getSito() + "', citta = '" + produttore.getCitta() + "', " +
                " nazione = '" + produttore.getNazione() + "' WHERE idProduttore = '" + produttore.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(int id) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM produttore WHERE idProduttore = '" + id + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
