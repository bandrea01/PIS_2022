package DAO.Ordine;

import DAO.Articolo.ArticoloDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import DAO.Utente.UtenteDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdineDAO implements IOrdineDAO{
    private static OrdineDAO instance = new OrdineDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;
    public OrdineDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static OrdineDAO getInstance() {
        return instance;
    }

    @Override
    public Ordine findOrdineById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM ordine WHERE idOrdine = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Ordine ordine = new Ordine();
        UtenteDAO utenteDAO = UtenteDAO.getInstance();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                ordine.setIdOrdine(id);
                ordine.setUtente(utenteDAO.findById(rs.getInt("idUtente")));
                ordine.setDataCreazione(Date.valueOf(rs.getString("date")));
                ordine.setStatoOrdine(Ordine.StatoOrdine.valueOf(rs.getString("pagato")));
                ArrayList<ProdottoOrdine> prodotti = this.findProdottiByOrdineId(id);
                ArrayList<Servizio> servizi = this.findServiziByOrdineId(id);
                ordine.setProdotti(prodotti);
                ordine.setServizi(servizi);
            }
            return ordine;
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
    public ArrayList<Ordine> findAllOfUtente(Utente utente) {
        String sql = "SELECT * FROM ordine WHERE idUtente = '" + utente.getId() + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Ordine> ordini = new ArrayList<>();
        try {
            while (rs.next()) {
                Ordine ordine = this.findOrdineById(rs.getInt("idOrdine"));
                ordini.add(ordine);
            }
            return ordini;
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
    public ArrayList<ProdottoOrdine> findProdottiByOrdineId(int id) {
        String sql = "SELECT * FROM ordine_has_prodotto WHERE idOrdine = '" + id + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<ProdottoOrdine> prodotti = new ArrayList<>();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        try {
            while (rs.next()) {
                ProdottoOrdine prodottoOrdine = new ProdottoOrdine();
                prodottoOrdine.setProdotto(prodottoDAO.findById(rs.getInt("idProdotto")));
                prodottoOrdine.setQuantita(rs.getInt("quantita"));
                prodotti.add(prodottoOrdine);
            }
            return prodotti;
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
    public ArrayList<Servizio> findServiziByOrdineId(int id) {
        String sql = "SELECT * FROM ordine_has_servizio WHERE idOrdine = '" + id + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Servizio> servizi = new ArrayList<>();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        try {
            while (rs.next()) {
                Servizio servizio = servizioDAO.findById(rs.getInt("idServizio"));
                servizi.add(servizio);
            }
            return servizi;
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
    public boolean isPagato(Ordine ordine) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM ordine WHERE idOrdine = '" + ordine.getIdOrdine() + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        try {
            rs.next();
            return Ordine.StatoOrdine.PAGATA.equals(Ordine.StatoOrdine.valueOf(rs.getString("pagato")));
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return false;
    }

    @Override
    public int paga(Ordine ordine) {
        executor = new DbOperationExecutor();
        sql = "UPDATE ordine SET pagato = '" + Ordine.StatoOrdine.PAGATA + "' WHERE idOrdine = '" + ordine.getIdOrdine() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int add(Ordine ordine) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO ordine (idOrdine, idUtente, date, pagato) VALUES ('" + ordine.getIdOrdine() + "','" + ordine.getUtente().getId() + "','" + ordine.getDataCreazione() + "','" + ordine.getStatoOrdine() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        this.addProdottiOrdine(ordine.getIdOrdine(), ordine.getProdotti());
        this.addServiziOrdine(ordine.getIdOrdine(), ordine.getServizi());
        return rowCount;
    }

    @Override
    public int addProdottiOrdine(int idOrdine, ArrayList<ProdottoOrdine> prodottiOrdine) {
        int rowCount = 0;
        for (ProdottoOrdine p : prodottiOrdine) {
            sql = "INSERT INTO ordine_has_prodotto (idOrdine, idProdotto, quantita) VALUES ('" + idOrdine + "','" + p.getProdotto().getId() + "','" + p.getQuantita() + "');";
            executor = new DbOperationExecutor();
            dbOperation = new WriteOperation(sql);
            rowCount += executor.executeOperation(dbOperation).getRowsAffected();
            executor.close(dbOperation);
        }
        return rowCount;
    }

    @Override
    public int addServiziOrdine(int idOrdine, ArrayList<Servizio> servizi) {
        int rowCount = 0;
        for (Servizio s : servizi) {
            sql = "INSERT INTO ordine_has_servizio (idOrdine, idServizio) VALUES ('" + idOrdine + "','" + s.getId() + "');";
            executor = new DbOperationExecutor();
            dbOperation = new WriteOperation(sql);
            rowCount += executor.executeOperation(dbOperation).getRowsAffected();
            executor.close(dbOperation);
        }
        return rowCount;
    }

    @Override
    public int remove(Ordine ordine) {
        executor = new DbOperationExecutor();
        this.removeProdottiOrdine(ordine.getIdOrdine());
        this.removeServiziOrdine(ordine.getIdOrdine());
        sql = "DELETE FROM ordine WHERE idOrdine = '" + ordine.getIdOrdine() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int removeProdottiOrdine(int idOrdine) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM ordine_has_prodotto WHERE idOrdine = '" + idOrdine + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int removeServiziOrdine(int idOrdine) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM ordine_has_servizio WHERE idOrdine = '" + idOrdine + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
