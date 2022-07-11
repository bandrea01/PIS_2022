package Business.Strategy;

import Model.Feedback;

import java.util.List;

public class OrdinamentoFeedback {
    private List<Feedback> feedbacks;
    private IOrdinamentoFeedbackStrategy ordinamentoFeedbackStrategy;

    public OrdinamentoFeedback(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void setOrdinamentoFeedbackStrategy(IOrdinamentoFeedbackStrategy ordinamentoFeedbackStrategy) {
        this.ordinamentoFeedbackStrategy = ordinamentoFeedbackStrategy;
    }

    public void ordina() {
        ordinamentoFeedbackStrategy.ordina(feedbacks);
    }
}
