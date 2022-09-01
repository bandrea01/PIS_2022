package DAO.Utente;

import DAO.Feedback.FeedbackDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO implements IUtenteDAO {
    private final static UtenteDAO instance = new UtenteDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    public UtenteDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static UtenteDAO getInstance() {
        return instance;
    }

    @Override
    public Utente findById(int id) {
        sql = "SELECT * FROM utente WHERE idUtente = '" + id + "';";
        executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        Utente utente = new Utente();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                utente = new Utente();
                utente.setId(rs.getInt("idUtente"));
                utente.setName(rs.getString("nome"));
                utente.setSurname(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setAge(rs.getInt("eta"));
                utente.setCity(rs.getString("residenza"));
                utente.setJob(rs.getString("professione"));
                utente.setPhone(rs.getString("telefono"));
            }
            return utente;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(readOp);
        }
        return null;
    }
    @Override
    public Utente findByUsername(String username) {
        String sql = "SELECT * FROM utente WHERE username = '" + username + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        Utente utente = new Utente();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                utente = new Utente();
                utente.setId(rs.getInt("idUtente"));
                utente.setName(rs.getString("nome"));
                utente.setSurname(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setAge(rs.getInt("eta"));
                utente.setCity(rs.getString("residenza"));
                utente.setJob(rs.getString("professione"));
                utente.setPhone(rs.getString("telefono"));
            }
            return utente;
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            executor.close(dbOperation);
        }
        return null;
    }

    public ArrayList<Utente> findAll() {
        String sql = "SELECT * FROM utente";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Utente> utenti = new ArrayList<>();
        try {
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setId(rs.getInt("idUtente"));
                utente.setName(rs.getString("nome"));
                utente.setSurname(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setAge(rs.getInt("eta"));
                utente.setCity(rs.getString("residenza"));
                utente.setJob(rs.getString("professione"));
                utente.setPhone(rs.getString("telefono"));
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
            executor.close(dbOperation);
        }
        return null;
    }

    @Override
    public int addUtente(Utente utente) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO utente (idUtente, nome, cognome, email, username, password, telefono, eta, residenza, professione) VALUES ('" + utente.getId() + "','" + utente.getName() + "','" + utente.getSurname() + "','" + utente.getEmail() + "','" + utente.getUsername() + "','" + utente.getPassword() + "','" + utente.getPhone() + "','" + utente.getAge() + "','" + utente.getCity() + "','" + utente.getJob() + "');";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
    @Override
    public int addManager(Manager manager) {
        executor = new DbOperationExecutor();
        int rowCount;
        String sql1 = "INSERT INTO utente (idUtente, nome, cognome, email, username, password, telefono, eta, residenza, professione) VALUES ('" + manager.getId() + "','" + manager.getName() + "','" + manager.getSurname() + "','" + manager.getEmail() + "','" + manager.getUsername() + "','" + manager.getPassword() + "','" + manager.getPhone() + "','" + manager.getAge() + "','" + manager.getCity() + "','" + manager.getJob() + "');";
        String sql2 = "INSERT INTO manager (idManager) VALUES ('" + manager.getId() + "');";
        IDbOperation readOp1 = new WriteOperation(sql1);
        rowCount = executor.executeOperation(readOp1).getRowsAffected();
        executor.close(readOp1);
        IDbOperation readOp2 = new WriteOperation(sql2);
        rowCount += executor.executeOperation(readOp2).getRowsAffected();
        executor.close(readOp1);

        return rowCount;
    }
    @Override
    public int addAdmin(Amministratore admin) {
        executor = new DbOperationExecutor();
        int rowCount;
        String sql1 = "INSERT INTO utente (idUtente, nome, cognome, email, username, password, telefono, eta, residenza, professione) VALUES ('" + admin.getId() + "','" + admin.getName() + "', '" + admin.getSurname() + "','" + admin.getEmail() + "','" + admin.getUsername() + "','" + admin.getPassword() + "','" + admin.getPhone() + "','" + admin.getAge() + "','" + admin.getCity() + "','" + admin.getJob() + "');";
        String sql2 = "INSERT INTO amministratore (idAdmin) VALUES ('" + admin.getId() + "');";
        IDbOperation writeOp1 = new WriteOperation(sql1);
        rowCount = executor.executeOperation(writeOp1).getRowsAffected();
        executor.close(writeOp1);
        IDbOperation writeOp2 = new WriteOperation(sql2);
        rowCount += executor.executeOperation(writeOp2).getRowsAffected();
        executor.close(writeOp2);

        return rowCount;
    }

    @Override
    public int removeById(int id) {
        String sql1; int rowCount = 0;
        if (isAdmin(findById(id).getUsername())){
            sql1 = "DELETE FROM amministratore WHERE idAdmin = '" + id + "';";
            IDbOperation writeOp1 = new WriteOperation(sql1);
            executor = new DbOperationExecutor();
            rowCount = executor.executeOperation(writeOp1).getRowsAffected();
            executor.close(writeOp1);
        }
        if (isManager(findById(id).getUsername())){
            sql1 = "DELETE FROM manager WHERE idManager = '" + id + "';";
            IDbOperation writeOp1 = new WriteOperation(sql1);
            executor = new DbOperationExecutor();
            rowCount = executor.executeOperation(writeOp1).getRowsAffected();
            executor.close(writeOp1);
        }
        String sql2 = "DELETE FROM utente WHERE idUtente = '" + id + "';";
        IDbOperation writeOp2 = new WriteOperation(sql2);
        rowCount += executor.executeOperation(writeOp2).getRowsAffected();
        executor.close(writeOp2);
        return rowCount;
    }

    @Override
    public int removeByUsername(String username) {
        String sql1; int rowCount = 0;
        if (isAdmin(username)){
            sql1 = "DELETE FROM amministratore WHERE idAdmin = '" + this.findByUsername(username).getId() + "';";
            IDbOperation writeOp1 = new WriteOperation(sql1);
            executor = new DbOperationExecutor();
            rowCount = executor.executeOperation(writeOp1).getRowsAffected();
            executor.close(writeOp1);
        }
        if (isManager(username)){
            sql1 = "DELETE FROM manager WHERE idManager = '" + this.findByUsername(username).getId() + "';";
            IDbOperation writeOp1 = new WriteOperation(sql1);
            executor = new DbOperationExecutor();
            rowCount = executor.executeOperation(writeOp1).getRowsAffected();
            executor.close(writeOp1);
        }
        String sql2 = "DELETE FROM utente WHERE username = '" + username + "';";
        IDbOperation writeOp2 = new WriteOperation(sql2);
        rowCount += executor.executeOperation(writeOp2).getRowsAffected();
        executor.close(writeOp2);
        return rowCount;
    }

    @Override
    public int update(Utente utente) {
        executor = new DbOperationExecutor();
        sql = "UPDATE utente SET nome = '" + utente.getName() + "', cognome = '" + utente.getSurname() + "', email = '" + utente.getEmail() + "'," +
                            " password = '" + utente.getPassword() + "', telefono = '" + utente.getPhone() + "'," +
                            " residenza = '" + utente.getCity() + "', professione = '" + utente.getJob() + "'," +
                            " username = '" + utente.getUsername() + "', eta = '" + utente.getAge() + "' " +
                            " WHERE idUtente = '" + utente.getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public boolean userExist(String username) {
        String sql = "SELECT count(*) AS count FROM mydb.utente AS U WHERE U.username='" + username + "';";
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
        String sql = "SELECT count(*) AS count FROM mydb.utente AS U WHERE U.password ='" + password + "' AND U.username='" + username + "';";
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
        String sql = "SELECT count(*) AS count FROM utente AS U INNER JOIN maanger AS C ON U.idCliente = C.idUtente WHERE U.username='" + username + "';";
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
        String sql = "SELECT count(*) AS count FROM utente AS U INNER JOIN manager AS M ON U.idUtente = M.idManager WHERE U.username='" + username + "';";
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
        String sql = "SELECT count(*) AS count FROM utente AS U INNER JOIN amministratore AS A ON U.idUtente = A.idAdmin WHERE U.username='" + username + "';";
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
    public Manager getManagerByUsername(String name) {
        Manager manager = new Manager();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        String sql = "SELECT * FROM utente AS U INNER JOIN manager AS M ON U.idUtente = M.idManager WHERE U.username = '" + name + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                manager.setId(rs.getInt("idUtente"));
                manager.setName(rs.getString("nome"));
                manager.setSurname(rs.getString("cognome"));
                manager.setEmail(rs.getString("email"));
                manager.setUsername(rs.getString("username"));
                manager.setPassword(rs.getString("password"));
                manager.setAge(rs.getInt("eta"));
                manager.setCity(rs.getString("residenza"));
                manager.setJob(rs.getString("professione"));
                manager.setPhone(rs.getString("telefono"));
                manager.setPuntoVendita(puntoVenditaDAO.findByIdManager(rs.getInt("idUtente")));
                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Manager getManagerById (int id) {
        Manager manager = new Manager();
        PuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
        String sql = "SELECT * FROM utente AS U INNER JOIN manager AS M ON U.idUtente = M.idManager WHERE U.idUtente = '" + id + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                manager.setId(rs.getInt("idUtente"));
                manager.setName(rs.getString("nome"));
                manager.setSurname(rs.getString("cognome"));
                manager.setEmail(rs.getString("email"));
                manager.setUsername(rs.getString("username"));
                manager.setPassword(rs.getString("password"));
                manager.setAge(rs.getInt("eta"));
                manager.setCity(rs.getString("residenza"));
                manager.setJob(rs.getString("professione"));
                manager.setPhone(rs.getString("telefono"));
                manager.setPuntoVendita(puntoVenditaDAO.findByIdManager(rs.getInt("idUtente")));
                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Amministratore getAdminByUsername (String username) {
        Amministratore admin = new Amministratore();
        String sql = "SELECT * FROM utente AS U INNER JOIN amministratore AS M ON U.idUtente = M.idAdmin WHERE U.username = '" + username + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                admin.setId(rs.getInt("idUtente"));
                admin.setName(rs.getString("nome"));
                admin.setSurname(rs.getString("cognome"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setAge(rs.getInt("eta"));
                admin.setCity(rs.getString("residenza"));
                admin.setJob(rs.getString("professione"));
                admin.setPhone(rs.getString("telefono"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Amministratore getAdminById (int id) {
        Amministratore admin = new Amministratore();
        String sql = "SELECT * FROM utente AS U INNER JOIN amministratore AS M ON U.idUtente = M.idAdmin WHERE U.idUtente = '" + id + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        try {
            rs.next();
            if (rs.getRow() == 1) {
                admin.setId(rs.getInt("idUtente"));
                admin.setName(rs.getString("nome"));
                admin.setSurname(rs.getString("cognome"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setAge(rs.getInt("eta"));
                admin.setCity(rs.getString("residenza"));
                admin.setJob(rs.getString("professione"));
                admin.setPhone(rs.getString("telefono"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
