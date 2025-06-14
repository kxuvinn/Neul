import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private ScreenController controller;

    public MainMenuPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); // 배경색

        // 상단 NEUL 로고
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(610, 100, 400, 100);
        add(title);

        // 왼쪽 상단 로그아웃 버튼
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(40, 30, 100, 40);
        add(logoutButton);

        // 버튼 공통 스타일
        Font buttonFont = new Font("SansSerif", Font.BOLD, 24);
        Color btnBackground = Color.WHITE;
        int buttonWidth = 300;
        int buttonHeight = 60;
        int x = 580;
        int yStart = 300;
        int gap = 140;

        // Calculator 버튼
        JButton calculatorBtn = new JButton("Calculator");
        calculatorBtn.setFont(buttonFont);
        calculatorBtn.setBackground(btnBackground);
        calculatorBtn.setBounds(x, yStart, buttonWidth, buttonHeight);
        add(calculatorBtn);

        // Today's 버튼
        JButton todayBtn = new JButton("Today's");
        todayBtn.setFont(buttonFont);
        todayBtn.setBackground(btnBackground);
        todayBtn.setBounds(x, yStart + gap, buttonWidth, buttonHeight);
        add(todayBtn);

        // Analysis 버튼
        JButton analysisBtn = new JButton("Analysis");
        analysisBtn.setFont(buttonFont);
        analysisBtn.setBackground(btnBackground);
        analysisBtn.setBounds(x, yStart + 2 * gap, buttonWidth, buttonHeight);
        add(analysisBtn);

        calculatorBtn.addActionListener(e -> controller.goToCalculator());
        todayBtn.addActionListener(e -> controller.goToTodayInput());
        analysisBtn.addActionListener(e -> controller.goToAnalysis());
        logoutButton.addActionListener(e -> controller.handleLogout());
    }

    //public static void main(String[] args) {
        //JFrame frame = new JFrame("Main Menu - NEUL");
        //frame.setSize(1440, 1024);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        //frame.add(new MainMenuPanel());
        //frame.setVisible(true);
    //}

    //테스트용 메인이라 나중에 주석처리하거나 지워야됨
}
