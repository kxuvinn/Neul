import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DailyInfo extends JDialog {

    // 날짜, 취침시간, 수면의 질 넘겨받기
    public DailyInfo(LocalDate date, String hour, String minute, String sleepQuality, String emotion) {
        setTitle("Daily Information");    // 창 이름
        setSize(600, 400);    // 창 크기
        setResizable(false);
        setLocationRelativeTo(null);      // 창을 화면 정중앙에 띄움
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     // 창 닫기

        setLayout(new BorderLayout());
        Color backgroundColor = new Color(200, 205, 225);  // 창 전체에 사용할 색
        getContentPane().setBackground(backgroundColor);

        // 날짜
        String formattedDate = formatDate(date);
        JLabel dateLabel = new JLabel(formattedDate);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 30));  // 글씨체 변경
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);   // 왼쪽
        dateLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));   // 여백
        dateLabel.setOpaque(true);                         // ← 배경색 적용 가능하게
        dateLabel.setBackground(backgroundColor);          // ← contentPanel과 색 통일
        add(dateLabel, BorderLayout.NORTH);   // 위쪽

        // 중앙 패널 (왼쪽: 원, 오른쪽: 텍스트)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);

        // 왼쪽 원형 패널
        CircleStatusPanel circlePanel = new CircleStatusPanel(sleepQuality);
        centerPanel.add(circlePanel, BorderLayout.WEST);

        // 취침 시간, 수면의 질
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(backgroundColor);  // 바탕색 설정

        JLabel sleepLabel = new JLabel(
                "<html><b><span style='color:#4B0082'>" + hour + "시간 " + minute + "분</span></b> 취침</html>",
                SwingConstants.RIGHT);
        sleepLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));  // 글자 크기 키움
        sleepLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40)); // 오른쪽 여백 주기

        JLabel qualityLabel = new JLabel("<html>이날의 감정: <b>" + emotion + "</b></html>", SwingConstants.RIGHT);
        qualityLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        qualityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

        contentPanel.add(sleepLabel);
        contentPanel.add(qualityLabel);

        centerPanel.add(contentPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    // 날짜 포맷 변환 (예: 05.19 Mon)
    private String formatDate(LocalDate date) {
        Date utilDate = java.sql.Date.valueOf(date);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd E", java.util.Locale.ENGLISH);
        return sdf.format(utilDate);
    }

    // 원형 상태 표현하는 내부 클래스
    static class CircleStatusPanel extends JPanel {
        private final String sleepQuality;

        public CircleStatusPanel(String quality) {
            this.sleepQuality = quality;
            setPreferredSize(new Dimension(300, 300));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 색 결정
            Color ringColor;
            switch (sleepQuality) {
                case "좋음":
                    ringColor = new Color(0, 100, 0);
                    break;
                case "보통":
                    ringColor = Color.ORANGE;
                    break;
                case "나쁨":
                    ringColor = new Color(139, 0, 0);
                    break;
                default:
                    ringColor = Color.GRAY;
            }

            int size = 150;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2 + 5;

            g2.setStroke(new BasicStroke(15));
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
