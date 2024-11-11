package org.example.GUI;

import org.example.Entities.Player;
import org.example.Game.GameState;
import org.example.Game.GameStatus;
import org.example.Utils.ScaleImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UpgraderGUI extends JDialog implements CreatorGUI, ActionListener {

    static ScaleImage scallingImageController = new ScaleImage();

    private static final int DAMAGE_COST = 20;
    private static final int HEALTH_COST = 10;

    JPanel imagesOfSkillsPanel = new JPanel();
    JPanel buyButtonsPanel = new JPanel();

    JLabel infoLB = new JLabel("",SwingConstants.CENTER);

    JLabel damageImageLB = new JLabel();
    JLabel healthImageLB = new JLabel();

    JButton buyDamageBT = new JButton("+2 damage for $" + DAMAGE_COST);
    JButton buyHealthBT = new JButton("+10 HP for $" + HEALTH_COST);

    private static ImageIcon[] icons = new ImageIcon[2];

    public UpgraderGUI() {
        setGUIParams();
        addGUIComponents();
        addGUIComponentsToListeners();
        setGUIComponentsParams();
        updatePlayerStats();
        SwingUtilities.invokeLater(this::loadImages);
        this.setVisible(true);
    }

    @Override
    public void setGUIParams() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle("Upgrader:");
        this.getContentPane().setBackground(Color.decode("#097301"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameState.getInstance().setGameStatus(GameStatus.RUNNING);
                e.getWindow().dispose();
            }
        });
        imagesOfSkillsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buyButtonsPanel.setLayout(new GridLayout(1, 2, 50, 50));
        this.add(imagesOfSkillsPanel, BorderLayout.CENTER);
        this.add(buyButtonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setGUIComponentsParams() {
        imagesOfSkillsPanel.setOpaque(false);
        buyButtonsPanel.setOpaque(false);
        imagesOfSkillsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buyButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));
    }

    @Override
    public void addGUIComponents() {
        this.add(infoLB,BorderLayout.NORTH);

        imagesOfSkillsPanel.add(damageImageLB);
        imagesOfSkillsPanel.add(healthImageLB);

        buyButtonsPanel.add(buyDamageBT);
        buyButtonsPanel.add(buyHealthBT);
    }

    @Override
    public void addGUIComponentsToListeners() {
        buyDamageBT.addActionListener(this);
        buyHealthBT.addActionListener(this);
    }

    //loads images
    private void loadImages() {
        try {
            icons[0] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/UpgraderGUI/Images/upgrade_damage.png")));
            icons[1] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/UpgraderGUI/Images/upgrade_health.png")));
            damageImageLB.setIcon(scallingImageController.scale(icons[0], damageImageLB.getWidth(), damageImageLB.getHeight()));
            healthImageLB.setIcon(scallingImageController.scale(icons[1], healthImageLB.getWidth(), healthImageLB.getHeight()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //updates player stats
    private void updatePlayerStats()
    {
        Player player = Player.getInstance();
        infoLB.setText(player.toString());
    }

    //no money warning
    private void showNoEnoughMoneyWarning()
    {
        JOptionPane.showMessageDialog(null,
                "You have no enough money to buy this skill!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Component component = (Component) e.getSource();
        Player player = Player.getInstance();
        int playerMoney = player.getMoney();

        if(component == buyDamageBT)
        {
            if(playerMoney >= DAMAGE_COST) {
                float damage = player.getDamage();
                player.setDamage(damage + 2);
                player.setMoney(playerMoney-DAMAGE_COST);
            }
            else {
                showNoEnoughMoneyWarning();
            }
        }

        if(component == buyHealthBT)
        {
            if(playerMoney >= HEALTH_COST) {
                int health = player.getHealth();
                player.setHealth(health + 10);
                player.setMoney(playerMoney-HEALTH_COST);
            }
            else {
                showNoEnoughMoneyWarning();
            }
        }
        updatePlayerStats();
    }
}
