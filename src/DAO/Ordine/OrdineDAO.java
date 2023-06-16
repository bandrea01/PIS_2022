package DAO.Ordine;

import DAO.Prodotto.ProdottoDAO;
import DAO.Servizio.ServizioDAO;
import DAO.Utente.UtenteDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.*;

import java.sql.Date;
import java.sql.PreparedStatement;
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
    public ArrayList<Ordine> findAll() {
        String sql = "SELECT * FROM ordine";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Ordine> ordini = new ArrayList<>();
        try {
            while (rs.next()) {
                Ordine ordine = findOrdineById(rs.getInt("idOrdine"));
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
            executor.close(readOp);
        }
        return null;
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
    public boolean utenteHasBought(Utente utente) {
        String sql = "SELECT count(*) AS count FROM mydb.ordine AS C WHERE C.idUtente=" + utente.getId() + ";";
        IDbOperation readOp = new ReadOperation(sql);
        DbOperationExecutor executor = new DbOperationExecutor();
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() != 0){
                int count = rs.getInt("count");
                return count != 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Ordine> findAllOfUtente(Utente utente) {
        String sql = "SELECT * FROM mydb.ordine WHERE idUtente = " + utente.getId() + ";";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Ordine> ordini = new ArrayList<>();
        try {
            while (rs.next()) {
                Ordine ordine = findOrdineById(rs.getInt("idOrdine"));
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
        Date date = new java.sql.Date(ordine.getDataCreazione().getTime());
        sql = "INSERT INTO ordine (idOrdine, idUtente, date, pagato) VALUES ('" + ordine.getIdOrdine() + "','" + ordine.getUtente().getId() + "','" + date + "','" + ordine.getStatoOrdine() + "');";
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
    public int removeById(int id) {
        executor = new DbOperationExecutor();
        this.removeProdottiOrdine(id);
        this.removeServiziOrdine(id);
        sql = "DELETE FROM ordine WHERE idOrdine = '" + id + "';";
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

    @Override
    public ArrayList<Servizio> findAllServicesBoughtFromUtente(Utente utente) {
        String sql = "SELECT DISTINCT mydb.ordine_has_servizio.idServizio FROM mydb.ordine_has_servizio JOIN mydb.ordine ON mydb.ordine.idOrdine = mydb.ordine_has_servizio.idOrdine where ordine.idUtente =" + utente.getId() + ";";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        ArrayList<Servizio> servizi = new ArrayList<>();
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
            executor.close(readOp);
        }
        return null;
    }

    @Override
    public ArrayList<Prodotto> findAllProductsBoughtFromUtente(Utente utente) {
        String sql = "SELECT DISTINCT mydb.ordine_has_prodotto.idProdotto FROM mydb.ordine_has_prodotto JOIN mydb.ordine ON mydb.ordine.idOrdine = mydb.ordine_has_prodotto.idOrdine where ordine.idUtente =" + utente.getId() + ";";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try {
            while (rs.next()) {
                Prodotto prodotto = prodottoDAO.findById(rs.getInt("idProdotto"));
                prodotti.add(prodotto);
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
            executor.close(readOp);
        }
        return null;
    }
}
