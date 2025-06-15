import javax.swing.SwingUtilities;

public class NEUL_MAIN {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 데이터를 관리하는 서비스 객체 생성
            SleepDataService dataService = new SleepDataService();
            
            // 수면에 관한 정보를 랜덤으로 제공하는 서비스 객체 생성
            RandomSleepInformation infoService = new RandomSleepInformation();

            // 화면 전환을 담당하는 controller 생성
            ScreenController controller = new ScreenController(null);

            // PanelManager 생성 시 controller, dataService, infoService 전달
            PanelManager manager = new PanelManager(controller, dataService, infoService);

            // controller에 main 화면으로 manager 연결
            controller.setMainWindow(manager); 

            // manager에 controller 연결
            manager.setController(controller);

            // manager에 패널 등록
            manager.registerScreens(dataService, infoService);

            // intro 화면으로 전환
            controller.navigateTo("intro");
        });
    }
}
