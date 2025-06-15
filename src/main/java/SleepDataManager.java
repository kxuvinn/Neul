import java.time.Duration;
import java.time.LocalDate;

public class SleepDataManager {
    private SleepDataService dataService;

    public SleepDataManager(SleepDataService dataService) {
        this.dataService = dataService;
    }

    public String getSleepQualityByDate(String userId, LocalDate date) {
        Duration sleepDuration = dataService.getSleepDurationByDate(userId, date);
        long totalMinutes = sleepDuration.toMinutes();

        if (totalMinutes >= 420) { // 7시간 이상
            return "좋음";
        } else if (totalMinutes >= 360) { // 6시간 이상 7시간 미만
            return "보통";
        } else {
            return "나쁨";
        }
    }
}
