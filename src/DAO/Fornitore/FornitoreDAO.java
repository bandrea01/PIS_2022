package DAO.Fornitore;

import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Categoria;
import Model.Fornitore;
import Model.ICategoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override
    public ArrayList<Fornitore> findAll() {
        executor = new DbOperationExecutor();
        sql = "SELECT idFornitore, nome, sito, citta, nazione FROM fornitore;";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        ArrayList<Fornitore> fornitori = new ArrayList<>();
        try {
            while (rs2.next()) {
                Fornitore fornitore = new Fornitore();
                fornitore.setId(rs2.getInt("idFornitore"));
                fornitore.setNome(rs2.getString("nome"));
                fornitore.setSito(rs2.getString("sito"));
                fornitore.setCitta(rs2.getString("citta"));
                fornitore.setNazione(rs2.getString("nazione"));
                fornitori.add(fornitore);
            }
            return fornitori;
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

    public boolean fornitoreExist(int id) {
        String sql = "SELECT count(*) AS count FROM mydb.fornitore AS U WHERE U.idFornitore="+id+";";
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
