import java.time.Duration;
import java.time.LocalDate;

public class SleepDataManager {
    private SleepDataService dataService;

    public SleepDataManager(SleepDataService dataService) {
        this.dataService = dataService;
    }

    /**
     * 당일 수면 질 평가
     * @param userId 사용자 ID
     * @return "좋음", "보통", "나쁨" 중 하나
     */
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
