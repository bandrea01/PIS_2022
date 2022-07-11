package DAO.Utente;

import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.Cliente;
import Model.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO implements IUtenteDAO {
    private static UtenteDAO instance = new UtenteDAO();
    private Utente utente;
    private static IDbConnection conn;
    private static ResultSet rs;


    private UtenteDAO() {
        utente = null;
        conn = null;
        rs = null;
    }

    public static UtenteDAO getInstance() {
        return instance;
    }

    public Utente findByName(String username) {
        String sql = "SELECT nome, cognome, username, password, email FROM utente WHERE username = '" + username + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                utente = new Utente();
                utente.setName(rs.getString("nome"));
                utente.setSurname(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setPwd(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                return utente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Utente> findAll() {
        String sql = "SELECT nome, cognome, username, password, email FROM Utente";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        ArrayList<Utente> utenti = new ArrayList<>();
        try {
            while (rs.next()) {
                utente = new Utente();
                utente.setName(rs.getString("nome"));
                utente.setSurname(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setPwd(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                utenti.add(utente);
            }
            return utenti;
        } catch (SQLException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // Gestisce le differenti categorie d'errore
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            conn.close();
        }
        return null;
    }

    @Override
    public int add(Utente utente) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("INSERT INTO utente (nome, cognome, username, password, email) VALUES ('" + utente.getName() + "', '" + utente.getSurname() + "','" + utente.getUsername() + "','" + utente.getPwd() + "','" + utente.getEmail() + "');");
        conn.close();
        return rowCount;
    }

    @Override
    public int removeByName(String username) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("DELETE FROM utente WHERE Username = '" + username +"';");
        conn.close();
        return rowCount;
    }

    @Override
    public int update(Utente utente) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("UPDATE utente SET Nome = '" + utente.getName() + "', Cognome = '" + utente.getSurname() + "', password = '" + utente.getPwd() + "', email = '" + utente.getEmail() + "';");
        conn.close();
        return rowCount;
    }

    @Override
    public boolean userExist(String username) {
        String sql = "SELECT count(*) AS count FROM myshop.utente AS U WHERE U.username='" + username + "';";
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
    public boolean checkCredentials (String username, String password) {
        String sql = "SELECT count(*) AS count FROM myshop.utente AS U WHERE U.password ='" + password + "' AND U.username='" + username + "';";
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
    public boolean isCliente(String username) {
        String sql = "SELECT count(*) AS count FROM myshop.utente AS U INNER JOIN myshop.cliente AS C ON U.idUtente = C.idUtente WHERE U.username='" + username + "';";
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
    public boolean isManager(String username) {
        String sql = "SELECT count(*) AS count FROM manager AS M WHERE M.username='" + username + "';";
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
    public boolean isAdmin(String username) {
        String sql = "SELECT count(*) AS count FROM amministratore AS A WHERE A.username='" + username + "';";
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
    public Cliente getCliente(String username) {
        Cliente c = new Cliente();
        String sql = "SELECT U.idUtente, U.nome, U.cognome, U.email, U.username, U.password FROM myshop.utente AS U INNER JOIN myshop.cliente AS C ON U.idUtente = C.idUtente WHERE U.username = '" + username + "';";

        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();

        try {
            rs.next();
            if (rs.getRow() == 1) {
                c.setName(rs.getString("nome"));
                c.setSurname(rs.getString("cognome"));
                c.setEmail(rs.getString("email"));
                c.setUsername(rs.getString("username"));
                //c.setCanalePreferito();
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
