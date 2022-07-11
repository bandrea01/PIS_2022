package Business.Strategy;

import Model.Feedback;

import java.util.Comparator;
import java.util.List;

public class FeedbackRecentiStrategy implements IOrdinamentoFeedbackStrategy {


    @Override
    public void ordina(List<Feedback> feedbacks) {
        //Controllo date non nulle
        feedbacks.sort(new Comparator<Feedback>() {
            @Override
            public int compare(Feedback o1, Feedback o2) {
                if (o2 == null|| o1 == null){
                    return 0;
                }
                return o2.getData().compareTo(o1.getData()); //Comparing date with Java Algorithm
            }
        });

    }
}
