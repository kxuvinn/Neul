// 10조 - 2415028 김수빈
// SleepRecord.java
// 사용자가 TodayPanel에서 입력한 수면 정보와 감정을 날짜에 따라 저장하는 클래스

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Duration;
import java.time.LocalDate;

public class SleepRecord {
    private LocalDate date;
    private Duration sleepDuration;
    private String mood;

    public SleepRecord() {}

    // 오늘 날짜로 수면 기록을 생성하는 생성자
    public SleepRecord(Duration sleepDuration, String mood) {
        this(LocalDate.now(), sleepDuration, mood);
    }

    // 특정 날짜로 수면 기록을 생성하는 생성자
    public SleepRecord(LocalDate date, Duration sleepDuration, String mood) {
        this.date = date;
        this.sleepDuration = sleepDuration;
        this.mood = mood;
    }

    // 날짜 getter
    public LocalDate getDate() {
        return date;
    }

    // 날짜 setter
    public void setDate(LocalDate date) {
        this.date = date;
    }

     // Duration 객체 그대로 반환
    @JsonIgnore
    public Duration getSleepDuration() {
        return sleepDuration;
    }

    // JSON에서 역직렬화 시 Duration 객체를 직접 설정
    @JsonProperty("sleepDuration")
    public void setSleepDurationRaw(Duration duration) {
        this.sleepDuration = duration;
    }

    // Duration을 "X시간 Y분" 형식으로 포맷하여 문자열로 반환 (사용자 표시용)
    @JsonProperty("sleepDuration")
    public String getSleepDurationFormatted() {
        if (sleepDuration == null) return "";
        long hours = sleepDuration.toHours();
        long minutes = sleepDuration.toMinutesPart();
        return String.format("%d시간 %d분", hours, minutes);
    }

    // "X시간 Y분" 형식의 문자열을 Duration으로 파싱하여 설정 (문자열 → Duration)
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

    // 감정 상태 반환
    public String getMood() {
        return mood;
    }

    // 감정 상태 설정
    public void setMood(String mood) {
        this.mood = mood;
    }

    // 수면 기록을 문자열로 보기 좋게 출력
    @Override
    public String toString() {
        return date + " | " + getSleepDurationFormatted() + " | 감정: " + mood;
    }
}
