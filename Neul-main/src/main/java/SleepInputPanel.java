import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class SleepInputPanel extends JPanel {

    private JComboBox<String> sleepHourCombo;
    private JComboBox<String> sleepMinuteCombo;
    private AnalogClock clock;
    private final JLabel digitalClockLabel;
    private Timer timer;
    private ScreenController controller;

    public SleepInputPanel(ScreenController controller) {
        this.controller = controller;
        setLayout(null);
        setBackground(new Color(91, 89, 153));

        // NEUL 제목
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(620, 40, 300, 100);
        add(title);

        // 홈 버튼
        try {
            ImageIcon homeIcon = new ImageIcon(getClass().getResource("/res/images/img.png"));
            Image scaled = homeIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaled);
            JButton homeButton = new JButton(resizedIcon);
            homeButton.setBounds(1360, 20, 48, 48);
            homeButton.setBorderPainted(false);
            homeButton.setContentAreaFilled(false);
            homeButton.setFocusPainted(false);
            homeButton.setOpaque(false);
            add(homeButton);

            homeButton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(SleepInputPanel.this);
                if (window instanceof JFrame frame) {
                    frame.setContentPane(new MainMenuPanel());
                    frame.revalidate();
                    frame.repaint();
                }
            });
        } catch (Exception e) {
            System.err.println("홈 버튼 이미지 로드 실패: " + e.getMessage());
        }

        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) hours[i] = String.valueOf(i);
        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) minutes[i] = String.format("%02d", i);

        // 수면 입력 패널 (왼쪽)
        JPanel sleepAtPanel = new JPanel();
        sleepAtPanel.setLayout(null);
        sleepAtPanel.setBackground(Color.WHITE);
        sleepAtPanel.setBounds(230, 180, 320, 600);

        JLabel sleepLabel = new JLabel("I will sleep at..", SwingConstants.CENTER);
        sleepLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sleepLabel.setBounds(30, 20, 260, 40);
        sleepAtPanel.add(sleepLabel);

        sleepHourCombo = new JComboBox<>(hours);
        sleepHourCombo.setBounds(40, 200, 100, 40);
        sleepAtPanel.add(sleepHourCombo);

        sleepMinuteCombo = new JComboBox<>(minutes);
        sleepMinuteCombo.setBounds(160, 200, 100, 40);
        sleepAtPanel.add(sleepMinuteCombo);

        JButton sleepNextBtn = new JButton("▶ NEXT");
        sleepNextBtn.setBounds(110, 500, 100, 40);
        sleepNextBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sleepAtPanel.add(sleepNextBtn);

        sleepNextBtn.addActionListener(e -> {
            String hour = (String) sleepHourCombo.getSelectedItem();
            String minute = (String) sleepMinuteCombo.getSelectedItem();
            String timeStr = String.format("%02d:%02d", Integer.parseInt(hour), Integer.parseInt(minute));
            SleepTime sleepTime = new SleepTime(timeStr);

            Window window = SwingUtilities.getWindowAncestor(SleepInputPanel.this);
            if (window instanceof JFrame frame) {
                frame.setContentPane(new SleepRecommendationPanel(sleepTime));
                frame.revalidate();
                frame.repaint();
            }
        });
        add(sleepAtPanel);

        // wake up at 패널 (중간)
        JPanel wakeUpPanel = new JPanel();
        wakeUpPanel.setLayout(null);
        wakeUpPanel.setBackground(Color.WHITE);
        wakeUpPanel.setBounds(580, 180, 320, 600);

        JLabel wakeLabel = new JLabel("I will wake up at..", SwingConstants.CENTER);
        wakeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        wakeLabel.setBounds(30, 20, 260, 40);
        wakeUpPanel.add(wakeLabel);

        JComboBox<String> wakeHourCombo = new JComboBox<>(hours);
        wakeHourCombo.setBounds(40, 200, 100, 40);
        wakeUpPanel.add(wakeHourCombo);

        JComboBox<String> wakeMinuteCombo = new JComboBox<>(minutes);
        wakeMinuteCombo.setBounds(160, 200, 100, 40);
        wakeUpPanel.add(wakeMinuteCombo);

        JButton wakeNextBtn = new JButton("▶ NEXT");
        wakeNextBtn.setBounds(110, 500, 100, 40);
        wakeNextBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        wakeUpPanel.add(wakeNextBtn);

        wakeNextBtn.addActionListener(e -> {
            String hour = (String) wakeHourCombo.getSelectedItem();
            String minute = (String) wakeMinuteCombo.getSelectedItem();
            String timeStr = String.format("%02d:%02d", Integer.parseInt(hour), Integer.parseInt(minute));
            WakeTime wakeTime = new WakeTime(timeStr);

            Window window = SwingUtilities.getWindowAncestor(SleepInputPanel.this);
            if (window instanceof JFrame frame) {
                frame.setContentPane(new WakeRecommendationPanel(wakeTime));
                frame.revalidate();
                frame.repaint();
            }
        });
        add(wakeUpPanel);

        // 시계 패널 (오른쪽)
        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(null);
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
        clockPanel.add(playNow);

        playNow.addActionListener(e -> {
            CurrentDateTime currentTimeBackend = new CurrentDateTime();
            Window window = SwingUtilities.getWindowAncestor(SleepInputPanel.this);
            if (window instanceof JFrame frame) {
                frame.setContentPane(new SleepCyclePanel(currentTimeBackend));
                frame.revalidate();
                frame.repaint();
            }
        });

        add(clockPanel);
        startClock();
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
