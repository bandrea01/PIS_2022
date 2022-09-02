package DAO.Collocazione;

import DAO.PuntoVendita.PuntoVenditaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Collocazione;
import Model.Magazzino;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollocazioneDAO implements ICollocazioneDAO {
    private static CollocazioneDAO instance = new CollocazioneDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;
    public CollocazioneDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static CollocazioneDAO getInstance() {
        return instance;
    }


    @Override
    public Collocazione findCollocazioneById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM collocazione WHERE idCollocazione = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Collocazione collocazione = new Collocazione();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                collocazione.setIdCollocazione(rs.getInt("idCollocazione"));
                collocazione.setCorsia(rs.getInt("corsia"));
                collocazione.setScaffale(rs.getInt("scaffale"));
            }
            return collocazione;
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
    public int add(Collocazione collocazione) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO collocazione (idCollocazione, corsia, scaffale) VALUES ('" + collocazione.getIdCollocazione() + "','" + collocazione.getCorsia() + "','" + collocazione.getScaffale() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(Collocazione collocazione) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM collocazione WHERE idCollocazione = '" + collocazione.getIdCollocazione() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
