// 사용자 정보가 앱 재시작 후에도 유지되도록 sleep_data.json을 관리하는 통합 클래스

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SleepDatabase {

    private SleepDataService dataService;

    public SleepDatabase() {
        this.dataService = new SleepDataService();
    }

    // 날짜 지정 기록
    public void addRecord(String userId, LocalDate date, Duration sleepDuration, String mood) {
        SleepRecord record = new SleepRecord(date, sleepDuration, mood);
        dataService.saveRecord(userId, record);
    }

    // 오늘 날짜 자동 기록 (메서도 오버로딩)
    public void addRecord(String userId, Duration sleepDuration, String mood) {
        addRecord(userId, LocalDate.now(), sleepDuration, mood);
    }

    // 특정 날짜 기록 조회
    public SleepRecord getRecordByDate(String userId, LocalDate date) {
        return dataService.getRecordByDate(userId, date);
    }

    // 최근 7일 수면 시간 리스트 반환 → SleepCalculator 용도
    public List<Duration> getLast7DaysDurations(String userId) {
        return dataService.getLast7DaysSleepDurations(userId);
    }

    // 날짜별 수면 시간 → SleepDataManager 용도
    public Duration getDurationByDate(String userId, LocalDate date) {
        return dataService.getSleepDurationByDate(userId, date);
    }

    // 날짜별 감정 상태 → 이모티콘 표시용
    public String getMoodByDate(String userId, LocalDate date) {
        return dataService.getMoodByDate(userId, date);
    }

    // AnalysisPanel 그래프용 수면시간 데이터
    public Map<String, Duration> getWeeklyGraph(String userId) {
        return dataService.getWeeklySleepGraph(userId);
    }
}
