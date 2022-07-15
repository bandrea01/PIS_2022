package DbInterface;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        DbUser dbUser = DbUser.getInstance();
        try {
            Class cls = Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Ho caricato la classe: " + cls.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Non ho trovato il driver MySQL JDBC: " + e.getMessage());
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbUser.getSchemaName() + "?serverTimezone=UTC", dbUser.getUserName(), dbUser.getPwd());
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT nome, cognome, residenza, email FROM utente");
            while (rs.next()) {
                System.out.print(rs.getString("nome") + " ");
                System.out.print(rs.getString("cognome") + " ");
                System.out.print(rs.getString("residenza") + " ");
                System.out.println(rs.getString("email") + " ");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    System.out.println("SQL State: " + e.getSQLState());
                    System.out.println("Vendor Error: " + e.getErrorCode());
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    System.out.println("SQL State: " + e.getSQLState());
                    System.out.println("Vendor Error: " + e.getErrorCode());
                }
                stmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("SQL Exception: " + e.getMessage());
                    System.out.println("SQL State: " + e.getSQLState());
                    System.out.println("Vendor Error: " + e.getErrorCode());
                }
            }
        }
    }
}