import javax.swing.*;
import java.awt.*;


public class IntroPanel extends JPanel {

    private ScreenController controller;

    public IntroPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(91, 89, 153)); // #5b5999

     
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(91, 89, 153));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

     


        JLabel subtitle = new JLabel(
                "<html><div style='text-align: center; width: 100%;'>늘 곁에 있는,<br>나만의 감정 수면 파트너</div></html>",
                SwingConstants.CENTER
        );
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 24));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);


        // 제목
        JLabel title = new JLabel("NEUL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 120));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // 로그인, 회원가입 버튼
        JButton loginBtn = new JButton("로그인");
        JButton signupBtn = new JButton("회원가입");
        customizeButton(loginBtn);
        customizeButton(signupBtn);

        loginBtn.addActionListener(e -> controller.navigateTo("login"));
        signupBtn.addActionListener(e -> controller.navigateTo("signup"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(91, 89, 153));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 구성
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(subtitle);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(70)); 
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());

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

   
}

