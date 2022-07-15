package DbInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDbConnection {
    public static void main(String[] args) {
        IDbConnection conn = DbConnection.getInstance();
        try{
            ResultSet rs = conn.executeQuery("SELECT nome, cognome, residenza, email FROM utente");
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
            conn.close();
        }
    }
}
