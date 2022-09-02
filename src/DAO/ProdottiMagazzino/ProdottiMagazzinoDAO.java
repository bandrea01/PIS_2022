package DAO.ProdottiMagazzino;

import DAO.Articolo.ArticoloDAO;
import DAO.Collocazione.CollocazioneDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottiMagazzinoDAO implements IProdottiMagazzinoDAO{

    private static ProdottiMagazzinoDAO instance = new ProdottiMagazzinoDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;
    public ProdottiMagazzinoDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ProdottiMagazzinoDAO getInstance() {
        return instance;
    }


    @Override
    public ArrayList<ProdottiMagazzino> findAllProdottiByMagazzino(Magazzino magazzino) {
        String sql = "SELECT * FROM magazzino_has_prodotto WHERE idMagazzino = '" + magazzino.getId() + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();

        ArrayList<ProdottiMagazzino> listaProdotti = new ArrayList<>();
        try {
            while (rs.next()) {
                ProdottiMagazzino prodottoMagazzino = new ProdottiMagazzino();

                prodottoMagazzino.setMagazzino(magazzino);
                prodottoMagazzino.setProdotto(prodottoDAO.findById(rs.getInt(("idProdotto"))));
                prodottoMagazzino.setCollocazione(collocazioneDAO.findCollocazioneById(rs.getInt("idCollocazione")));
                prodottoMagazzino.setQuantita(rs.getInt("disponibilita"));

                listaProdotti.add(prodottoMagazzino);
            }
            return listaProdotti;
        } catch (SQLException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return null;
    }

    @Override
    public Collocazione findCollocazioneProdotto(Magazzino magazzino, Prodotto prodotto) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM magazzino_has_prodotto WHERE idMagazzino = '" + magazzino.getId() + "' AND idProdotto = '" + prodotto.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        CollocazioneDAO collocazioneDAO = CollocazioneDAO.getInstance();
        Collocazione collocazione = new Collocazione();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                collocazione = collocazioneDAO.findCollocazioneById(rs.getInt("idCollocazione"));
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
    public int findQuantitaProdotto(Magazzino magazzino, Prodotto prodotto) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM magazzino_has_prodotto WHERE idMagazzino = '" + magazzino.getId() + "' AND idProdotto = '" + prodotto.getId() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                return rs.getInt("disponibilita");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return 0;
    }

    @Override
    public int addQuantita(Magazzino magazzino, Prodotto prodotto, int quantita) {
        executor = new DbOperationExecutor();
        sql = "UPDATE magazzino_has_prodotto SET disponibilita = '" + (this.findQuantitaProdotto(magazzino, prodotto) + quantita) + "'" +
                "WHERE idMagazzino = '" + magazzino.getId() + "' AND idProdotto = '" + prodotto.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    //Controllo eccezione nel business
    @Override
    public int reduceQuantita(Magazzino magazzino, Prodotto prodotto, int quantita) {
        executor = new DbOperationExecutor();
        sql = "UPDATE magazzino_has_prodotto SET disponibilita = '" + (this.findQuantitaProdotto(magazzino, prodotto) - quantita) + "'" +
                "WHERE idMagazzino = '" + magazzino.getId() + "' AND idProdotto = '" + prodotto.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }


    @Override
    public int add(ProdottiMagazzino prodottoMagazzino) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO magazzino_has_prodotto (idMagazzino, idProdotto, idCollocazione, disponibilita) VALUES ('" + prodottoMagazzino.getMagazzino().getId() + "','" + prodottoMagazzino.getProdotto().getId() + "','" + prodottoMagazzino.getCollocazione().getIdCollocazione() + "','" + prodottoMagazzino.getQuantita() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(ProdottiMagazzino prodottoMagazzino) {
        executor = new DbOperationExecutor();
        sql = "UPDATE magazzino_has_prodotto SET idProdotto = '" + prodottoMagazzino.getProdotto().getId() + "'," +
                                                "idCollocazione = '" + prodottoMagazzino.getCollocazione().getIdCollocazione() + "', " +
                                                "disponibilita = '" + prodottoMagazzino.getQuantita() + "' " +
                                                "WHERE idMagazzino = '" + prodottoMagazzino.getMagazzino().getId() + "' AND idProdotto = '" + prodottoMagazzino.getProdotto().getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(ProdottiMagazzino prodottoMagazzino) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM magazzino_has_prodotto WHERE idMagazzino = '" + prodottoMagazzino.getMagazzino().getId() + "' AND idProdotto = '" + prodottoMagazzino.getProdotto().getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
