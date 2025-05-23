import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private ScreenController controller;

    public LoginPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); // 짙은 보라 배경

        // NEUL + 달 이미지
        // NEUL 텍스트
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(new Color(182, 153, 40)); // 금색
        title.setBounds(620, 60, 200, 80);
        add(title);

// 코드로 직접 그린 달 원
        MoonCircle moon = new MoonCircle();
        moon.setBounds(800, 70, 50, 50);
        add(moon);


        // "로그인" 타이틀
        JLabel loginLabel = new JLabel("로그인", SwingConstants.CENTER);
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        loginLabel.setBounds(620, 180, 200, 50);
        add(loginLabel);

        // 로그인 폼 배경 박스
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(470, 290, 500, 500);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(formPanel);

        // 아이디 라벨과 텍스트필드
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(60, 50, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(60, 80, 380, 35);

        // 비밀번호 라벨과 텍스트필드
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(60, 150, 100, 30);
        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(60, 180, 380, 35);

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(60, 350, 380, 40);
        loginBtn.setBackground(new Color(40, 40, 40));
        loginBtn.setForeground(Color.WHITE);

        // 컴포넌트 추가
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwLabel);
        formPanel.add(pwField);
        formPanel.add(loginBtn);

        // 로그인 버튼 이벤트 처리
        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pw = new String(pwField.getPassword());

            if (UserService.getInstance().login(id, pw)) {
                AuthManager.setCurrentUser(new User(id, pw)); // ✅ 로그인 사용자 저장
                controller.handleLoginSuccess();          // ✅ Main 화면으로 이동
            } else {
                controller.showError("아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        });

    }

    // 테스트용 실행
    //public static void main(String[] args) {
        //JFrame frame = new JFrame("Login - NEUL");
        //frame.setSize(1440, 1024);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        //frame.add(new LoginPanel());
        //frame.setVisible(true);
    //}
}

