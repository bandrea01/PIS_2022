package DAO.Categoria;

import DbInterface.Command.*;
import Model.Categoria;
import Model.ICategoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAO implements ICategoriaDAO {

    private final static CategoriaDAO instance = new CategoriaDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    private ArrayList<ICategoria> sottoCategorie = new ArrayList<>();

    private CategoriaDAO(){
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static CategoriaDAO getInstance() {
        return instance;
    }


    @Override
    public Categoria findById(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT idCategoria, nome, idCategoriaPadre FROM categoria WHERE idCategoria = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Categoria categoria = new Categoria();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setName(rs.getString("nome"));
                int idPadre = rs.getInt("idCategoriaPadre");
                if (idPadre == 0){
                    categoria.setCategoriaPadre(null);
                }
                else{
                    categoria.setCategoriaPadre(this.findById(idPadre));
                }
                return categoria;
            }
            return null;
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
    public ICategoria findByName(String nome) {
        executor = new DbOperationExecutor();
        sql = "SELECT idCategoria, nome, idCategoriaPadre FROM categoria WHERE nome = '" + nome + "';";
        dbOperation = new ReadOperation(sql);
        rs = executor.executeOperation(dbOperation).getResultSet();
        Categoria categoria = new Categoria();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setName(rs.getString("nome"));
                int idPadre = rs.getInt("idCategoriaPadre");
                if (idPadre == 0){
                    categoria.setCategoriaPadre(null);
                }
                else{
                    categoria.setCategoriaPadre(this.findById(idPadre));
                }
                return categoria;
            }
            return null;
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
    public ArrayList<ICategoria> findAll() {
        executor = new DbOperationExecutor();
        sql = "SELECT idCategoria, nome, idCategoriaPadre FROM categoria;";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        ArrayList<ICategoria> categorie = new ArrayList<>();
        try {
            while (rs2.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs2.getInt("idCategoria"));
                categoria.setName(rs2.getString("nome"));
                int idPadre = rs2.getInt("idCategoriaPadre");
                if (idPadre == 0){
                    categoria.setCategoriaPadre(null);
                }
                else{
                    categoria.setCategoriaPadre(this.findById(idPadre));
                }
                categorie.add(categoria);
            }
            return categorie;
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
    public ArrayList<ICategoria> findAllSottoCategorie(int id) {
        executor = new DbOperationExecutor();
        sql = "SELECT idCategoria, nome, idCategoriaPadre FROM categoria WHERE idCategoriaPadre = '" + id + "';";
        dbOperation = new ReadOperation(sql);
        ResultSet rs2 = executor.executeOperation(dbOperation).getResultSet();
        ArrayList<ICategoria> categorie = new ArrayList<>();
        try {
            while (rs2.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs2.getInt("idCategoria"));
                categoria.setName(rs2.getString("nome"));
                int idPadre = rs2.getInt("idCategoriaPadre");
                if (idPadre == 0){
                    categoria.setCategoriaPadre(null);
                }
                else{
                    categoria.setCategoriaPadre(this.findById(idPadre));
                }
                categorie.add(categoria);
            }
            return categorie;
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
    public int add(ICategoria categoria) {
        executor = new DbOperationExecutor();
        int rowCount;
        if (categoria.getCategoriaPadre() == null) {
            sql = "INSERT INTO categoria (idCategoria, nome, idCategoriaPadre) VALUES ('" + categoria.getId() + "','" + categoria.getName() + "','0');";
        } else {
            sql = "INSERT INTO categoria (idCategoria, nome, idCategoriaPadre) VALUES ('" + categoria.getId() + "','" + categoria.getName() + "','" + categoria.getCategoriaPadre().getId() + "');";
        }
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int remove (ICategoria categoria) {
        executor = new DbOperationExecutor();
        sql = "DELETE FROM categoria WHERE idCategoria = '" + categoria.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(ICategoria categoria) {
        executor = new DbOperationExecutor();
        if (categoria.getCategoriaPadre() == null) {
            sql = "UPDATE categoria SET nome = '" + categoria.getName() + "', idCategoriaPadre = 0 WHERE idCategoria = '" + categoria.getId() + "';";
        } else {
            sql = "UPDATE categoria SET nome = '" + categoria.getName() + "', idCategoriaPadre =" + categoria.getCategoriaPadre().getId() + " WHERE idCategoria = '" + categoria.getId() + "';";
        }
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int findId(String categoria) {
        String sql = "SELECT idCategoria FROM mydb.categoria WHERE nome='"+categoria+"';";
        IDbOperation readOp = new ReadOperation(sql);
        DbOperationExecutor executor = new DbOperationExecutor();
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1){
                int id = rs.getInt("idCategoria");
                return id;
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean categoriaExist(String nome) {
        String sql = "SELECT count(*) AS count FROM mydb.categoria AS U WHERE U.nome='"+nome+"';";
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
