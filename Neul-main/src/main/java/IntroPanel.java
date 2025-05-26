
import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {
    private ScreenController controller;

    public IntroPanel(ScreenController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(91, 89, 153));


        // 중앙 텍스트 + 버튼 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(91, 89, 153));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(300, 0, 0, 0));

        // 부제목
        JLabel subtitle = new JLabel(
                "<html><div style='text-align: center;'>늘 곁에 있는,<br>나만의 감정 수면 파트너</div></html>",
                SwingConstants.CENTER
        );
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 24));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 제목
        JLabel title = new JLabel("NEUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 90));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 버튼
        JButton loginBtn = new JButton("로그인");
        JButton signupBtn = new JButton("회원가입");

        customizeButton(loginBtn);
        customizeButton(signupBtn);

        // 각 버튼 클릭 시 새 창 열기
        loginBtn.addActionListener(e -> {
            controller.changeScreen("login");  // 기존 창 닫고 카드 전환
        });

        signupBtn.addActionListener(e -> {
            controller.changeScreen("signup"); // 기존 창 닫고 카드 전환
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(91, 89, 153));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 패널 구성
        centerPanel.add(subtitle);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(70));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
    }

    //public static void main(String[] args) {
        //JFrame frame = new JFrame("Intro");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(1440, 1024);
        //frame.setLocationRelativeTo(null);
        //frame.add(new IntroPanel());
        //frame.setVisible(true);
    //}
}
//test 용 main임 주석처리하고 사용하기
