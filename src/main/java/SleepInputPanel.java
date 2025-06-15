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

        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(620, 100, 400, 100);
        add(title);

        String[] titles = {"I will sleep at..", "I will wake up at..", "sleep now.."};
        int[] xPositions = {230, 580, 930};

        for (int i = 0; i < 3; i++) {
            JPanel roundedPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
            };
            roundedPanel.setLayout(new BorderLayout());
            roundedPanel.setBounds(xPositions[i], 270, 320, 60);
            roundedPanel.setOpaque(false);

            JLabel label = new JLabel(titles[i], SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 22));
            label.setForeground(new Color(44, 33, 87));
            roundedPanel.add(label, BorderLayout.CENTER);
            add(roundedPanel);
        }

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

        String[] hourArray = makeHourArray();
        String[] minuteArray = makeMinuteArray();

        JPanel sleepAtPanel = new JPanel(null);
        sleepAtPanel.setBackground(Color.WHITE);
        sleepAtPanel.setBounds(230, 350, 320, 400);
        sleepAtPanel.add(new JLabel(new ImageIcon(getClass().getResource("/images/sleep2.gif")))).setBounds(75, 50, 170, 170);

        JComboBox<String> sleepHourCombo = new JComboBox<>(hourArray);
        JComboBox<String> sleepMinCombo = new JComboBox<>(minuteArray);
        sleepHourCombo.setFont(new Font("SansSerif", Font.BOLD, 18));
        sleepMinCombo.setFont(new Font("SansSerif", Font.BOLD, 18));
        sleepHourCombo.setBounds(50, 240, 100, 40);
        sleepMinCombo.setBounds(170, 240, 100, 40);
        sleepAtPanel.add(sleepHourCombo);
        sleepAtPanel.add(sleepMinCombo);

        JButton sleepNext = createPlayButton();
        sleepNext.setBounds(110, 330, 100, 40);
        sleepNext.addActionListener(e -> {
            String hour = (String) sleepHourCombo.getSelectedItem();
            String minute = (String) sleepMinCombo.getSelectedItem();
            controller.showSleepRecommendation(new SleepTime(hour + ":" + minute));
        });
        sleepAtPanel.add(sleepNext);
        add(sleepAtPanel);

        JPanel wakeUpPanel = new JPanel(null);
        wakeUpPanel.setBackground(Color.WHITE);
        wakeUpPanel.setBounds(580, 350, 320, 400);
        wakeUpPanel.add(new JLabel(new ImageIcon(getClass().getResource("/images/wake2.gif")))).setBounds(75, 50, 170, 170);

        JComboBox<String> wakeHourCombo = new JComboBox<>(hourArray);
        JComboBox<String> wakeMinCombo = new JComboBox<>(minuteArray);
        wakeHourCombo.setFont(new Font("SansSerif", Font.BOLD, 18));
        wakeMinCombo.setFont(new Font("SansSerif", Font.BOLD, 18));
        wakeHourCombo.setBounds(50, 240, 100, 40);
        wakeMinCombo.setBounds(170, 240, 100, 40);
        wakeUpPanel.add(wakeHourCombo);
        wakeUpPanel.add(wakeMinCombo);

        JButton wakeNext = createPlayButton();
        wakeNext.setBounds(110, 330, 100, 40);
        wakeNext.addActionListener(e -> {
            String hour = (String) wakeHourCombo.getSelectedItem();
            String minute = (String) wakeMinCombo.getSelectedItem();
            controller.showWakeRecommendation(new WakeTime(hour + ":" + minute));
        });
        wakeUpPanel.add(wakeNext);
        add(wakeUpPanel);

        JPanel clockPanel = new JPanel(null);
        clockPanel.setBackground(Color.WHITE);
        clockPanel.setBounds(930, 350, 320, 400);
        JPanel clockContainer = new JPanel(new BorderLayout());
        clockContainer.setOpaque(false);
        clockContainer.setBounds(20, 40, 280, 280);

        clock = new AnalogClock();
        digitalClockLabel = new JLabel("", SwingConstants.CENTER);
        digitalClockLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        digitalClockLabel.setForeground(Color.BLACK);

        clockContainer.add(clock, BorderLayout.CENTER);
        clockContainer.add(digitalClockLabel, BorderLayout.SOUTH);
        clockPanel.add(clockContainer);

        JButton playNow = createPlayButton();
        playNow.setBounds(110, 330, 100, 40);
        playNow.addActionListener(e -> controller.showSleepCycle());
        clockPanel.add(playNow);

        add(clockPanel);
        startClock();
    }

    private JButton createPlayButton() {
        JButton button = new JButton("▶ Play") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x2C2157)); 
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private String[] makeHourArray() {
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) hours[i] = String.format("%02d", i);
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
