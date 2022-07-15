package Test;

import Business.Strategy.FeedbackMiglioriStrategy;
import Business.Strategy.FeedbackRecentiStrategy;
import Business.Strategy.IOrdinamentoFeedbackStrategy;
import Business.Strategy.OrdinamentoFeedback;
import Model.Feedback;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdinamentoFeedbackTest {
    List<Feedback> feedbacks;

    @Before
    public void setUp() throws Exception{
        //Fixture
        feedbacks = new ArrayList<Feedback>();

        Feedback f1 = new Feedback();
        f1.setIdFeedback(1);
        f1.setGradimento(Feedback.Gradimento.PESSIMO);
        Date data1 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-20");

        Feedback f2 = new Feedback();
        f2.setIdFeedback(2);
        Date data2 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-27");
        f2.setGradimento(Feedback.Gradimento.SCARSO);

        Feedback f3 = new Feedback();
        f3.setIdFeedback(3);
        Date data3 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-22");
        f3.setGradimento(Feedback.Gradimento.ECCELLENTE);

        feedbacks.add(f1);
        feedbacks.add(f2);
        feedbacks.add(f3);
    }
    @After
    public void tearDown() {
        feedbacks = null;
    }

    @Test
    public void ordinaRecentiTest () {
        //Most recent feedbacks
        OrdinamentoFeedback ordinamentoFeedback = new OrdinamentoFeedback(feedbacks);
        //IOrdinamentoFeedbackStrategy strategy = new FeedbackRecentiStrategy();
        //ordinamentoFeedback.setOrdinamentoFeedbackStrategy(strategy);
        ordinamentoFeedback.ordina();

        Assert.assertEquals(feedbacks.get(0).getIdFeedback(), 2);
        Assert.assertEquals(feedbacks.get(1).getIdFeedback(), 3);
        Assert.assertEquals(feedbacks.get(2).getIdFeedback(), 1);
    }

    @Test
    public void ordinaMiglioriTest () {
        OrdinamentoFeedback ordinamentoFeedback = new OrdinamentoFeedback(feedbacks);
        IOrdinamentoFeedbackStrategy strategy = new FeedbackMiglioriStrategy();
        ordinamentoFeedback.setOrdinamentoFeedbackStrategy(strategy);
        ordinamentoFeedback.ordina();

        Assert.assertEquals(feedbacks.get(0).getIdFeedback(), 3);
        Assert.assertEquals(feedbacks.get(1).getIdFeedback(), 2);
        Assert.assertEquals(feedbacks.get(2).getIdFeedback(), 1);
    }

}
