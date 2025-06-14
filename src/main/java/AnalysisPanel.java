import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AnalysisPanel extends JPanel {

    private SleepDataService sleepDataService;
    private RandomSleepInformation infoService;
    private String userId;
    private Duration averageDuration;
    private ScreenController screenController;
    private PanelManager panelManager;
    private DailyInfo currentPopup;
    private SleepDataManager sleepDataManager;

    public AnalysisPanel(SleepDataService sleepDataService,
                         RandomSleepInformation infoService,
                         Duration averageDuration,
                         ScreenController screenController,
                         PanelManager panelManager,
                         String userId,
                         SleepDataManager sleepDataManager) {

        this.sleepDataService = sleepDataService;
        this.infoService = infoService;
        this.averageDuration = averageDuration;
        this.screenController = screenController;
        this.panelManager = panelManager;
        this.userId = userId;
        this.sleepDataManager = sleepDataManager;

        setLayout(null);
        setBackground(Color.decode("#565686"));
        setSize(1440, 1024);

        JLabel titleLabel = new JLabel("NEUL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 80));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 1440, 80);
        add(titleLabel);

        // 홈버튼 추가
        ImageIcon originalIcon = new ImageIcon("src/main/resources/images/img.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton homeButton = new JButton(resizedIcon);
        homeButton.setBounds(1375, 30, 32, 32);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeButton.addActionListener(e -> screenController.navigateTo("main"));
        add(homeButton);

        // ---------- Graph Panel ----------
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSleepGraph(g);
            }

            // ✅ 마우스가 그래프 위에 있을 때만 툴팁 표시
            @Override
            public String getToolTipText(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int baseX = 50;
                int baseY = 570;
                int barWidth = 30;
                int space = 70;

                Map<String, Duration> sleepMap = sleepDataService.getWeeklySleepGraph(userId);
                for (int i = 0; i < 7; i++) {
                    int x = baseX + i * space;
                    Duration d = sleepMap.getOrDefault(getDayString(i), Duration.ZERO);
                    int barHeight = (int) Math.round(d.toMinutes() / 2.5);
                    int barTopY = baseY - barHeight;

                    if (mouseX >= x && mouseX <= x + barWidth && mouseY >= barTopY && mouseY <= baseY) {
                        if (!d.isZero()) {
                            long h = d.toHours();
                            long m = d.toMinutesPart();
                            return getDayString(i) + ": " + h + "시간 " + m + "분 수면";
                        } else {
                            return getDayString(i) + ": 수면 기록 없음";
                        }
                    }
                }
                return null;
            }
        };
        graphPanel.setToolTipText("");
        ToolTipManager.sharedInstance().registerComponent(graphPanel);

        graphPanel.setBounds(100, 160, 580, 650);
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setLayout(null);
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(graphPanel);

        JLabel graphTitle = new JLabel("Sleep statistics", SwingConstants.CENTER);
        graphTitle.setFont(new Font("Serif", Font.PLAIN, 35));
        graphTitle.setBounds(0, 40, 580, 40);
        graphPanel.add(graphTitle);

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < days.length; i++) {
            final int index = i;
            JLabel dayLabel = new JLabel(days[i], SwingConstants.CENTER);
            dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            dayLabel.setBounds(45 + i * 70, 110, 60, 20);
            dayLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (currentPopup != null && currentPopup.isDisplayable()) {
                        currentPopup.setVisible(false);
                        currentPopup.dispose();
                        currentPopup = null;
                    }

                    LocalDate today = LocalDate.now();
                    int diffToMonday = today.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
                    LocalDate monday = today.minusDays(diffToMonday);
                    LocalDate date = monday.plusDays(index);

                    SleepRecord record = sleepDataService.getRecordByDate(userId, date);
                    if (record != null) {
                        String hour = String.valueOf(record.getSleepDuration().toHours());
                        String minute = String.valueOf(record.getSleepDuration().toMinutesPart());
                        String mood = record.getMood();
                        String quality = sleepDataManager.getSleepQualityByDate(userId, date);
                        currentPopup = new DailyInfo(date, hour, minute, quality, mood);
                        currentPopup.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, days[index] + "의 기록이 없습니다.");
                    }
                }
            });
            graphPanel.add(dayLabel);
        }

        // ---------- Info Panel ----------
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBounds(760, 160, 580, 650);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        add(infoPanel);

        JLabel infoTitle = new JLabel("Information about sleep");
        infoTitle.setFont(new Font("Serif", Font.PLAIN, 35));
        infoTitle.setBounds(20, 40, 500, 40);
        infoPanel.add(infoTitle);

        JPanel infoBox = new JPanel(null);
        infoBox.setBounds(20, 90, 540, 250);
        infoBox.setBackground(Color.WHITE);
        infoBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(infoBox);

        JTextArea infoArea = new JTextArea();
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(null);

        List<String> goodThings = infoService.getRandomGoodThings();
        List<String> badThings = infoService.getRandomBadThings();
        StringBuilder infoText = new StringBuilder();
        infoText.append("좋은 요소\n");
        for (String item : goodThings) infoText.append("• ").append(item).append("\n");
        infoText.append("\n나쁜 요소\n");
        for (String item : badThings) infoText.append("• ").append(item).append("\n");

        infoArea.setText(infoText.toString());
        infoArea.setBounds(10, 25, 520, 210);
        infoBox.add(infoArea);

        // 감정 코멘트
        JPanel commentBox = new JPanel(null);
        commentBox.setBounds(20, 420, 540, 180);
        commentBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        commentBox.setBackground(Color.WHITE);
        infoPanel.add(commentBox);

        JLabel commentLabel = new JLabel("Comment related to emotion");
        commentLabel.setFont(new Font("Serif", Font.PLAIN, 35));
        commentLabel.setBounds(20, 370, 500, 40);
        infoPanel.add(commentLabel);

        JTextArea commentArea = new JTextArea();
        commentArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        commentArea.setEditable(false);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBackground(null);

        commentArea.setText(infoService.getRandomSleepWord());
        commentArea.setBounds(10, 25, 520, 140);
        commentBox.add(commentArea);
    }

    private void drawSleepGraph(Graphics g) {
        Map<String, Duration> sleepMap = sleepDataService.getWeeklySleepGraph(userId);
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        int baseX = 50;
        int baseY = 570;
        int barWidth = 30;
        int space = 70;

        for (int i = 0; i < days.length; i++) {
            Duration d = sleepMap.getOrDefault(days[i], Duration.ZERO);
            int barHeight = (int) Math.round(d.toMinutes() / 2.5);
            g.setColor(Color.BLACK);
            g.fillRect(baseX + i * space, baseY - barHeight, barWidth, barHeight);
        }

        // 평균선
        g.setColor(new Color(160, 140, 255));
        int avgHeight = (int) (averageDuration.toMinutes() / 2.5);
        int lineY = baseY - avgHeight;
        g.drawLine(baseX, lineY, baseX + days.length * space, lineY);

        long h = averageDuration.toHours();
        long m = averageDuration.toMinutesPart();
        String avgLabel = "평균 (" + h + "시간 " + m + "분)";
        FontMetrics fm = g.getFontMetrics();
        int labelWidth = fm.stringWidth(avgLabel);
        int labelX = baseX + days.length * space - labelWidth;
        int labelY = lineY - 6;
        g.drawString(avgLabel, labelX, labelY);
    }

    private String getDayString(int index) {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        return days[index];
    }
}
