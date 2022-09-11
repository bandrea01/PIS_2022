package DAO.Prodotto;

import DAO.Articolo.ArticoloDAO;
import DAO.Produttore.ProduttoreDAO;
import DAO.Prodotto.IProdottoDAO;
import DAO.Servizio.IServizioDAO;
import DAO.Servizio.ServizioDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO implements IProdottoDAO {
    private final static ProdottoDAO instance = new ProdottoDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ProdottoDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ProdottoDAO getInstance(){
        return instance;
    }


    @Override
    public Prodotto findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM prodotto WHERE idProdotto = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        Articolo articolo = articoloDAO.findById(id);
        Prodotto prodotto = new Prodotto();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                prodotto.setId(rs.getInt("idProdotto"));
                prodotto.setName(articolo.getName());
                prodotto.setPrezzo(articolo.getPrezzo());
                prodotto.setDescrizione(articolo.getDescrizione());
                prodotto.setImmagini(articolo.getImmagini());
                prodotto.setProduttore(produttoreDAO.findById(rs.getInt("idProduttore")));
                prodotto.setSottoProdotti(this.getAllSottoProdotti(prodotto));
            }
            return prodotto;
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
    public Prodotto findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM prodotto WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        Articolo articolo = articoloDAO.findByName(name);
        Prodotto prodotto = new Prodotto();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                prodotto.setId(rs.getInt("idServizio"));
                prodotto.setName(articolo.getName());
                prodotto.setPrezzo(articolo.getPrezzo());
                prodotto.setDescrizione(articolo.getDescrizione());
                prodotto.setImmagini(articolo.getImmagini());
                prodotto.setProduttore(produttoreDAO.findById(rs.getInt("idProduttore")));
                prodotto.setSottoProdotti(this.getAllSottoProdotti(prodotto));
            }
            return prodotto;
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
    public ArrayList<Prodotto> findAllByProduttore(Produttore produttore) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM prodotto WHERE idProduttore = '" + produttore.getId() + "';";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        Prodotto prodotto = new Prodotto();
        try {
            while (rs2.next()) {
                if (rs2.getRow() == 0){
                    return null;
                }
                Articolo articolo = articoloDAO.findById(rs2.getInt("idProdotto"));
                prodotto.setId(rs2.getInt("idProdotto"));
                prodotto.setName(articolo.getName());
                prodotto.setPrezzo(articolo.getPrezzo());
                prodotto.setDescrizione(articolo.getDescrizione());
                prodotto.setImmagini(articolo.getImmagini());
                prodotto.setProduttore(produttoreDAO.findById(rs2.getInt("idProduttore")));
                prodotto.setSottoProdotti(this.getAllSottoProdotti(prodotto));
                prodotti.add(prodotto);
            }
            return prodotti;
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
    public int add(Prodotto prodotto) {
        int rowCount;
        sql = "INSERT INTO prodotto (idProdotto, idProduttore) VALUES ('" + prodotto.getId() + "','" + prodotto.getProduttore().getId() + "');";
        executor = new DbOperationExecutor();
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        if (prodotto.getSottoProdotti() != null){
            this.addSottoProdotti(prodotto);
        }
        return rowCount;
    }

    @Override
    public int update(Prodotto prodotto) {
        executor = new DbOperationExecutor();
        sql = "UPDATE prodotto SET idProdotto = '" + prodotto.getId() + "', idProduttore = '" + prodotto.getProduttore().getId() + "' WHERE idProdotto = '" + prodotto.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        if (prodotto.getSottoProdotti() != null){
            this.updateSottoProdotti(prodotto);
        }
        return rowCount;
    }

    @Override
    public int remove(int id) {
        executor = new DbOperationExecutor();
        String sql2 = "DELETE FROM prodotto_has_prodotto WHERE idProdotto = '" + id + "' OR idSottoProdotto = '" + id + "';";
        dbOperation = new WriteOperation(sql2);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        sql = "DELETE FROM prodotto WHERE idProdotto = '" + id + "';";
        dbOperation = new WriteOperation(sql);
        rowCount += executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public ArrayList<Prodotto> getAllSottoProdotti(Prodotto prodotto) {
        String sql1 = "SELECT count(*) AS count FROM prodotto_has_prodotto WHERE idProdotto = '" + prodotto.getId() + "';";
        String sql2 = "SELECT * FROM prodotto_has_prodotto WHERE idProdotto = '" + prodotto.getId() + "';";

        ArrayList<Prodotto> sottoProdotti = new ArrayList<>();
        executor = new DbOperationExecutor();
        IDbOperation readOp1 = new ReadOperation(sql1);
        ResultSet rs1 = executor.executeOperation(readOp1).getResultSet();
        IDbOperation readOp2 = new ReadOperation(sql2);
        ResultSet rs2 = executor.executeOperation(readOp2).getResultSet();

        try {
            rs1.next();
            if (rs1.getInt("count") == 0){
                return null;
            }
            else {
                while (rs2.next()) {
                    Prodotto p = this.findById(rs2.getInt("idSottoProdotto"));
                    sottoProdotti.add(p);
                }
                return sottoProdotti;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(readOp1);
            executor.close(readOp2);
        }
        return null;
    }

    @Override
    public int addSottoProdotti(Prodotto prodotto) {
        int rowCount = 0;
        for (Prodotto p : prodotto.getSottoProdotti()) {
            sql = "INSERT INTO prodotto_has_prodotto (idProdotto, idSottoProdotto) VALUES ('" + prodotto.getId() + "','" + p.getId() + "');";
            executor = new DbOperationExecutor();
            dbOperation = new WriteOperation(sql);
            rowCount += executor.executeOperation(dbOperation).getRowsAffected();
            executor.close(dbOperation);
        }
        return rowCount;
    }

    @Override
    public int updateSottoProdotti(Prodotto prodotto) {
        //Elimino i sottoprodotti del prodotto principale
        int rowCount;
        sql = "DELETE FROM prodotto_has_prodotto WHERE idProdotto = '" + prodotto.getId() + "';";
        executor = new DbOperationExecutor();
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);

        //Aggiungo i sottoprodotti aggiornati del prodotto principale
        rowCount += this.addSottoProdotti(prodotto);
        return rowCount;
    }

    public ArrayList<Prodotto> findAll() {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM prodotto";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        Prodotto prodotto = new Prodotto();
        try {
            while (rs2.next()) {
                if (rs2.getRow() == 0){
                    return null;
                }
                Articolo articolo = articoloDAO.findById(rs2.getInt("idProdotto"));
                prodotto.setId(rs2.getInt("idProdotto"));
                prodotto.setName(articolo.getName());
                prodotto.setPrezzo(articolo.getPrezzo());
                prodotto.setDescrizione(articolo.getDescrizione());
                prodotto.setImmagini(articolo.getImmagini());
                prodotto.setProduttore(produttoreDAO.findById(rs2.getInt("idProduttore")));
                prodotto.setSottoProdotti(this.getAllSottoProdotti(prodotto));
                prodotti.add(prodotto);
            }
            return prodotti;
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
