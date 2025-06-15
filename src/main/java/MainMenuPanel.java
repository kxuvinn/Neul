import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private ScreenController controller;

    public MainMenuPanel(ScreenController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(new Color(91, 89, 153)); 

        
        JLabel title = new JLabel("NEUL");
        title.setFont(new Font("Serif", Font.BOLD, 80));
        title.setForeground(Color.WHITE);
        title.setBounds(610, 100, 400, 100);
        add(title);

        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(40, 30, 100, 40);
        add(logoutButton);

        
        Font buttonFont = new Font("SansSerif", Font.BOLD, 24);
        Color btnBackground = Color.WHITE;
        int buttonWidth = 300;
        int buttonHeight = 60;
        int x = 580;
        int yStart = 300;
        int gap = 140;

        
        JButton calculatorBtn = new JButton("Calculator");
        calculatorBtn.setFont(buttonFont);
        calculatorBtn.setBackground(btnBackground);
        calculatorBtn.setBounds(x, yStart, buttonWidth, buttonHeight);
        add(calculatorBtn);

        
        JButton todayBtn = new JButton("Today's");
        todayBtn.setFont(buttonFont);
        todayBtn.setBackground(btnBackground);
        todayBtn.setBounds(x, yStart + gap, buttonWidth, buttonHeight);
        add(todayBtn);

        
        JButton analysisBtn = new JButton("Analysis");
        analysisBtn.setFont(buttonFont);
        analysisBtn.setBackground(btnBackground);
        analysisBtn.setBounds(x, yStart + 2 * gap, buttonWidth, buttonHeight);
        add(analysisBtn);

        calculatorBtn.addActionListener(e -> controller.goToCalculator());
        todayBtn.addActionListener(e -> controller.goToTodayInput());
        analysisBtn.addActionListener(e -> controller.goToAnalysis());
        logoutButton.addActionListener(e -> controller.handleLogout());
    }

}
