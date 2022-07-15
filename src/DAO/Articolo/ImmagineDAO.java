package DAO.Articolo;

import DAO.Prodotto.ProdottoDAO;
import DAO.Servizio.ServizioDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;

import java.io.File;
import java.sql.ResultSet;

public class ImmagineDAO implements IImmagineDAO{
    private final static ImmagineDAO instance = new ImmagineDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ImmagineDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static ImmagineDAO getInstance(){
        return instance;
    }

    @Override
    public int add(int idArticolo, File file) {
        String sql = "INSERT INTO immagine (idArticolo, immagine) VALUES ('" + idArticolo +  "','" + file.getPath() + "');";
        executor = new DbOperationExecutor();
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove(File file) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM immagine WHERE immagine = '" + file.getPath() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
