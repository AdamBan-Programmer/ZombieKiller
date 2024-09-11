package org.example.GUI;

import org.example.Entity.Player;
import org.example.Entity.Zombie;
import org.example.Game.GameState;
import org.example.Game.Status;
import org.example.Utils.ScaleImage;
import org.example.Utils.ScaleLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

public class GameGUI implements CreatorGUI {

    static ScaleLayout scallingController = new ScaleLayout();
    static ScaleImage scallingImageController = new ScaleImage();

    static LinkedHashMap<JPanel, Zombie> zombieHashMap = new LinkedHashMap<>();

    static JFrame gameFrame = new JFrame();
    static JLayeredPane safeZone = new JLayeredPane();
    static JLayeredPane battleZone = new JLayeredPane();

    static JLabel safeZoneBackground = new JLabel();
    static JLabel battleZoneBackground = new JLabel();

    static JPanel playerPanel = new JPanel();
    static JLabel healthImageLB = new JLabel();
    static JLabel gameInfoLB = new JLabel();

    static JProgressBar levelProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
    static JButton pauseGameBT = new JButton();
    static JButton showUpgraderBT = new JButton();

   private static ImageIcon hpImage;

    public GameGUI() {
        setGUIParams();
        addGUIComponents();
        addGUIComponentsToListeners();
        setGUIComponentsParams();
        loadImages();
        spawnPlayer();
        changeCrosshair();
    }

    @Override
    public void setGUIParams() {
        gameFrame = new JFrame();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setUndecorated(true);
        gameFrame.setTitle("Zombie Killer");
        gameFrame.setLayout(null);
        battleZone.setLayout(null);
        safeZone.setLayout(null);
        gameFrame.setVisible(true);
    }

    @Override
    public void setGUIComponentsParams() {
        scallingController.setScallingParams(30,100,0,0,0,safeZone,gameFrame);
        scallingController.setScallingParams(70,100,0,0,30, battleZone,gameFrame);
        scallingController.setScallingParams(2,3,0,1,1, healthImageLB,gameFrame);
        scallingController.setScallingParams(90,5,50,0,3, gameInfoLB,gameFrame);
        scallingController.setScallingParams(20,3,50,1,40,levelProgressBar,gameFrame);
        scallingController.setScallingParams(3,5,0,1,96, pauseGameBT,gameFrame);
        scallingController.setScallingParams(3,5,0,7,96, showUpgraderBT,gameFrame);


        scallingController.setScallingParams(100,100,0,0,0,safeZoneBackground,safeZone);
        scallingController.setScallingParams(100,100,0,0,0, battleZoneBackground, battleZone);
        scallingController.setScallingParams(25,20,0,40,75, playerPanel,safeZone);
        levelProgressBar.setStringPainted(true);
        levelProgressBar.setForeground(Color.GREEN);
    }

    @Override
    public void addGUIComponents() {
        gameFrame.add(healthImageLB,2,0);
        gameFrame.add(gameInfoLB,2,0);
        gameFrame.add(levelProgressBar,2,0);
        gameFrame.add(pauseGameBT,2,0);
        gameFrame.add(showUpgraderBT,2,0);
        gameFrame.add(safeZone);
        gameFrame.add(battleZone);

        safeZone.add(safeZoneBackground,1,0);
        battleZone.add(battleZoneBackground,1,0);
    }

    @Override
    public void addGUIComponentsToListeners() {
        pauseGameBT.addActionListener(actionListener);
        showUpgraderBT.addActionListener(actionListener);
    }

   static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component component = (Component) e.getSource();

            if (component == pauseGameBT) {
                GameState.getInstance().setGameStatus(Status.PAUSED);
                new ExitGUI();
            }

