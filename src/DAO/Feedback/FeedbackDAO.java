package DAO.Feedback;

import DAO.Articolo.ArticoloDAO;
import DAO.Utente.IUtenteDAO;
import DAO.Utente.UtenteDAO;
import DbInterface.Command.DbOperationExecutor;
import DbInterface.Command.IDbOperation;
import DbInterface.Command.ReadOperation;
import DbInterface.Command.WriteOperation;
import Model.Articolo;
import Model.Feedback;
import Model.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO implements IFeedbackDAO{
    private static FeedbackDAO instance = new FeedbackDAO();

    private ResultSet rs;
    private DbOperationExecutor executor;
    private IDbOperation dbOperation;
    private String sql;

    UtenteDAO utenteDAO = UtenteDAO.getInstance();

    public FeedbackDAO() {
        this.rs = null;
        this.dbOperation = null;
        this.executor = null;
        this.sql = null;
    }

    public static FeedbackDAO getInstance() {
        return instance;
    }

    @Override
    public List<Feedback> findAllOfArticolo (Articolo articolo) {
        String sql = "SELECT * FROM feedback WHERE idArticolo = '" + articolo.getId() + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        try {
            while(rs.next()){
                Feedback feedback = new Feedback();
                feedback.setIdFeedback(rs.getInt("idFeedback"));
                feedback.setArticolo(articolo);
                feedback.setUtente(utenteDAO.findById(rs.getInt("idUtente")));
                feedback.setCommento(rs.getString("commento"));
                feedback.setGradimento(Feedback.Gradimento.valueOf(rs.getString("gradimento")));
                feedback.setRisposta(rs.getString("risposta"));
                feedback.setManager(utenteDAO.getManagerById(rs.getInt("idManager")));
                feedbacks.add(feedback);
            }
            return feedbacks;
        } catch (SQLException e) {

            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Feedback> findAllOfUtente(Utente utente) {
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        String sql = "SELECT * FROM feedback WHERE idUtente = '" + utente.getId() + "';";
        DbOperationExecutor executor = new DbOperationExecutor();
        IDbOperation readOp = new ReadOperation(sql);
        rs = executor.executeOperation(readOp).getResultSet();
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        try {
            while(rs.next()){
                Feedback feedback = new Feedback();
                feedback.setIdFeedback(rs.getInt("idFeedback"));
                feedback.setArticolo(articoloDAO.findById(rs.getInt("idArticolo")));
                feedback.setUtente(utente);
                feedback.setCommento(rs.getString("commento"));
                feedback.setGradimento(Feedback.Gradimento.valueOf(rs.getString("gradimento")));
                feedback.setRisposta(rs.getString("risposta"));
                feedback.setManager(utenteDAO.getManagerById(rs.getInt("idManager")));
                feedbacks.add(feedback);
            }
            return feedbacks;
        } catch (SQLException e) {

            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (NullPointerException e) {

            System.out.println("Resultset: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int add(Feedback feedback) {
        executor = new DbOperationExecutor();
        int rowCount;
        sql = "INSERT INTO feedback (idFeedback, idUtente, idArticolo, commento, gradimento, risposta, idManager) VALUES ('" + feedback.getIdFeedback() + "', '" + feedback.getUtente().getId() + "','" + feedback.getArticolo().getId() + "','" + "','" + feedback.getCommento() + "," + feedback.getGradimento() + "','" + feedback.getRisposta() + "','" + feedback.getManager().getId() + "';";
        dbOperation = new WriteOperation(sql);
        rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }

    @Override
    public int update(Feedback feedback) {
        executor = new DbOperationExecutor();
        sql = "UPDATE feedback SET idFeedback = '" + feedback.getIdFeedback() + "', idArticolo = '" + feedback.getArticolo().getId() +
                "', idUtente = '" + feedback.getUtente().getId() + "'," + " commento = '" + feedback.getCommento() +
                "', gradimento = '" + feedback.getGradimento() + "'," + " risposta = '" + feedback.getRisposta() +
                "', idManager = '" + feedback.getManager().getId() + "';";
        dbOperation = new WriteOperation(sql);
        int rowCount = executor.executeOperation(dbOperation).getRowsAffected();
        executor.close(dbOperation);
        return rowCount;
    }
}
