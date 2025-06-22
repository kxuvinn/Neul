import javax.swing.*;
import java.awt.*;

public class DailyInfo extends JDialog {
    public DailyInfo(String day, String sleepTime, String quality) {        // 날짜, 취침시간, 수면의 질 넘겨받기

        setTitle("Daily Information");    // 창 이름
        setSize(753, 418);    // 창 크기
        setResizable(false);
        setLocationRelativeTo(null);      // 창을 화면 정중앙에 띄움
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     // 창 닫기

        setLayout(new BorderLayout());
        Color backgroundColor = new Color(200, 205, 225);  // 창 전체에 사용할 색

        // 날짜
        JLabel dateLabel = new JLabel(day);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 30));  // 글씨체 변경
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);   // 왼쪽
        dateLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));   // 여백
        dateLabel.setOpaque(true);                         // ← 배경색 적용 가능하게
        dateLabel.setBackground(backgroundColor);          // ← contentPanel과 색 통일
        add(dateLabel, BorderLayout.NORTH);   // 위쪽

        // 취침 시간, 수면의 질
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(backgroundColor);  // 바탕색 설정

        JLabel sleepLabel = new JLabel(sleepTime + " 취침", SwingConstants.RIGHT);
        JLabel qualityLabel = new JLabel("수면의 질: " + quality, SwingConstants.RIGHT);

        contentPanel.add(sleepLabel);
        contentPanel.add(qualityLabel);

        add(contentPanel, BorderLayout.CENTER);
    }
}
