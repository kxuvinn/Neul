import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private ScreenController controller;

    public LoginPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); 

       
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(new Color(0xFFE23D)); 
        title.setBounds(620, 60, 200, 80);
        add(title);

    
        MoonCircle moon = new MoonCircle();
        moon.setBounds(800, 70, 50, 50);
        add(moon);


        
        JLabel loginLabel = new JLabel("로그인", SwingConstants.CENTER);
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setBounds(620, 180, 200, 50);
        add(loginLabel);

        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(470, 290, 500, 500);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(formPanel);

      
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(60, 50, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(60, 80, 380, 35);

        
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(60, 150, 100, 30);
        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(60, 180, 380, 35);

     
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(60, 350, 380, 40);
        loginBtn.setBackground(new Color(40, 40, 40));
        loginBtn.setForeground(Color.WHITE);

        
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwLabel);
        formPanel.add(pwField);
        formPanel.add(loginBtn);

        //이벤트
        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pw = new String(pwField.getPassword());

            if (UserService.getInstance().login(id, pw)) {
                AuthManager.setCurrentUser(new User(id, pw)); 
                controller.handleLoginSuccess();          
            } else {
                controller.showError("아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        });

    }

}

