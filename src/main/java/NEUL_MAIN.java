import javax.swing.SwingUtilities;

public class NEUL_MAIN {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SleepDataService dataService = new SleepDataService();
            RandomSleepInformation infoService = new RandomSleepInformation();

            // dummy controller 생성
            ScreenController controller = new ScreenController(null);

            // PanelManager 생성 시 controller 전달
            PanelManager manager = new PanelManager(controller, dataService, infoService);

            // controller 내부에 manager 연결
            controller.setMainWindow(manager); 

            // controller를 PanelManager에
            manager.setController(controller);

            // 패널 등록
            manager.registerScreens(dataService, infoService);

            controller.navigateTo("intro");
        });
    }
}
