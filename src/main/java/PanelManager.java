import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 전체 패널 관리, CardLayout을 사용하여 화면 전환 담당
public class PanelManager extends JFrame{
    private ScreenController controller;  // 화면 전환을 제어하는 controller
    private CardLayout cardLayout;        // 화면 전환을 위한 cardlayout
    private SleepDataService dataService;  // 데이터 관리 서비스
    private RandomSleepInformation infoService;  // 랜덤 수면 정보 제공 서비스
    private JPanel cardPanel;
    private HashMap<String, JPanel> screens = new HashMap<>();  // 화면 이름과 패널 매핑

    // 생성자
    public PanelManager(ScreenController controller, SleepDataService dataService, RandomSleepInformation infoService) {
        this.controller = controller;
        this.dataService = dataService;
        this.infoService = infoService;

        // 프레임 기본 정보
        setTitle("Sleep Tracker - NEUL");
        setSize(1440, 1024);        // 창 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 종료 버튼
        setLocationRelativeTo(null);    // 화면 중앙 배치

        // CardLayout 기반 화면 전환용 패널 초기화
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);     // 카드 패널 추가
        setVisible(true);   // 창 표시
    }

    // 화면 등록 메서드
    public void registerScreens(SleepDataService dataService, RandomSleepInformation infoService) {
        addScreen("intro", new IntroPanel(controller));   // 초기 화면
        addScreen("login", new LoginPanel(controller));   // 로그인 화면
        addScreen("signup", new SignupPanel(controller)); // 회원가입 화면
        addScreen("main", new MainMenuPanel(controller));     // 메인 화면
        addScreen("calculator", new SleepInputPanel(controller)); // 시간계산기 화면
    }

    // 컨트롤러 재설정
    public void setController(ScreenController controller) {
        this.controller = controller;
    }

    // 화면 등록 메서드
    public void addScreen(String name, JPanel panel){
        if (screens.containsKey(name)) {
            cardPanel.remove(screens.get(name)); // 이미 있는 패널 제거
            screens.remove(name);                // 맵에서도 제거
        }
        screens.put(name,panel);    // 맵에 패널 저장
        cardPanel.add(panel,name);  // 카드 패널에 패널 추가
    }

    // 화면 전환 메서드
    public void showScreen(String name){
        System.out.println("전환 시도: " + name);
        cardLayout.show(cardPanel, name);  // 카드 레이아웃에서 해당 이름 화면 보여주기
    }

    // 수면 데이터 서비스 getter
    public SleepDataService getSleepDataService() {
        return dataService;
    }

    // 랜덤 정보 서비스 getter
    public RandomSleepInformation getInfoService() {
        return infoService;
    }
}
