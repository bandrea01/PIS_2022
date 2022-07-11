package DAO.PuntoVendita;


import DbInterface.DbConnection;
import DbInterface.IDbConnection;
import Model.PuntoVendita;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PuntoVenditaDAO implements IPuntoVenditaDAO {
    private static PuntoVenditaDAO instance = new PuntoVenditaDAO();
    private static PuntoVendita puntoVendita;
    private static IDbConnection conn;
    private static ResultSet rs;

    private PuntoVenditaDAO() {
        puntoVendita = null;
        conn = null;
        rs = null;
    }

    public static PuntoVenditaDAO getInstance(){
        return instance;
    }

    @Override
    public ArrayList<PuntoVendita> findAll() {
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT nomePunto, città, idManager, durataIncarico FROM puntovendita");
        ArrayList<PuntoVendita> punti = new ArrayList<>();
        try {
            while (rs.next()) {
                puntoVendita = new PuntoVendita();
                puntoVendita.setName(rs.getString("nomePunto"));
                puntoVendita.setCity(rs.getString("città"));
                puntoVendita.setidMan(rs.getInt("idManager"));
                puntoVendita.setDurata(rs.getInt("durataIncarico"));
                punti.add(puntoVendita);
            }
            return punti;
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
    public PuntoVendita findByName(String name) {
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT nomePunto, città, idManager, durataIncarico FROM puntovendita WHERE nomePunto = '" + name + "'");
        try {
            rs.next();
            if (rs.getRow() == 1) {
                puntoVendita = new PuntoVendita();
                puntoVendita.setName(rs.getString("nomePunto"));
                puntoVendita.setCity(rs.getString("città"));
                puntoVendita.setidMan(rs.getInt("idManager"));
                puntoVendita.setDurata(rs.getInt("durataIncarico"));
                return puntoVendita;
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
    public int add(PuntoVendita puntoVendita) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("INSERT INTO puntovendita (nomePunto, città, idManager, durataIncarico) VALUES ('" + puntoVendita.getName() + "','" + puntoVendita.getCity() + "','" + puntoVendita.getIdMan() + "','" + puntoVendita.getDurata() + "');");
        conn.close();
        return rowCount;
    }

    @Override
    public int removeByName(String name) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("DELETE FROM puntovendita WHERE nomePunto = '" + name +"';");
        conn.close();
        return rowCount;
    }

    @Override
    public int update(PuntoVendita puntoVendita) {
        conn = DbConnection.getInstance();
        int rowCount = conn.executeUpdate("UPDATE puntovendita SET nomePunto = '" + puntoVendita.getName() + "', città = '" + puntoVendita.getCity() + "', idManager = '" + puntoVendita.getIdMan() + "', durataIncarico = '" + puntoVendita.getDurata() + "' WHERE nomePunto = '" + puntoVendita.getName() + "';");
        conn.close();
        return rowCount;
    }

    @Override
    public int getIdByName(String name){
        conn = DbConnection.getInstance();
        rs = conn.executeQuery("SELECT idPuntoVendita FROM puntovendita WHERE nomePunto = '" + name + "';");
        try {
            rs.next();
            if (rs.getRow() == 1) {
                return rs.getInt("idPuntoVendita");
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

