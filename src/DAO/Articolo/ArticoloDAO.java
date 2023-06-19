package DAO.Articolo;

import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Feedback.FeedbackDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Servizio.ServizioDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;
import Model.Feedback;
import Model.Prodotto;
import Model.Servizio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloDAO implements IArticoloDAO {
    private final static ArticoloDAO instance = new ArticoloDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ArticoloDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ArticoloDAO getInstance(){
        return instance;
    }

    @Override
    public Articolo findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT idArticolo, nome, prezzo, descrizione, idCategoria FROM articolo WHERE idArticolo = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(dbOperation).getResultSet();
        Articolo articolo = new Articolo();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                articolo.setId(rs.getInt("idArticolo"));
                articolo.setName(rs.getString("nome"));
                articolo.setPrezzo(rs.getFloat("prezzo"));
                articolo.setDescrizione(rs.getString("descrizione"));
                articolo.setCategoria(categoriaDAO.findById(rs.getInt("idCategoria")));
                if (isProdotto(articolo.getId())) {
                    articolo.setPathImmagine(ImmagineDAO.getInstance().getPathByIdArticolo(articolo.getId()));
                } else {
                    articolo.setPathImmagine("servizio.png");
                }
            }
            return articolo;
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
    public Articolo findByName(String name) {
        executor = new DbOperationExecutor();
        sql = "SELECT * FROM articolo WHERE nome = '" + name + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Articolo articolo = new Articolo();
        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
        ImmagineDAO immagineDAO = ImmagineDAO.getInstance();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                articolo.setId(rs.getInt("idArticolo"));
                articolo.setName(rs.getString("nome"));
                articolo.setPrezzo(rs.getFloat("prezzo"));
                articolo.setDescrizione(rs.getString("descrizione"));
                articolo.setCategoria(categoriaDAO.findById(rs.getInt("idCategoria")));
                articolo.setImmagini(immagineDAO.findImagesByArticoloId(rs.getInt("idArticolo")));

            }
            return articolo;
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
    public ArrayList<Articolo> findAll() {
        String sql = "SELECT * FROM articolo";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        ResultSet rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Articolo> articoli = new ArrayList<>();
        try {
            while (rs.next()) {
                Articolo articolo = findById(rs.getInt("IdArticolo"));
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
    public boolean isProdotto(int id) {
        String sql = "SELECT count(*) AS count FROM articolo AS A INNER JOIN prodotto AS P ON A.idArticolo = P.idProdotto WHERE A.idArticolo ='" + id + "';";
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

    @Override
    public boolean isServizio(int id) {
        String sql = "SELECT count(*) AS count FROM articolo AS A INNER JOIN servizio AS S ON A.idArticolo = S.idServizio WHERE A.idArticolo ='" + id + "';";
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

    @Override
    public int addProdotto (Prodotto prodotto) {
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        int rowCount;
        sql = "INSERT INTO articolo (idArticolo, nome, prezzo, descrizione, idCategoria) VALUES ('" + prodotto.getId() + "','" + prodotto.getName() + "','" + prodotto.getPrezzo() + "','" + prodotto.getDescrizione() + "','" + prodotto.getCategoria().getId() + "');";
        executor = new DbOperationExecutor();
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        prodottoDAO.add(prodotto);
        return rowCount;
    }

    @Override
    public int addServizio (Servizio servizio) {
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO articolo (idArticolo, nome, prezzo, descrizione, idCategoria) VALUES ('" + servizio.getId() + "','" + servizio.getName() + "','" + servizio.getPrezzo() + "','" + servizio.getDescrizione() + "','" + servizio.getCategoria().getId() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        servizioDAO.add(servizio);
        return rowCount;
    }

    @Override
    public int updateProdotto(Prodotto prodotto) {
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        executor = new DbOperationExecutor();
        sql = "UPDATE articolo SET idArticolo = '" + prodotto.getId() + "', nome = '" + prodotto.getName() + "', " +
                "prezzo = '" + prodotto.getPrezzo() + "'," + " descrizione = '" + prodotto.getDescrizione() + "', " +
                "idCategoria = '" + prodotto.getCategoria().getId() + "' WHERE idArticolo = '" + prodotto.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        prodottoDAO.update(prodotto);
        return rowCount;
    }
    @Override
    public int updateServizio(Servizio servizio) {
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        executor = new DbOperationExecutor();
        sql = "UPDATE articolo SET idArticolo = '" + servizio.getId() + "', nome = '" + servizio.getName() + "', " +
                "prezzo = '" + servizio.getPrezzo() + "'," + " descrizione = '" + servizio.getDescrizione() + "', " +
                "idCategoria = '" + servizio.getCategoria().getId() + "' WHERE idArticolo = '" + servizio.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        servizioDAO.update(servizio);
        return rowCount;
    }

    @Override
    public int removeById(int id) {
        ImmagineDAO.getInstance().removeImagesByArticleId(id);
        ArticoloPuntoVenditaDAO.getInstance().removeArticoloFromAll(findById(id));
        FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Feedback> feedbacks = feedbackDAO.findAllOfArticolo(articoloDAO.findById(id));
        for (Feedback f : feedbacks){
            feedbackDAO.remove(f);
        }
        executor = new DbOperationExecutor();
        sql = "DELETE FROM articolo WHERE idArticolo = '" + id + "';";
        if (this.isProdotto(id)){
            ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
            prodottoDAO.remove(id);
        }
        else{
            ServizioDAO servizioDAO = ServizioDAO.getInstance();
            servizioDAO.remove(id);
        }
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int removeByName(String name) {
        ArticoloPuntoVenditaDAO.getInstance().removeArticoloFromAll(findByName(name));
        FeedbackDAO feedbackDAO = FeedbackDAO.getInstance();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Feedback> feedbacks = feedbackDAO.findAllOfArticolo(articoloDAO.findByName(name));
        for (Feedback f : feedbacks){
            feedbackDAO.remove(f);
        }
        ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();
        ServizioDAO servizioDAO = ServizioDAO.getInstance();
        executor = new DbOperationExecutor();
        sql = "DELETE FROM articolo WHERE nome = '" + name + "';";
        if (this.isProdotto(this.findByName(name).getId())){
            try {
                prodottoDAO.remove(rs.getInt("idArticolo"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                servizioDAO.remove(rs.getInt("idArticolo"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    public boolean articoloExist(int id) {
        String sql = "SELECT count(*) AS count FROM mydb.articolo AS U WHERE U.idArticolo="+id+";";
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

    public boolean articoloNameExist(String nome) {
        String sql = "SELECT count(*) AS count FROM mydb.articolo AS U WHERE U.idArticolo='"+nome+"';";
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
