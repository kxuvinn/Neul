import javax.swing.*;

public class ScreenController {
    private final PanelManager panelManager;

    public ScreenController(PanelManager panelManager) {
        this.panelManager = panelManager;
    }

    // 화면 전환 요청 메서드
    public void changeScreen(String screenName) {
        panelManager.showScreen(screenName);
    }

    // 예시: 수면 추천 패널 열기
    public void showSleepRecommendation(SleepTime sleepTime) {
        SleepRecommendationPanel panel = new SleepRecommendationPanel(this, sleepTime);  // ✅ 이 부분은 생성자 수정된 상태일 때만 사용
        panelManager.addScreen("sleepRecommendation", panel);
        changeScreen("sleepRecommendation");
    }

    // 예시: 기상 추천 패널 열기
    public void showWakeRecommendation(WakeTime wakeTime) {
        WakeRecommendationPanel panel = new WakeRecommendationPanel(this, wakeTime);  // ✅ 이 부분도 생성자 수정된 상태일 때만 사용
        panelManager.addScreen("wakeRecommendation", panel);
        changeScreen("wakeRecommendation");
    }

    // 🔧 오류 해결된 부분: CurrentDateTime 기반 SleepCyclePanel
    public void showSleepCycle() {
        CurrentDateTime currentDateTime = new CurrentDateTime();  // ✅ 이 줄 추가
        SleepCyclePanel panel = new SleepCyclePanel(currentDateTime);  // ✅ 고친 부분
        panelManager.addScreen("sleepCycle", panel);
        changeScreen("sleepCycle");
    }

    // 필요시 다른 화면도 여기서 제어 가능
}
