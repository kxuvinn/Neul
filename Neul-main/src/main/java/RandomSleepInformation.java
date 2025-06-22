import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSleepInformation {
    private final String[] goodThings = {
            "규칙적인 수면 시간", "자기 전 명상", "따뜻한 우유 한 잔",
            "카페인과 니코틴 피하기", "낮에 햇빛 충분히 쬐기"
    };

    private final String[] badThings = {
            "카페인 음료", "알코올 섭취", "취침 전 과한 운동",
            "불규칙한 수면 패턴", "전자기기의 남용", "스트레스와 불안", "신체 활동량 부족"
    };

    private final String[] sleepWords = {
            "잘 자야 마음도 편해져요.", "꿀잠은 좋은 감정의 시작!", "잠은 마음의 휴식 시간이에요.",
            "잠이 부족하면 감정도 불안해져요.", "마음 편히 자면 감정도 안정돼요.",
            "잘 자고 좋은 기분 만들기!", "좋은 잠이 좋은 하루를 만듭니다."
    };

    // 랜덤으로 좋은 요인 3개
    public List<String> getRandomGoodThings() {
        List<String> list = new ArrayList<>(List.of(goodThings));
        Collections.shuffle(list);
        return list.subList(0, Math.min(3, list.size()));
    }

    // 랜덤으로 나쁜 요인 3개
    public List<String> getRandomBadThings() {
        List<String> list = new ArrayList<>(List.of(badThings));
        Collections.shuffle(list);
        return list.subList(0, Math.min(3, list.size()));
    }

    // 랜덤 문구 1개
    public String getRandomSleepWord() {
        int index = (int) (Math.random() * sleepWords.length);
        return sleepWords[index];
    }
}
