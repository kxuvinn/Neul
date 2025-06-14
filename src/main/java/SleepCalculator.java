import java.time.Duration;
import java.util.List;
import java.util.Map;

public class SleepCalculator {

    /**
     * 수면 시간 목록(Duration 리스트)을 받아서 평균 수면 시간을 계산합니다.
     *
     * @param durations 수면 시간 리스트
     * @return 평균 수면 시간 (Duration 형태), 비어있으면 Duration.ZERO 반환
     */
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

    /**
     * Duration을 사람이 읽기 좋은 문자열로 변환합니다. (예: "7시간 30분")
     *
     * @param duration Duration 객체
     * @return 포맷된 문자열
     */
    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart(); // Java 9 이상에서 사용 가능
        return hours + "시간 " + minutes + "분";
    }

    /**
     * 수면 기록 Map<String, Duration>을 기준으로 평균을 계산합니다.
     * (AnalysisPanel 그래프 기준과 동일한 방식)
     *
     * @param sleepMap 요일 → 수면시간 매핑
     * @return 평균 수면 시간 (Duration 형태)
     */
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
