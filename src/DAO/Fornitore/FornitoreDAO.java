package DAO.Fornitore;

import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Fornitore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FornitoreDAO implements IFornitoreDAO{
    private final static FornitoreDAO instance = new FornitoreDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private FornitoreDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static FornitoreDAO getInstance(){
        return instance;
    }


    @Override
    public Fornitore findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM fornitore WHERE idFornitore = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Fornitore fornitore = new Fornitore();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                fornitore.setId(rs.getInt("idFornitore"));
                fornitore.setNome(rs.getString("nome"));
                fornitore.setSito(rs.getString("sito"));
                fornitore.setCitta(rs.getString("citta"));
                fornitore.setNazione(rs.getString("nazione"));
            }
            return fornitore;
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
    public Fornitore findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM fornitore WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Fornitore fornitore = new Fornitore();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                fornitore.setId(rs.getInt("idFornitore"));
                fornitore.setNome(rs.getString("nome"));
                fornitore.setSito(rs.getString("sito"));
                fornitore.setCitta(rs.getString("citta"));
                fornitore.setNazione(rs.getString("nazione"));
            }
            return fornitore;
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
    public int add(Fornitore fornitore) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO fornitore (idFornitore, nome, sito, citta, nazione) VALUES ('" + fornitore.getId() + "','" + fornitore.getNome() + "','" + fornitore.getSito() + "','" + fornitore.getCitta() + "','" + fornitore.getNazione() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(Fornitore fornitore) {
        executor = new DbOperationExecutor();
        sql = "UPDATE fornitore SET idFornitore = '" + fornitore.getId() + "', nome = '" + fornitore.getNome() + "', " +
                " sito = '" + fornitore.getSito() + "', citta = '" + fornitore.getCitta() + "', " +
                " nazione = '" + fornitore.getNazione() + "' WHERE idFornitore = '" + fornitore.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(Fornitore fornitore) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM fornitore WHERE idFornitore = '" + fornitore.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
