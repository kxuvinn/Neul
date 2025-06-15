import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {

    private ScreenController controller;

    public SignupPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); 

        // NEUL 텍스트
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(new Color(0xFFE23D)); 
        title.setBounds(620, 60, 200, 80);
        add(title);

        
        MoonCircle moon = new MoonCircle();
        moon.setBounds(800, 70, 50, 50);
        add(moon);

        
        JLabel registerLabel = new JLabel("회원가입", SwingConstants.CENTER);
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setBounds(620, 180, 200, 50);
        add(registerLabel);

       
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

        // 이벤트트
        registerBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pw = new String(pwField.getPassword());
            String pwConfirm = new String(pwConfirmField.getPassword());

            if (id.isEmpty() || pw.isEmpty() || pwConfirm.isEmpty()) {
                controller.showError("모든 항목을 입력해주세요.");
                return;
            }

            if (!pw.equals(pwConfirm)) {
                controller.showError("비밀번호가 일치하지 않습니다.");
                return;
            }

            boolean success = UserService.getInstance().register(id, pw); 

            if (success) {
                JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
                controller.handleSignupSuccess(); // 로그인 화면으로 이동
            } else {
                controller.showError("이미 존재하는 아이디입니다.");
            }
        });


        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwLabel);
        formPanel.add(pwField);
        formPanel.add(pwConfirmLabel);
        formPanel.add(pwConfirmField);
        formPanel.add(registerBtn);
    }


}

