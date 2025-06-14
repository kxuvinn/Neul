import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SleepRecommendationPanel extends JPanel {
    private ScreenController controller;

    public SleepRecommendationPanel(ScreenController controller, SleepTime sleepTime) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153));

        // 백엔드에서 시간 받아오기
        String currentTime = sleepTime.getSleepTime().format(SleepTime.FORMATTER);
        List<String> recommendedTimes = sleepTime.recommendWakeTimes();

        // NEUL 제목
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(640, 50, 300, 100);
        add(title);

        // 홈 버튼
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/img.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton homeButton = new JButton(resizedIcon);
        homeButton.setBounds(1375, 30, 32, 32); // 오른쪽 상단에 딱 맞게
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        homeButton.addActionListener(e -> {
            controller.navigateTo("main");
        });
        add(homeButton);

        // 현재 시간 패널
        JPanel currentTimePanel = new JPanel(new BorderLayout());
        currentTimePanel.setBackground(Color.WHITE);
        currentTimePanel.setBounds(640, 160, 200, 50);
        JLabel timeText = new JLabel(currentTime, SwingConstants.CENTER);
        timeText.setFont(new Font("Digital-7 Mono", Font.BOLD, 28));
        currentTimePanel.add(timeText, BorderLayout.CENTER);
        add(currentTimePanel);

        // 안내 문구
        JLabel desc = new JLabel("<html><div style='text-align:center;'>You should wake up at:<br>(1 cycle = 1.5hrs)</div></html>", SwingConstants.CENTER);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 18));
        desc.setForeground(Color.WHITE);
        desc.setBounds(600, 250, 300, 60);
        add(desc);

        // 수면 사이클 패널
        int x = 400, y = 380;
        for (int i = 0; i < 6; i++) {
            JPanel cyclePanel = new JPanel();
            cyclePanel.setLayout(new BoxLayout(cyclePanel, BoxLayout.Y_AXIS));
            cyclePanel.setBackground(Color.WHITE);
            cyclePanel.setBounds(x, y, 200, 120);

            JLabel cycleLabel = new JLabel((i + 1) + " cycle" + (i == 0 ? "" : "s"), SwingConstants.CENTER);
            JLabel hourLabel = new JLabel((1.5 * (i + 1)) + " hrs", SwingConstants.CENTER);

            cycleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            hourLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 디지털 시계 스타일 시간 박스
            JPanel digitalPanel = new JPanel();
            digitalPanel.setBackground(Color.WHITE);
            digitalPanel.setMaximumSize(new Dimension(160, 40));
            JLabel timeLabel = new JLabel(recommendedTimes.get(i), SwingConstants.CENTER);
            timeLabel.setFont(new Font("Digital-7 Mono", Font.BOLD, 20));
            timeLabel.setForeground(new Color(66, 133, 244));
            digitalPanel.add(timeLabel);

            digitalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            cyclePanel.add(Box.createVerticalStrut(10));
            cyclePanel.add(cycleLabel);
            cyclePanel.add(hourLabel);
            cyclePanel.add(Box.createVerticalStrut(5));
            cyclePanel.add(digitalPanel);

            Color borderColor = (i < 2) ? Color.RED : (i < 4) ? Color.YELLOW : Color.GREEN;
            cyclePanel.setBorder(BorderFactory.createLineBorder(borderColor, 3));
            add(cyclePanel);

            x += 240;
            if (i == 2) {
                x = 400;
                y += 150;
            }
        }

        // 하단 추천 문구
        JLabel bottomText = new JLabel("We recommend to sleep for 5–6 cycles!!", SwingConstants.CENTER);
        bottomText.setFont(new Font("SansSerif", Font.BOLD, 20));
        bottomText.setForeground(Color.WHITE);
        bottomText.setBounds(440, 700, 600, 50);
        add(bottomText);
    }

    // 테스트용 메인
    //public static void main(String[] args) {
        //JFrame frame = new JFrame("Sleep Cycle Recommendation");
        //frame.setSize(1440, 1024);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);

        // 수면 시간 설정 예: "22:00"
        //SleepTime sleepTime = new SleepTime("22:00");
        //SleepRecommendationPanel panel = new SleepRecommendationPanel();

        //frame.add(panel);
        //frame.setVisible(true);
    //}
}
