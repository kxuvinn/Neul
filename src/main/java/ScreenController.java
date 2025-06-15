import javax.swing.*;
import java.time.Duration;
import java.util.Map;

public class ScreenController {
    protected PanelManager mainWindow;
    private PanelManager panelManager;

    public ScreenController(PanelManager mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setMainWindow(PanelManager mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void navigateTo(String screenName) {
        if (mainWindow != null) {
            mainWindow.showScreen(screenName);
        } else {
            System.err.println("mainWindow is null!");
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(mainWindow, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void handleLoginSuccess() {
        String userId = AuthManager.getCurrentUser().getUsername();

        SleepDataService sleepDataService = mainWindow.getSleepDataService();
        SleepDataManager sleepDataManager = new SleepDataManager(sleepDataService);

        // 주간 그래프용 데이터, 평균 계산
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
        navigateTo("main");
    }

    public void handleSignupSuccess() {
        navigateTo("login");
    }

    public void handleLogout() {
        navigateTo("intro");
    }

    public void goToCalculator() {
        navigateTo("calculator");
    }

    public void goToTodayInput() {
        navigateTo("today");
    }

    public void goToAnalysis() {
        navigateTo("analysis");
    }

    public void showSleepRecommendation(SleepTime sleepTime) {
        System.out.println("SleepRecommendationPanel 생성 시작");
        SleepRecommendationPanel panel = new SleepRecommendationPanel(this, sleepTime);
        mainWindow.addScreen("sleepRecommendation", panel);
        System.out.println("패널 등록 완료 -> 전환 시도");
        navigateTo("sleepRecommendation");
    }

    public void showWakeRecommendation(WakeTime wakeTime) {
        WakeRecommendationPanel panel = new WakeRecommendationPanel(this, wakeTime);
        mainWindow.addScreen("wakeRecommendation", panel);
        navigateTo("wakeRecommendation");
    }

    public void showSleepCycle() {
        SleepCyclePanel panel = new SleepCyclePanel(this);
        mainWindow.addScreen("sleepCycle", panel);
        navigateTo("sleepCycle");
    }
}
