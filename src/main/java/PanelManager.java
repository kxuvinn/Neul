import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelManager extends JFrame{
    private ScreenController controller;
    private CardLayout cardLayout;
    private SleepDataService dataService;
    private RandomSleepInformation infoService;
    private JPanel cardPanel;
    private HashMap<String, JPanel> screens = new HashMap<>();

    public PanelManager(ScreenController controller, SleepDataService dataService, RandomSleepInformation infoService) {
        this.controller = controller;
        this.dataService = dataService;
        this.infoService = infoService;

        // 화면 전환 중앙 통제
        setTitle("Sleep Tracker - NEUL");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // 화면 중앙 정렬

        // CardLayout 기반 화면 전환용 패널 초기화
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);
        setVisible(true);
    }

    public void registerScreens(SleepDataService dataService, RandomSleepInformation infoService) {
        addScreen("intro", new IntroPanel(controller));   // 초기 화면
        addScreen("login", new LoginPanel(controller));   // 로그인 화면
        addScreen("signup", new SignupPanel(controller)); // 회원가입 화면
        addScreen("main", new MainMenuPanel(controller));     // 메인 화면
        addScreen("calculator", new SleepInputPanel(controller)); // 시간계산기 화면
    }

    public void setController(ScreenController controller) {
        this.controller = controller;
    }

    // 화면 등록 메서드
    public void addScreen(String name, JPanel panel){
        if (screens.containsKey(name)) {
            cardPanel.remove(screens.get(name)); // 이미 있는 패널 제거
            screens.remove(name);                // 맵에서도 제거
        }
        screens.put(name,panel);
        cardPanel.add(panel,name);
    }

    // 화면 전환 메서드
    public void showScreen(String name){
        System.out.println("전환 시도: " + name);
        cardLayout.show(cardPanel, name);
    }

    public SleepDataService getSleepDataService() {
        return dataService;
    }

    public RandomSleepInformation getInfoService() {
        return infoService;
    }
}
