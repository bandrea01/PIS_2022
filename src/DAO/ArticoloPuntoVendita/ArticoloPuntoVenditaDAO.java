package DAO.ArticoloPuntoVendita;

import DAO.Articolo.ArticoloDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;
import Model.PuntoVendita;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloPuntoVenditaDAO implements IArticoloPuntoVenditaDAO {
    private static ArticoloPuntoVenditaDAO instance = new ArticoloPuntoVenditaDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    public ArticoloPuntoVenditaDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ArticoloPuntoVenditaDAO getInstance() {
        return instance;
    }


    @Override
    public ArrayList<Articolo> findByPunto(PuntoVendita puntoVendita) {
        String sql = "SELECT * FROM puntovendita_has_articolo WHERE idPuntoVendita = '" + puntoVendita.getIdPuntoVendita() + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Articolo> articoli = new ArrayList<>();
        try {
            while (rs.next()) {
                Articolo articolo = articoloDAO.findById(rs.getInt("IdArticolo"));
                articoli.add(articolo);
            }
            return articoli;
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
    public int add(Articolo articolo, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO puntovendita_has_articolo (idArticolo, idPuntoVendita) VALUES ('" + articolo.getId() + "','" + puntoVendita.getIdPuntoVendita() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove (Articolo articolo, PuntoVendita puntoVendita) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM puntovendita_has_articolo WHERE idArticolo = '" + articolo.getId() + "' AND idPuntoVendita = '" + puntoVendita.getIdPuntoVendita() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int removeByPunto(PuntoVendita puntoVendita){
        executor = new DbOperationExecutor();
        sql = "DELETE FROM puntovendita_has_articolo WHERE idPuntoVendita = '" + puntoVendita.getIdPuntoVendita() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
