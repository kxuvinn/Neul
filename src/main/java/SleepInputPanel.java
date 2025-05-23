import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class SleepInputPanel extends JPanel {

    private ScreenController controller;

    private AnalogClock clock;
    private JLabel digitalClockLabel;
    private Timer timer;

    public SleepInputPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153));

        // 타이틀
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(620, 40, 300, 100);
        add(title);

        // 홈 버튼
        try {
            ImageIcon homeIcon = new ImageIcon(getClass().getClassLoader().getResource("images/img.png"));
            Image scaled = homeIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            JButton homeButton = new JButton(new ImageIcon(scaled));
            homeButton.setBounds(1360, 20, 48, 48);
            homeButton.setBorderPainted(false);
            homeButton.setContentAreaFilled(false);
            homeButton.setFocusPainted(false);
            homeButton.setOpaque(false);
            homeButton.addActionListener(e -> controller.handleLoginSuccess());
            add(homeButton);
        } catch (Exception e) {
            System.err.println("홈 버튼 이미지 로드 실패: " + e.getMessage());
        }

        // 왼쪽 패널: SleepRecommendationPanel
        JPanel sleepAtPanel = new JPanel(null);
        sleepAtPanel.setBackground(Color.WHITE);
        sleepAtPanel.setBounds(230, 180, 320, 600);
        JLabel sleepLabel = new JLabel("I will sleep at..", SwingConstants.CENTER);
        sleepLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sleepLabel.setBounds(30, 20, 260, 40);
        sleepAtPanel.add(sleepLabel);
        JComboBox<String> sleepHour = new JComboBox<>(makeHourArray());
        JComboBox<String> sleepMin = new JComboBox<>(makeMinuteArray());
        sleepHour.setBounds(40, 200, 100, 40);
        sleepMin.setBounds(160, 200, 100, 40);
        sleepAtPanel.add(sleepHour);
        sleepAtPanel.add(sleepMin);
        JButton sleepNext = new JButton("▶ NEXT");
        sleepNext.setBounds(110, 500, 100, 40);
        sleepNext.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sleepNext.addActionListener(e -> {
            String hour = String.format("%02d", Integer.parseInt((String) sleepHour.getSelectedItem()));
            String minute = String.format("%02d", Integer.parseInt((String) sleepMin.getSelectedItem()));
            SleepTime sleepTime = new SleepTime(hour + ":" + minute);
            controller.showSleepRecommendation(sleepTime);
        });
        sleepAtPanel.add(sleepNext);
        add(sleepAtPanel);

        // 가운데 패널: WakeRecommendationPanel
        JPanel wakeUpPanel = new JPanel(null);
        wakeUpPanel.setBackground(Color.WHITE);
        wakeUpPanel.setBounds(580, 180, 320, 600);
        JLabel wakeLabel = new JLabel("I will wake up at..", SwingConstants.CENTER);
        wakeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        wakeLabel.setBounds(30, 20, 260, 40);
        wakeUpPanel.add(wakeLabel);
        JComboBox<String> wakeHour = new JComboBox<>(makeHourArray());
        JComboBox<String> wakeMin = new JComboBox<>(makeMinuteArray());
        wakeHour.setBounds(40, 200, 100, 40);
        wakeMin.setBounds(160, 200, 100, 40);
        wakeUpPanel.add(wakeHour);
        wakeUpPanel.add(wakeMin);
        JButton wakeNext = new JButton("▶ NEXT");
        wakeNext.setBounds(110, 500, 100, 40);
        wakeNext.setFont(new Font("SansSerif", Font.PLAIN, 14));
        wakeNext.addActionListener(e -> {
            String hour = String.format("%02d", Integer.parseInt((String) wakeHour.getSelectedItem()));  // "04"
            String minute = String.format("%02d", Integer.parseInt((String) wakeMin.getSelectedItem())); // "00"
            WakeTime wakeTime = new WakeTime(hour + ":" + minute);  // "04:00"
            controller.showWakeRecommendation(wakeTime);
        });
        wakeUpPanel.add(wakeNext);
        add(wakeUpPanel);

        // 오른쪽 패널: SleepCyclePanel
        JPanel clockPanel = new JPanel(null);
        clockPanel.setBackground(Color.WHITE);
        clockPanel.setBounds(930, 180, 320, 600);
        JLabel clockLabel = new JLabel("Sleep now", SwingConstants.CENTER);
        clockLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        clockLabel.setBounds(30, 20, 260, 40);
        clockPanel.add(clockLabel);

        JPanel clockContainer = new JPanel(new BorderLayout());
        clockContainer.setOpaque(false);
        clockContainer.setBounds(10, 70, 300, 400);

        clock = new AnalogClock();
        digitalClockLabel = new JLabel("", SwingConstants.CENTER);
        digitalClockLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        digitalClockLabel.setForeground(Color.BLACK);

        clockContainer.add(clock, BorderLayout.CENTER);
        clockContainer.add(digitalClockLabel, BorderLayout.SOUTH);
        clockPanel.add(clockContainer);

        JButton playNow = new JButton("▶ NEXT");
        playNow.setBounds(110, 500, 100, 40);
        playNow.setFont(new Font("SansSerif", Font.PLAIN, 14));
        playNow.addActionListener(e -> {
            controller.showSleepCycle();
        });
        clockPanel.add(playNow);

        add(clockPanel);
        startClock();
    }

    private String[] makeHourArray() {
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) hours[i] = String.valueOf(i);
        return hours;
    }

    private String[] makeMinuteArray() {
        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) minutes[i] = String.format("%02d", i);
        return minutes;
    }

    private void startClock() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    clock.repaint();
                    digitalClockLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                });
            }
        }, 0, 1000);
    }

    static class AnalogClock extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 2 - 10;
            int centerX = width / 2;
            int centerY = height / 2;

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            g2.setColor(Color.BLACK);
            g2.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            LocalTime time = LocalTime.now();
            double minute = time.getMinute() * 6;
            double hour = (time.getHour() % 12 + time.getMinute() / 60.0) * 30;

            drawHand(g2, centerX, centerY, hour, radius * 0.5f, 6);
            drawHand(g2, centerX, centerY, minute, radius * 0.75f, 4);
        }

        private void drawHand(Graphics2D g2, int cx, int cy, double angle, double length, int thickness) {
            double rad = Math.toRadians(angle - 90);
            int x = (int) (cx + length * Math.cos(rad));
            int y = (int) (cy + length * Math.sin(rad));
            g2.setStroke(new BasicStroke(thickness));
            g2.drawLine(cx, cy, x, y);
        }
    }
}
