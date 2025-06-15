import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.*;

public class TodayPanel extends JPanel {
    private ScreenController controller;
    private final User user;

    public TodayPanel(ScreenController controller) {
        this.controller = controller;

        this.user = AuthManager.getCurrentUser();

        if (this.user == null) {
            JOptionPane.showMessageDialog(this, "로그인 정보가 없습니다. 다시 로그인해주세요.");
            controller.navigateTo("login"); 
            return;
        }

        setBackground(Color.decode("#565686"));
        setLayout(null);

        JLabel titleLabel = new JLabel("NEUL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 80));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 80, 1440, 80);
        add(titleLabel);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/img.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // 홈버튼 추가 
        JButton homeButton = new JButton(resizedIcon);
        homeButton.setBounds(1375, 30, 32, 32); 
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        homeButton.addActionListener(e -> {
            controller.navigateTo("main");
        });
        add(homeButton);

        // 왼쪽 패널 
        JPanel sleepPanel = new JPanel(null);
        sleepPanel.setBounds(100, 200, 600, 500);
        sleepPanel.setBackground(Color.WHITE);
        sleepPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(sleepPanel);

        JLabel sleepLabel = new JLabel("오늘의 수면시간 입력", SwingConstants.CENTER);
        sleepLabel.setBounds(150, 70, 300, 50);
        sleepLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        sleepPanel.add(sleepLabel);

        // 수면 시간 입력 
        SpinnerNumberModel hourModel = new SpinnerNumberModel(8, 0, 23, 1);
        JSpinner hourSpinner = new JSpinner(hourModel);
        hourSpinner.setFont(new Font("SansSerif", Font.BOLD, 24));
        hourSpinner.setBounds(150, 200, 100, 60);
        ((JSpinner.DefaultEditor) hourSpinner.getEditor()).getTextField().setEditable(false);
        sleepPanel.add(hourSpinner);

        JLabel colon = new JLabel(":", SwingConstants.CENTER);
        colon.setFont(new Font("SansSerif", Font.BOLD, 28));
        colon.setBounds(260, 200, 20, 60);
        sleepPanel.add(colon);

        SpinnerNumberModel minModel = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner minSpinner = new JSpinner(minModel);
        minSpinner.setFont(new Font("SansSerif", Font.BOLD, 24));
        minSpinner.setBounds(290, 200, 100, 60);
        ((JSpinner.DefaultEditor) minSpinner.getEditor()).getTextField().setEditable(false);
        sleepPanel.add(minSpinner);

        JLabel hourLabel = new JLabel("Hour");
        hourLabel.setBounds(180, 270, 50, 20);
        sleepPanel.add(hourLabel);

        JLabel minLabel = new JLabel("Minute");
        minLabel.setBounds(325, 270, 60, 20);
        sleepPanel.add(minLabel);

        // 오른쪽 패널 
        JPanel moodPanel = new JPanel(null);
        moodPanel.setBounds(740, 200, 600, 500);
        moodPanel.setBackground(Color.WHITE);
        moodPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(moodPanel);

        JLabel moodLabel = new JLabel("오늘의 기분", SwingConstants.CENTER);
        moodLabel.setBounds(200, 70, 200, 30);
        moodLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        moodPanel.add(moodLabel);

        ImageIcon goodIcon = resizeIcon("images/Emotion.png");
        ImageIcon normalIcon = resizeIcon("images/Emotion (1).png");
        ImageIcon badIcon = resizeIcon("images/Emotion (2).png");

        JToggleButton goodBtn = new JToggleButton(goodIcon);
        JToggleButton normalBtn = new JToggleButton(normalIcon);
        JToggleButton badBtn = new JToggleButton(badIcon);

        goodBtn.setBounds(50, 200, 140, 140);
        normalBtn.setBounds(230, 200, 140, 140);
        badBtn.setBounds(410, 200, 140, 140);

        Font moodFont = new Font("SansSerif", Font.BOLD, 18);
        JLabel goodLabel = new JLabel("좋음", SwingConstants.CENTER);
        goodLabel.setBounds(50, 350, 140, 30);
        goodLabel.setFont(moodFont);

        JLabel normalLabel = new JLabel("보통", SwingConstants.CENTER);
        normalLabel.setBounds(230, 350, 140, 30);
        normalLabel.setFont(moodFont);

        JLabel badLabel = new JLabel("나쁨", SwingConstants.CENTER);
        badLabel.setBounds(410, 350, 140, 30);
        badLabel.setFont(moodFont);

        // mood 버튼 event로 구현 
        ButtonGroup moodGroup = new ButtonGroup();
        moodGroup.add(goodBtn);
        moodGroup.add(normalBtn);
        moodGroup.add(badBtn);

        for (JToggleButton btn : new JToggleButton[]{goodBtn, normalBtn, badBtn}) {
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        }

        goodBtn.addActionListener(e -> {
            goodBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            normalBtn.setBorder(BorderFactory.createEmptyBorder());
            badBtn.setBorder(BorderFactory.createEmptyBorder());
        });

        normalBtn.addActionListener(e -> {
            goodBtn.setBorder(BorderFactory.createEmptyBorder());
            normalBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            badBtn.setBorder(BorderFactory.createEmptyBorder());
        });

        badBtn.addActionListener(e -> {
            goodBtn.setBorder(BorderFactory.createEmptyBorder());
            normalBtn.setBorder(BorderFactory.createEmptyBorder());
            badBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        });

        moodPanel.add(goodBtn);
        moodPanel.add(normalBtn);
        moodPanel.add(badBtn);
        moodPanel.add(goodLabel);
        moodPanel.add(normalLabel);
        moodPanel.add(badLabel);

        // 저장 버튼 
        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        saveBtn.setBounds(637, 750, 166, 50);
        saveBtn.setEnabled(true);
        add(saveBtn);

        Action updateSaveState = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                boolean moodSelected = goodBtn.isSelected() || normalBtn.isSelected() || badBtn.isSelected();
                boolean timeValid = true; // 시/분은 항상 유효한 Spinner 값이라 true로 설정
                saveBtn.setEnabled(moodSelected && timeValid);
            }
        };

        goodBtn.addActionListener(updateSaveState);
        normalBtn.addActionListener(updateSaveState);
        badBtn.addActionListener(updateSaveState);

        saveBtn.addActionListener((ActionEvent e) -> {
            int hour = (int) hourSpinner.getValue();
            int minute = (int) minSpinner.getValue();
            Duration duration = Duration.ofHours(hour).plusMinutes(minute);

            String mood = goodBtn.isSelected() ? "좋음" :
                    normalBtn.isSelected() ? "보통" :
                            badBtn.isSelected() ? "나쁨" : null;

            if (mood == null) {
                JOptionPane.showMessageDialog(this, "기분을 선택해주세요.");
                return;
            }

            SleepRecord record = new SleepRecord(duration, mood);
            SleepDataService dataService = new SleepDataService();
            dataService.saveRecord(user.getUsername(), record);

            JOptionPane.showMessageDialog(this, "저장되었습니다!");
        });

        setVisible(true);
    }

    private ImageIcon resizeIcon(String filePath) {
        java.net.URL imageUrl = getClass().getResource("/" + filePath);  // 리소스 경로 불러오기
        if (imageUrl == null) {
            System.err.println("이미지 로딩 실패: " + filePath);
            return null;
        }

        Image img = new ImageIcon(imageUrl).getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
