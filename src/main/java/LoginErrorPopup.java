import javax.swing.*;
import java.awt.*;

public class LoginErrorPopup {

    public static void showErrorDialog(JFrame parent) {
        // 다이얼로그 생성
        JDialog dialog = new JDialog(parent, "오류", true);
        dialog.setSize(400, 220);
        dialog.setLayout(null);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(new Color(200, 205, 225));

        // 중앙 정렬
        dialog.setLocationRelativeTo(parent);

        // 메시지 라벨
        JLabel message = new JLabel("<html><div style='text-align:center;'>아이디나 비밀번호가 다릅니다.<br>다시 입력해주세요!</div></html>", SwingConstants.CENTER);
        message.setFont(new Font("SansSerif", Font.BOLD, 16));
        message.setBounds(30, 30, 340, 60);
        dialog.add(message);

        // 확인 버튼
        JButton okButton = new JButton("확인");
        okButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        okButton.setBounds(160, 120, 80, 35);
        dialog.add(okButton);

        // 버튼 클릭 시 창 닫기
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // 테스트용 실행
    //public static void main(String[] args) {
        //JFrame dummyParent = new JFrame();
        //dummyParent.setSize(400, 400);
        //dummyParent.setLocationRelativeTo(null);
        //dummyParent.setVisible(true);

        //showErrorDialog(dummyParent);
    //}
}
