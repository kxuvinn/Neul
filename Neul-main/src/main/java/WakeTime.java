import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WakeTime {
    private LocalTime wakeTime;
    private static final int CYCLE_MINUTES = 90;
    private static final int MAX_CYCLES = 6;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public WakeTime(String timeStr) {
        this.wakeTime = LocalTime.parse(timeStr, FORMATTER);
    }

    // 1.5시간 단위로 수면 시간 추천
    public List<String> recommendSleepTimes() {
        List<String> sleepTimes = new ArrayList<>();
        for (int i = 1; i <= MAX_CYCLES; i++) {
            LocalTime sleepTime = wakeTime.minusMinutes(i * CYCLE_MINUTES);
            sleepTimes.add(sleepTime.format(FORMATTER));
        }
        return sleepTimes;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }
}

