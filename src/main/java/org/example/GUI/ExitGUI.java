package org.example.GUI;

import org.example.Game.GameState;
import org.example.Game.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitGUI extends JDialog implements CreatorGUI, ActionListener {

    private JLabel titleLB = new JLabel("Game has been stopped.", SwingConstants.CENTER);

    private JPanel optionsPanel = new JPanel();
    private JButton exitBT = new JButton("EXIT");
    private JButton resumeBT = new JButton("RESUME");

    public ExitGUI() {
        setGUIParams();
        addGUIComponents();
        addGUIComponentsToListeners();
        setGUIComponentsParams();
        this.setVisible(true);
    }

    @Override
    public void setGUIParams() {
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLocationRelativeTo(null); // Center window
        this.setTitle("Pause:");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.decode("#097301"));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GameState.getInstance().setGameStatus(GameStatus.RUNNING);
                e.getWindow().dispose();
            }
        });
    }

    @Override
    public void setGUIComponentsParams() {
        titleLB.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        optionsPanel.setLayout(new GridLayout(2, 1, 50, 50));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(125, 50, 100, 50));
        optionsPanel.setBackground(Color.decode("#097301"));

        resumeBT.setBackground(Color.decode("#fc7f03"));
        exitBT.setBackground(Color.decode("#fc7f03"));
    }

    @Override
    public void addGUIComponents() {
        this.add(titleLB, BorderLayout.NORTH);
        this.add(optionsPanel, BorderLayout.CENTER);

        optionsPanel.add(resumeBT);
        optionsPanel.add(exitBT);
    }

    @Override
    public void addGUIComponentsToListeners() {
        resumeBT.addActionListener(this);
        exitBT.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exitBT) {
            GameGUI.close();
        }
        else {
            GameState.getInstance().setGameStatus(GameStatus.RUNNING);
        }
        this.dispose();
    }
}
