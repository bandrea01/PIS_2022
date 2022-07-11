package DAO.Acquirente;


import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.Acquirente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcquirenteDAO implements IAcquirenteDAO {
    private static AcquirenteDAO instance = new AcquirenteDAO();
    private static Acquirente acquirente;
    private static IDbConnection conn;
    private static ResultSet rs;

    private AcquirenteDAO() {
        acquirente = null;
        conn = null;
        rs = null;
    }
    public static AcquirenteDAO getInstance(){
        return instance;
    }

    @Override
    public Acquirente findByUsername(String username) {
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT * FROM acquirente WHERE username = '" + username + "';");
        try {
            rs.next();
            if (rs.getRow() == 1) {
                acquirente = new Acquirente();
                acquirente.setName(rs.getString("nome"));
                acquirente.setSurname(rs.getString("cognome"));
                acquirente.setUsername(rs.getString("username"));
                acquirente.setEmail(rs.getString("email"));
                acquirente.setPwd(rs.getString("password"));
                acquirente.setPhone(rs.getLong("telefono"));
                acquirente.setAge(rs.getInt("età"));
                acquirente.setCity(rs.getString("residenza"));
                acquirente.setJob(rs.getString("professione"));
                return acquirente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            conn.close();
        }
        return null;
    }
    @Override
    public Acquirente findById(int id) {
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT * FROM acquirente WHERE idAcquirente = '" + id + "';");
        try {
            rs.next();
            if (rs.getRow() == 1) {
                acquirente = new Acquirente();
                acquirente.setName(rs.getString("nome"));
                acquirente.setSurname(rs.getString("cognome"));
                acquirente.setUsername(rs.getString("username"));
                acquirente.setEmail(rs.getString("email"));
                acquirente.setPwd(rs.getString("password"));
                acquirente.setPhone(rs.getLong("telefono"));
                acquirente.setAge(rs.getInt("età"));
                acquirente.setCity(rs.getString("residenza"));
                acquirente.setJob(rs.getString("professione"));
                return acquirente;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            conn.close();
        }
        return null;
    }
    @Override
    public ArrayList<Acquirente> findAll() {
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT * FROM acquirente;");
        ArrayList<Acquirente> acquirenti = new ArrayList<>();
        try {
            while (rs.next()) {
                acquirente.setName(rs.getString("nome"));
                acquirente.setSurname(rs.getString("cognome"));
                acquirente.setUsername(rs.getString("username"));
                acquirente.setEmail(rs.getString("email"));
                acquirente.setPwd(rs.getString("password"));
                acquirente.setPhone(rs.getLong("telefono"));
                acquirente.setAge(rs.getInt("età"));
                acquirente.setCity(rs.getString("residenza"));
                acquirente.setJob(rs.getString("professione"));
                acquirenti.add(acquirente);
            }
            return acquirenti;
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
    public int add(Acquirente acquirente) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("INSERT INTO acquirente (nome, cognome, username, email, password, telefono, età, residenza, professione) VALUES ('" + acquirente.getName() + "', '" + acquirente.getSurname() + "','" + acquirente.getUsername() + "','" + acquirente.getEmail() + "','" + acquirente.getPwd() + "','" + acquirente.getPhone() + "','" + acquirente.getAge() + "','" + acquirente.getCity() + "','" + acquirente.getJob() + "');");
        conn.close();
        return rowCount;
    }

    @Override
    public int removeByUsername(String username) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("DELETE FROM acquirente WHERE username = '" + username +"';");
        conn.close();
        return rowCount;
    }
    @Override
    public int removeById(int id) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("DELETE FROM acquirente WHERE idAcquirente = '" + id +"';");
        conn.close();
        return rowCount;
    }

    @Override
    public int update(Acquirente acquirente) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("UPDATE acquirente SET nome = '" + acquirente.getName() + "', cognome = '" + acquirente.getSurname() + "', password = '" + acquirente.getPwd() + "', email = '" + acquirente.getEmail() + "', telefono = '" + acquirente.getPhone() + "', età = '" + acquirente.getAge() + "', residenza = '" + acquirente.getCity() + "', professione = '" + acquirente.getJob() + "' WHERE username = '" + acquirente.getUsername() + "';");
        conn.close();
        return rowCount;
    }

    @Override
    public int getIdByUsername(String username){
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT idAcquirente FROM acquirente WHERE username = '" + username + "';");
        try {
            rs.next();
            if (rs.getRow() == 1) {
                int id = rs.getInt("idAcquirente");
                return id;
            }
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {
            // handle any errors
            System.out.println("Resultset: " + e.getMessage());
        } finally {
            conn.close();
        }
        return 999;
    }
}

