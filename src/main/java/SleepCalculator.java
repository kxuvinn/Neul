import java.time.Duration;
import java.util.List;
import java.util.Map;

public class SleepCalculator {

    public static Duration calculateAverage(List<Duration> durations) {
        if (durations == null || durations.isEmpty()) {
            return Duration.ZERO;
        }

        long totalSeconds = 0;
        int count = 0;

        for (Duration d : durations) {
            if (d != null && !d.isZero()) {
                totalSeconds += d.getSeconds();
                count++;
            }
        }

        if (count == 0) return Duration.ZERO;

        long averageSeconds = Math.round((double) totalSeconds / count);
        return Duration.ofSeconds(averageSeconds);
    }

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart(); // Java 9 이상에서 사용 가능
        return hours + "시간 " + minutes + "분";
    }

    public static Duration calculateAverageFromMap(Map<String, Duration> sleepMap) {
        long totalMinutes = 0;
        int count = 0;

        for (Duration d : sleepMap.values()) {
            if (d != null && !d.isZero()) {
                totalMinutes += d.toMinutes();
                count++;
            }
        }

        if (count == 0) return Duration.ZERO;
        return Duration.ofMinutes(totalMinutes / count);
    }
}