            if(component == showUpgraderBT)
            {
                GameState.getInstance().setGameStatus(Status.PAUSED);
                new UpgraderGUI();
            }
        }
    };

    //setting crosshair instead of cursor
    private void changeCrosshair() {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Crosshair/crosshair.png"))).getImage();
            Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
            gameFrame.getContentPane().setCursor(c);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "Couldn't set crosshair.");
        }
    }

    //loads images
    private void loadImages()
    {
        try {
            ImageIcon gameIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/game_icon.png")));
            gameFrame.setIconImage(gameIcon.getImage());

            ImageIcon safeZoneImage = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Background/safe_zone_image.png")));
            safeZoneBackground.setIcon(scallingImageController.scale(safeZoneImage, safeZone.getWidth(), safeZone.getHeight()));

            ImageIcon battlefieldImage = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Background/battle_zone_image.png")));
            battleZoneBackground.setIcon(scallingImageController.scale(battlefieldImage, battleZone.getWidth(), battleZone.getHeight()));

            hpImage = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Other/player_health.png")));
            healthImageLB.setIcon(scallingImageController.scale(hpImage, healthImageLB.getWidth(), healthImageLB.getHeight()));

            ImageIcon pauseGameIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Buttons/pause_game.png")));
            pauseGameBT.setIcon(scallingImageController.scale(pauseGameIcon, pauseGameBT.getWidth(), pauseGameBT.getHeight()));

            ImageIcon upgraderIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Buttons/upgrader_image.png")));
            showUpgraderBT.setIcon(scallingImageController.scale(upgraderIcon, showUpgraderBT.getWidth(), showUpgraderBT.getHeight()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //creates player image and hp bar
    private void spawnPlayer()
    {
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);

        JLabel playerImage = new JLabel();
        scallingController.setScallingParams(100,89,0,10,0, playerImage,playerPanel);

        JLabel playerHP = new JLabel("HP");
        scallingController.setScallingParams(50,10,90,0,30, playerHP,playerPanel);

        JLabel playerHPicon = new JLabel();
        playerHPicon.setHorizontalAlignment(SwingConstants.CENTER);
        scallingController.setScallingParams(15,10,0,0,15, playerHPicon,playerPanel);
        playerHPicon.setIcon(scallingImageController.scale(hpImage, playerHPicon.getWidth(), playerHPicon.getHeight()));

        playerPanel.add(playerImage);
        playerPanel.add(playerHP);
        playerPanel.add(playerHPicon);
        safeZone.add(playerPanel,2,0);
    }

    //creates zombie and zombie hp bar
    private static void spawnZombie()
    {
        Zombie zombieObj = new Zombie(false,true,null);
        zombieObj.calculateSkills();

        JPanel zombiePanel = new JPanel();
        zombiePanel.setLayout(null);
        zombiePanel.setOpaque(false);

        Random rand = new Random();
        int posX = rand.nextInt(90 - 70 + 1) + 70;
        int posY = rand.nextInt(90 - 10 + 1) + 10;
        scallingController.setScallingParams(10,10,0,posY,posX,zombiePanel, battleZone);

        JButton newZombieImageButton = new JButton();
        newZombieImageButton.setBorderPainted(false);
        newZombieImageButton.setFocusPainted(false);
        newZombieImageButton.setContentAreaFilled(false);
        scallingController.setScallingParams(100,80,0,20,0,newZombieImageButton,zombiePanel);

        JLabel zombieHP = new JLabel("HP " + zombieObj.getHealth(),SwingConstants.CENTER);
        zombieHP.setOpaque(false);
        scallingController.setScallingParams(40,20,90,0,60,zombieHP,zombiePanel);

        newZombieImageButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JButton zombieImageButton = (JButton) me.getSource();
                JPanel zombiePanel = (JPanel) zombieImageButton.getParent();
                Zombie zombieObj = zombieHashMap.get(zombiePanel);
                zombieObj.registerDamage();
            }
        });
        zombiePanel.add(newZombieImageButton);
        zombiePanel.add(zombieHP);
        battleZone.add(zombiePanel,2,0);
        zombieHashMap.put(zombiePanel,zombieObj);
    }

    //updates all entities and game stats panel
    public static void update() {
        GameState gameState = GameState.getInstance();
        if(gameState.getGameStatus() != Status.FINISHED) {
            int zombiesCount = gameState.getZombiesToKill();
            if (zombieHashMap.size() < zombiesCount) {
                spawnZombie();
            }
            updateZombie();
            updatePlayer();
            updateGameInformations();
        }
        else {
            JOptionPane.showMessageDialog(null, "The last defender is dead. Zombies have taken over the world");
            finishGame();
        }
    }

    //updates all zombies
    private static void updateZombie() {
        Iterator<Map.Entry<JPanel, Zombie>> iterator = zombieHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<JPanel, Zombie> entry = iterator.next();
            JPanel zombiePanel = entry.getKey();
            JButton zombieImageButton = (JButton) zombiePanel.getComponent(0);
            JLabel zombieHP = (JLabel) zombiePanel.getComponent(1);
            Zombie zombieObj = entry.getValue();

            if (zombieObj.isAlive()) {
                zombieHP.setText("HP " + zombieObj.getHealth());
                updateZombiePos(zombiePanel,zombieObj);
                if(canAttack(zombiePanel))
                {
                    Player.registerDamage(zombieObj.getDamage());
                }
            } else if(!zombieObj.isHurt()) {
                zombieObj.registerDeath();
                iterator.remove();
                battleZone.remove(zombiePanel);
                battleZone.repaint();
            }
            zombieObj.updateImage();
            zombieImageButton.setIcon(scallingImageController.scale(zombieObj.getCurrentImage(), zombieImageButton.getWidth(), zombieImageButton.getHeight()));
        }
    }

    //updates zombie position
    private static void updateZombiePos(JPanel zombiePanel,Zombie zombie)
    {
        JLabel playerImage = (JLabel) playerPanel.getComponents()[0];
        int speed = zombie.getSpeed();

        int newX = zombiePanel.getX();
        int newY = zombiePanel.getY();

        if(zombie.isHurt()) {   //step back when got hit
            if(zombiePanel.getX() + speed <= battleZone.getX() + battleZone.getWidth()) {
                newX = zombiePanel.getX() + speed;
            }
        }
        else {
            if (zombiePanel.getX() > 0) {
                if ((zombiePanel.getX() - speed) > 0) {
                    newX = zombiePanel.getX() - speed;
                } else {
                    newX = 0;
                }
            } else {
                if (zombiePanel.getY() < (playerPanel.getY() + (playerImage.getHeight() / 2))) {
                    newY = zombiePanel.getY() + speed;
                } else {
                    newY = zombiePanel.getY() - speed;
                }
            }
        }
        zombiePanel.setLocation(newX,newY);
    }

    //checks that zombie is near to player
    private static boolean canAttack(JPanel zombiePanel)
    {
        if(zombiePanel.getX() == 0)
        {
            if(zombiePanel.getY() > playerPanel.getY() && zombiePanel.getY() < playerPanel.getY() + playerPanel.getHeight())
            {
                return  true;
            }
        }
        return false;
    }

    //updates player
    private static void updatePlayer() {
        Player player = Player.getInstance();
        JLabel playerImage = (JLabel) playerPanel.getComponents()[0];
        player.updateImage();
        playerImage.setIcon(scallingImageController.scale(player.getCurrentImage(), playerImage.getWidth(), playerImage.getHeight()));
        JLabel playerHP = (JLabel) playerPanel.getComponents()[1];
        playerHP.setText(player.getHealth() + "HP");
    }

    //updates info panel
    private static void updateGameInformations()
    {
        Player player = Player.getInstance();
        int level = GameState.getInstance().getLevel();
        int zombiesToKill = GameState.getInstance().getZombiesToKill();
        int progress = 100-(int)((double)zombiesToKill/(5+level-1)*100);

        gameInfoLB.setText(player.getHealth() + "    $" + player.getMoney() + "     KILLS: " + player.getKills());
        levelProgressBar.setString("level: "+level + "    " + progress + "%");
        levelProgressBar.setValue(progress);
    }

    //clears and closes GameGUI
    public static void finishGame()
    {
        GameState.getInstance().setGameStatus(Status.FINISHED);
        pauseGameBT.removeActionListener(actionListener);
        showUpgraderBT.removeActionListener(actionListener);
        zombieHashMap.clear();
        playerPanel.removeAll();
        safeZone.removeAll();
        battleZone.removeAll();
        gameFrame.removeAll();
        gameFrame.dispose();
    }
}
