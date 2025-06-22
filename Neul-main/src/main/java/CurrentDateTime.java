import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CurrentDateTime {
    //현재 시간
    public LocalTime now;
    //MAX_TIME
    public static int TIMES_IN_MINUTE = 90;
    //MAX_CYCLE
    public static int MAX_CYCLES = 6;
    //시간 포맷
    public DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    //현재 시간 출력
    public String getCurrentTime() {
        now = LocalTime.now();
        return now.format(FORMATTER);
    }

    //현재 시간에서 1.5시간 사이클 계산
    public List<String> recommendWakeTimes() {
        now = LocalTime.now();
        List<String> wakeTimes = new ArrayList<>();
        for (int i = 1; i <= MAX_CYCLES; i++) {
            LocalTime wakeTime = now.plusMinutes(i * TIMES_IN_MINUTE);
            wakeTimes.add(wakeTime.format(FORMATTER));
        }
        return wakeTimes;
    }
}
