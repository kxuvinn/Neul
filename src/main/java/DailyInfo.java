import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DailyInfo extends JDialog {

    // 날짜, 취침시간, 수면의 질, 감정 넘겨받기
    public DailyInfo(LocalDate date, String hour, String minute, String sleepQuality, String emotion) {
        setTitle("Daily Information");    // 창 이름
        setSize(600, 400);    // 창 크기
        setResizable(false);  // 크기 고정
        setLocationRelativeTo(null);      // 창을 화면 정중앙에 띄움
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     // 창 닫기

        setLayout(new BorderLayout());
        Color backgroundColor = new Color(200, 205, 225);  // 창 전체에 사용할 색
        getContentPane().setBackground(backgroundColor);

        // 날짜
        String formattedDate = formatDate(date);
        JLabel dateLabel = new JLabel(formattedDate);  // 포맷된 날짜 표시
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 30));  // 글씨체 변경
        dateLabel.setHorizontalAlig기

            // 수면의 질 색 결정
            Color ringColor;
            switch (sleepQuality) {
                case "좋음":
                    ringColor = new Color(0, 100, 0);  //초록색
                    break;
                case "보통":
                    ringColor = Color.ORANGE;  // 주황색
                    break;
                case "나쁨":
                    ringColor = new Color(139, 0, 0);  // 빨간색
                    break;
                default:
                    ringColor = Color.GRAY;  // 회색
            }

            // 원 위치, 크기
            int size = 150;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2 + 5;

            // 원 그리기
            g2.setStroke(new BasicStroke(15));  // 두께
            g2.setColor(ringColor);
            g2.drawOval(x, y, size, size);

            // 가운데 텍스트
            g2.setFont(new Font("SansSerif", Font.BOLD, 32));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(sleepQuality);
            int textHeight = fm.getAscent();
            g2.setColor(Color.BLACK);
            g2.drawString(sleepQuality, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4 + 5);

            // 원 위에 텍스트
            g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
            String label = "수면의 질";
            FontMetrics fmLabel = g2.getFontMetrics();
            int labelWidth = fmLabel.stringWidth(label);
            g2.drawString(label, getWidth() / 2 - labelWidth / 2, y - 20);
        }
    }
}
