package Business;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmailSessionManager {
    private LocalDateTime startTime;
    private int durataMinuti;

    public EmailSessionManager(int durataMinuti) {
        this.startTime = LocalDateTime.now();
        this.durataMinuti = durataMinuti;
    }

    public boolean isScaduta() {
        LocalDateTime endTime = startTime.plus(durataMinuti, ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endTime);
    }
}