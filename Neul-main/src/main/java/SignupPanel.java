import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {
    private ScreenController controller;

    public SignupPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); // 짙은 보라 배경

        // NEUL 텍스트
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(new Color(182, 153, 40)); // 금색
        title.setBounds(620, 60, 200, 80);
        add(title);

        // 달 원 컴포넌트
        MoonCircle moon = new MoonCircle();
        moon.setBounds(800, 70, 50, 50);
        add(moon);

        // "회원가입" 타이틀
        JLabel registerLabel = new JLabel("회원가입", SwingConstants.CENTER);
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        registerLabel.setBounds(620, 180, 200, 50);
        add(registerLabel);

        // 회원가입 폼 박스
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(470, 290, 500, 500);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(formPanel);

        // 아이디
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(60, 40, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(60, 70, 380, 35);

        // 비밀번호
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(60, 130, 100, 30);
        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(60, 160, 380, 35);

        // 비밀번호 확인
        JLabel pwConfirmLabel = new JLabel("비밀번호 확인");
        pwConfirmLabel.setBounds(60, 220, 150, 30);
        JPasswordField pwConfirmField = new JPasswordField();
        pwConfirmField.setBounds(60, 250, 380, 35);

        // 가입하기 버튼
        JButton registerBtn = new JButton("가입하기");
        registerBtn.setBounds(60, 330, 380, 40);
        registerBtn.setBackground(new Color(40, 40, 40));
        registerBtn.setForeground(Color.WHITE);

        // 👉 회원가입 버튼 클릭 이벤트
        registerBtn.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());
            String pwConfirm = new String(pwConfirmField.getPassword());

            if (!pw.equals(pwConfirm)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
                return;
            }

            // 실제 회원가입 처리 로직은 여기에 추가할 수 있음
            JOptionPane.showMessageDialog(this, "회원가입 완료!");

            // 현재 창 닫기
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

            // 로그인 화면으로 전환 (새 JFrame 말고 카드 전환이 좋지만, 현재 구조 유지)
            JFrame loginFrame = new JFrame("로그인");
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loginFrame.setSize(1440, 1024);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setContentPane(new LoginPanel(controller));  // ✅ controller 전달
            loginFrame.setVisible(true);
        });

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwLabel);
        formPanel.add(pwField);
        formPanel.add(pwConfirmLabel);
        formPanel.add(pwConfirmField);
        formPanel.add(registerBtn);
    }

    // 테스트용 실행
    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Register - NEUL");
    //     frame.setSize(1440, 1024);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setLocationRelativeTo(null);
    //     frame.add(new SignupPanel(null));  // 테스트 시 null 전달
    //     frame.setVisible(true);
    // }
}
