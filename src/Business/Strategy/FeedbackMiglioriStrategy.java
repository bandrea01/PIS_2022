package Business.Strategy;

import Model.Feedback;

import java.util.Comparator;
import java.util.List;

public class FeedbackMiglioriStrategy implements IOrdinamentoFeedbackStrategy {
    @Override
    public void ordina(List<Feedback> feedbacks) {
        feedbacks.sort(new Comparator<Feedback>() {
            @Override
            public int compare(Feedback o1, Feedback o2) {
                //Restituisce 0 se o1.punetggio è uguale a o2.punteggio
                if (o1.getGradimento() == o2.getGradimento()) return 0;
                //Restituisce >0 se o1.punetggio è minore a o2.punteggio
                //Restituisce <0 se o1.punetggio è maggiore a o2.punteggio
                int punteggio1 = 0;
                int punteggio2 = 0;
                switch(o1.getGradimento()) {
                    case PESSIMO: punteggio1 = 1; break;
                    case SCARSO: punteggio1 = 2; break;
                    case NORMALE: punteggio1 = 3; break;
                    case BUONO: punteggio1 = 4; break;
                    case ECCELLENTE: punteggio1 = 5; break;
                }
                switch(o2.getGradimento()) {
                    case PESSIMO: punteggio2 = 1; break;
                    case SCARSO: punteggio2 = 2; break;
                    case NORMALE: punteggio2 = 3; break;
                    case BUONO: punteggio2 = 4; break;
                    case ECCELLENTE: punteggio2 = 5; break;
                }
                return punteggio2 - punteggio1;
            }
        });
    }
}
