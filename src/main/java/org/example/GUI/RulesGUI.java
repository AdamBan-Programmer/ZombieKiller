package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class RulesGUI extends JDialog implements CreatorGUI {

    JLabel titleLB = new JLabel("RULES:", SwingConstants.CENTER);
    JLabel rulesLB = new JLabel("", SwingConstants.CENTER);

    public RulesGUI() {
        setGUIParams();
        setGUIComponentsParams();
        addGUIComponents();
        addGUIComponentsToListeners();
        this.setVisible(true);
    }

    @Override
    public void setGUIParams() {
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setSize(500,500);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.GRAY);
        this.setTitle("Rules:");
    }

    @Override
    public void setGUIComponentsParams() {
        titleLB.setFont(new Font("Calibri", Font.BOLD, 40));
        titleLB.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        rulesLB.setText(getRulesText());
        rulesLB.setFont(new Font("Calibri", Font.BOLD, 20));
        rulesLB.setBorder(BorderFactory.createEmptyBorder(50, 20, 100, 20));
    }

    @Override
    public void addGUIComponents() {
        this.add(titleLB,BorderLayout.NORTH);
        this.add(rulesLB,BorderLayout.CENTER);
    }

    @Override
    public void addGUIComponentsToListeners() {
        //
    }

    private String getRulesText()
    {
        String rulesText =
                "<html>" +
                        "<p style='margin-bottom: 10px;'>1. Press START button to begin the game.</p>" +
                        "<p style='margin-bottom: 10px;'>2. Aim on the target using your mouse. Use left-click to hit the zombie.</p>" +
                        "<p style='margin-bottom: 10px;'>3. Upgrade your skills in Upgrader.</p>" +
                        "<p style='margin-bottom: 10px;'>4. Try to save the Kingdom before the zombie invasion.</p>" +
                        "</html>";
        return rulesText;
    }
}
