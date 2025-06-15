import javax.swing.*;
import java.awt.*;

public class MoonCircle extends JPanel {
    public MoonCircle() {
        setPreferredSize(new Dimension(50, 50));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 그림자
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillOval(5, 5, 40, 40);

        
        GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 223, 0), 40, 40, new Color(200, 170, 0));
        g2d.setPaint(gradient);
        g2d.fillOval(0, 0, 40, 40);

        // 테두리
        g2d.setColor(new Color(160, 130, 0));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawOval(0, 0, 40, 40);
    }
}
