import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SleepTime {
    private LocalTime sleepTime;
    private static final int CYCLE_MINUTES = 90;
    private static final int MAX_CYCLES = 6;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public SleepTime(String timeStr) {
        this.sleepTime = LocalTime.parse(timeStr, FORMATTER);
    }

    // 1.5시간 단위로 기상 시간 추천
    public List<String> recommendWakeTimes() {
        List<String> wakeTimes = new ArrayList<>();
        for (int i = 1; i <= MAX_CYCLES; i++) {
            LocalTime wakeTime = sleepTime.plusMinutes(i * CYCLE_MINUTES);
            wakeTimes.add(wakeTime.format(FORMATTER));
        }
        return wakeTimes;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }
}

