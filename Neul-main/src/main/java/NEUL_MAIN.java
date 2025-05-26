import javax.swing.SwingUtilities;

public class NEUL_MAIN {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SleepDataService dataService = new SleepDataService();
            RandomSleepInformation infoService = new RandomSleepInformation();

            // dummy controller 먼저 생성
            ScreenController controller = new ScreenController(null);

            // PanelManager 생성 시 controller는 일단 전달
            PanelManager manager = new PanelManager(controller, dataService, infoService);

            // controller 내부에 manager 연결
            controller.setMainWindow(manager);  // setMainWindow() 메서드 필요

            // controller 다시 PanelManager에 주입 (선택 사항)
            manager.setController(controller);

            // 이제 controller가 살아 있으니 패널 등록 진행
            manager.registerScreens(dataService, infoService);

            controller.navigateTo("intro");
        });
    }
}
