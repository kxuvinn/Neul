// 사용자가 TodayPanel에서 입력한 수면 정보와 감정을 날짜에 따라 저장하는 클래스

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Duration;
import java.time.LocalDate;

public class SleepRecord {
    private LocalDate date;
    private Duration sleepDuration;
    private String mood;

    public SleepRecord() {} // 기본 생성자 (필수)

    // 오늘 날짜로 기록
    public SleepRecord(Duration sleepDuration, String mood) {
        this(LocalDate.now(), sleepDuration, mood);
    }

    // 날짜 지정해서 기록
    public SleepRecord(LocalDate date, Duration sleepDuration, String mood) {
        this.date = date;
        this.sleepDuration = sleepDuration;
        this.mood = mood;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // 내부 로직에서 사용할 Duration 객체 반환용
    @JsonIgnore
    public Duration getSleepDuration() {
        return sleepDuration;
    }

    // 문자열 → Duration 역변환용
    @JsonProperty("sleepDuration")
    public void setSleepDurationRaw(Duration duration) {
        this.sleepDuration = duration;
    }

    // 사용자에게 보여줄 문자열 반환용
    @JsonProperty("sleepDuration")
    public String getSleepDurationFormatted() {
        if (sleepDuration == null) return "";
        long hours = sleepDuration.toHours();
        long minutes = sleepDuration.toMinutesPart();
        return String.format("%d시간 %d분", hours, minutes);
    }

    @JsonProperty("sleepDuration")
    public void setSleepDurationFormatted(String durationStr) {
        try {
            // 예: "2시간 30분" → 시간과 분 추출
            long hours = 0;
            long minutes = 0;
            if (durationStr.contains("시간")) {
                String[] split = durationStr.split("시간");
                hours = Long.parseLong(split[0].trim());
                if (split.length > 1 && split[1].contains("분")) {
                    minutes = Long.parseLong(split[1].replace("분", "").trim());
                }
            } else if (durationStr.contains("분")) {
                minutes = Long.parseLong(durationStr.replace("분", "").trim());
            }
            this.sleepDuration = Duration.ofHours(hours).plusMinutes(minutes);
        } catch (Exception e) {
            // 파싱 실패 시 기본값 설정 (혹은 null 처리)
            this.sleepDuration = Duration.ZERO;
        }
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return date + " | " + getSleepDurationFormatted() + " | 감정: " + mood;
    }
}
