// 각 클래스에서 필요한 데이터를 저장/조회하는 클래스

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class SleepDataService {

    private static final String DATA_FILE = "sleep_data.json";
    private Map<String, List<SleepRecord>> userSleepMap;
    private ObjectMapper objectMapper;

    public SleepDataService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        loadData();
    }

    // JSON 파일에서 사용자별 수면 기록 로드
    private void loadData() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                userSleepMap = objectMapper.readValue(file,
                        new TypeReference<Map<String, List<SleepRecord>>>() {});
            } else {
                userSleepMap = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            userSleepMap = new HashMap<>();
        }
    }

    // 수면 기록을 JSON 파일에 저장
    private void saveData() {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DATA_FILE), userSleepMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 오늘 날짜로 수면기록 저장 (중복 날짜는 덮어쓰기)
    public void saveRecord(String userId, SleepRecord record) {
        List<SleepRecord> records = userSleepMap.computeIfAbsent(userId, k -> new ArrayList<>());
        records.removeIf(r -> r.getDate().equals(record.getDate()));
        records.add(record);
        saveData();
    }

    // SleepRecord에서 특정 날짜 기록 조회 -> SleepRecord 클래스 전체로 반환 (RecordPanel 팝업용)
    public SleepRecord getRecordByDate(String userId, LocalDate date) {
        List<SleepRecord> records = userSleepMap.getOrDefault(userId, new ArrayList<>());
        return records.stream()
                .filter(r -> r.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    //  SleepRecord에서 수면 시간 조회 -> sleepDuration 리스트 형태로 반환 (SleepCalculation 평균 수면 시간 계산 용)
    public List<Duration> getLast7DaysSleepDurations(String userId) {
        List<SleepRecord> records = userSleepMap.getOrDefault(userId, new ArrayList<>());
        LocalDate now = LocalDate.now();
        List<Duration> durations = new ArrayList<>();

        records.stream()
                .filter(r -> !r.getDate().isAfter(now) && !r.getDate().isBefore(now.minusDays(6)))
                .sorted(Comparator.comparing(SleepRecord::getDate))
                .forEach(r -> durations.add(r.getSleepDuration()));

        return durations;
    }

    // 평균 계산
    public Duration getWeeklyAverage(String userId) {
        List<Duration> durations = getLast7DaysSleepDurations(userId); // 기존 메서드 재사용!
        return SleepCalculator.calculateAverage(durations);
    }

    // SleepRecord에서 수면 시간 조회 -> 그래프용 요일-수면시간 Map 형태로 반환 (AnalysisPanel 수면시간 그래프 용)
    public Map<String, Duration> getWeeklySleepGraph(String userId) {
        Map<String, Duration> graphData = new LinkedHashMap<>();
        List<SleepRecord> records = userSleepMap.getOrDefault(userId, new ArrayList<>());
        LocalDate now = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = now.minusDays(6 - i);
            String day = targetDate.getDayOfWeek().toString().substring(0, 3); // "MON", "TUE", ...
            Duration duration = records.stream()
                    .filter(r -> r.getDate().equals(targetDate))
                    .map(SleepRecord::getSleepDuration)
                    .findFirst()
                    .orElse(Duration.ZERO);
            graphData.put(day, duration);
        }

        return graphData;
    }

    // SleepRrcord에서 수면 시간 조회 -> duration 객체로 반환 (SleepDataManager 수면의 질 계산 용)
    public Duration getSleepDurationByDate(String userId, LocalDate date) {
        SleepRecord r = getRecordByDate(userId, date);
        return r != null ? r.getSleepDuration() : Duration.ZERO;
    }

    public String getMoodByDate(String userId, LocalDate date) {
        List<SleepRecord> records = userSleepMap.getOrDefault(userId, new ArrayList<>());
        for (SleepRecord record : records) {
            if (record.getDate().equals(date)) {
                return record.getMood();
            }
        }
        return null;
    }
}
