import javax.swing.*;
import java.time.Duration;
import java.util.Map;

// 화면 전환, 오류 처리, 이벤트 처리 등을 담당하는 컨트롤러
public class ScreenController {
    protected PanelManager mainWindow;
    private PanelManager panelManager;

    // 생성자
    public ScreenController(PanelManager mainWindow) {
        this.mainWindow = mainWindow;
    }

    // mainWindow 세팅
    public void setMainWindow(PanelManager mainWindow) {
        this.mainWindow = mainWindow;
    }

    // 화면 이름에 해당하는 화면으로 전환
    public void navigateTo(String screenName) {
        if (mainWindow != null) {
            mainWindow.showScreen(screenName);  // 화면 전환
        } else {
            System.err.println("mainWindow is null!");  // 오류 메시지 출력
        }
    }

    // 오류 메시지 표시
    public void showError(String message) {
        JOptionPane.showMessageDialog(mainWindow, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // 로그인 성공 시
    public void handleLoginSuccess() {
        String userId = AuthManager.getCurrentUser().getUsername(); // 로그인된 사용자 ID 가져오기

        SleepDataService sleepDataService = mainWindow.getSleepDataService();  // 데이터 서비스 가져오기
        SleepDataManager sleepDataManager = new SleepDataManager(sleepDataService); // sleepdatamanager 생성

        // 주간 그래프용 데이터, 평균 수면 시간 계산
        Map<String, Duration> graphData = sleepDataService.getWeeklySleepGraph(userId);
        Duration average = SleepCalculator.calculateAverageFromMap(graphData);

        // Today Panel
        TodayPanel todayPanel = new TodayPanel(this);
        mainWindow.addScreen("today", todayPanel);

        // Analysis Panel
        AnalysisPanel panel = new AnalysisPanel(
                sleepDataService,
                mainWindow.getInfoService(),
                average,
                this,
                panelManager,
                userId,
                sleepDataManager
        );

        mainWindow.addScreen("analysis", panel);

        // 메인 화면으로 이동
        navigateTo("main");
    }

    // 회원가입 성공 시 로그인 화면으로 이동
    public void handleSignupSuccess() {
        navigateTo("login");
    }

    // 로그아웃 시 인트로 화면으로 이동
    public void handleLogout() {
        navigateTo("intro");
    }

    // calculator 화면으로 이동
    public void goToCalculator() {
        navigateTo("calculator");
    }

    // today 화면으로 이동
    public void goToTodayInput() {
        navigateTo("today");
    }

     // analysis 화면으로 이동
    public void goToAnalysis() {
        navigateTo("analysis");
    }

    // showSleepRecommendation 화면 
    public void showSleepRecommendation(SleepTime sleepTime) {
        System.out.println("SleepRecommendationPanel 생성 시작");
        SleepRecommendationPanel panel = new SleepRecommendationPanel(this, sleepTime);
        mainWindow.addScreen("sleepRecommendation", panel);
        System.out.println("패널 등록 완료 -> 전환 시도");
        navigateTo("sleepRecommendation");
    }

    // showWakeRecommendation 화면
    public void showWakeRecommendation(WakeTime wakeTime) {
        WakeRecommendationPanel panel = new WakeRecommendationPanel(this, wakeTime);
        mainWindow.addScreen("wakeRecommendation", panel);
        navigateTo("wakeRecommendation");
    }

     // showSleepCycle 화면
    public void showSleepCycle() {
        SleepCyclePanel panel = new SleepCyclePanel(this);
        mainWindow.addScreen("sleepCycle", panel);
        navigateTo("sleepCycle");
    }
}
