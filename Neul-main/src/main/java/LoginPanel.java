import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private ScreenController controller;

    public LoginPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); // 짙은 보라 배경

        // NEUL + 달 이미지
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(new Color(182, 153, 40)); // 금색
        title.setBounds(620, 60, 200, 80);
        add(title);

        // 달 이미지 (그림)
        MoonCircle moon = new MoonCircle();
        moon.setBounds(800, 70, 50, 50);
        add(moon);

        // "로그인" 타이틀
        JLabel loginLabel = new JLabel("로그인", SwingConstants.CENTER);
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        loginLabel.setBounds(620, 180, 200, 50);
        add(loginLabel);

        // 로그인 폼 박스
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(470, 290, 500, 500);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(formPanel);

        // 아이디 입력 필드
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(60, 50, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(60, 80, 380, 35);

        // 비밀번호 입력 필드
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(60, 150, 100, 30);
        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(60, 180, 380, 35);

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(60, 350, 380, 40);
        loginBtn.setBackground(new Color(40, 40, 40));
        loginBtn.setForeground(Color.WHITE);

        // UserService 인스턴스
        UserService userService = UserService.getInstance();

        // 버튼 클릭 이벤트 처리
        loginBtn.addActionListener(e -> {
            String username = idField.getText();
            String password = new String(pwField.getPassword());

            String result = String.valueOf(userService.login(username, password));

            if (!result.equals("로그인이 완료되었습니다.")) {
                // 로그인 실패 팝업
                LoginErrorPopup.showErrorDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            } else {
                // 로그인 성공 처리
                JOptionPane.showMessageDialog(this, "로그인 성공!");

                // 현재 창 닫기
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }

                // 카드 기반 화면 전환
                controller.changeScreen("main");
            }
        });

        // 컴포넌트들을 formPanel에 추가
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwLabel);
        formPanel.add(pwField);
        formPanel.add(loginBtn);
    }

    // 테스트용 실행
    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Login - NEUL");
    //     frame.setSize(1440, 1024);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setLocationRelativeTo(null);
    //     frame.add(new LoginPanel(null)); // 테스트용으로 null 전달
    //     frame.setVisible(true);
    // }
}
