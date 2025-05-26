import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.List;

public class AnalysisPanel extends JPanel {

    private SleepDataService sleepDataService;
    private RandomSleepInformation infoService;
    public String userId = "default";
    private Duration averageDuration;  // SleepCalculator에서 받아온 평균
    private ScreenController screenController;
    private PanelManager panelManager;

    public AnalysisPanel(SleepDataService sleepDataService,
                         RandomSleepInformation infoService,
                         Duration averageDuration,
                         ScreenController screenController,
                         PanelManager panelManager) {

        this.sleepDataService = sleepDataService;
        this.infoService = infoService;
        this.averageDuration = averageDuration;
        this.screenController = screenController;
        this.panelManager = panelManager;

        //setTitle("NEUL - 수면 및 감정 분석");
        setSize(1440, 1024);
        //setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBackground(Color.decode("#565686"));

        JLabel titleLabel = new JLabel("NEUL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 80));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 1440, 80);
        add(titleLabel);

        // Graph Panel (Left)
        JPanel graphPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSleepGraph(g);
            }
        };
        graphPanel.setBounds(80, 150, 600, 750);
        graphPanel.setBackground(Color.WHITE);
        add(graphPanel);

        JLabel graphTitle = new JLabel("Sleep statistics", SwingConstants.CENTER);
        graphTitle.setFont(new Font("Serif", Font.PLAIN, 26));
        graphTitle.setBounds(80, 120, 600, 30);
        graphTitle.setForeground(Color.BLACK);
        add(graphTitle);

        // Info Panel (Right)
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBounds(760, 150, 600, 750);
        infoPanel.setBackground(Color.WHITE);
        add(infoPanel);

        JLabel infoTitle = new JLabel("Information about sleep");
        infoTitle.setFont(new Font("Serif", Font.PLAIN, 26));
        infoTitle.setBounds(20, 10, 500, 40);
        infoPanel.add(infoTitle);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        List<String> goodThings = infoService.getRandomGoodThings();
        List<String> badThings = infoService.getRandomBadThings();

        StringBuilder infoText = new StringBuilder();
        infoText.append("좋은 요소\n");
        infoText.append(String.join(", ", goodThings)).append(" ...\n\n");
        infoText.append("나쁜 요소\n");
        infoText.append(String.join(", ", badThings)).append(" ...");

        infoArea.setText(infoText.toString());

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBounds(20, 60, 550, 120);
        infoPanel.add(scrollPane);

        JLabel commentLabel = new JLabel("Comment related to emotion");
        commentLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        commentLabel.setBounds(20, 200, 500, 40);
        infoPanel.add(commentLabel);

        JTextArea commentArea = new JTextArea();
        commentArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        commentArea.setEditable(false);
        commentArea.setBackground(Color.WHITE);
        commentArea.setBounds(20, 250, 550, 150);
        infoPanel.add(commentArea);

        setVisible(true);
    }

    private void drawSleepGraph(Graphics g) {
        List<Duration> weekly = sleepDataService.getLast7DaysSleepDurations(userId); // 월~일 리스트

        int baseX = 60;
        int baseY = 500;
        int barWidth = 30;
        int space = 60;

        g.setColor(Color.BLACK);
        for (int i = 0; i < weekly.size(); i++) {
            int barHeight = (int) (weekly.get(i).toMinutes() / 10);  // scale
            g.fillRect(baseX + i * (barWidth + space), baseY - barHeight, barWidth, barHeight);
        }

        // 평균선 (SleepCalculator에서 받아온 값 사용)
        g.setColor(new Color(160, 140, 255));
        int avgHeight = (int) (averageDuration.toMinutes() / 10);
        g.drawLine(baseX, baseY - avgHeight, baseX + weekly.size() * (barWidth + space), baseY - avgHeight);
        g.drawString("\uD3C9\uADE0", baseX + weekly.size() * (barWidth + space) + 5, baseY - avgHeight);
    }
}
