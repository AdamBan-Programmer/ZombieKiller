package org.example.GUI;

import org.example.Utils.ScaleImage;
import org.example.Utils.ScaleLayout;
import org.example.Game.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuGUI extends JFrame implements CreatorGUI, ActionListener {

    ScaleLayout scallingController = new ScaleLayout();
    ScaleImage scallingImageController = new ScaleImage();

    JPanel modePanel = new JPanel();

    JButton startGameBT = new JButton("START");
    JButton rulesBT = new JButton("RULES");
    JButton exitBT = new JButton("EXIT");

    public MenuGUI() {
        setGUIParams();
        addGUIComponents();
        addGUIComponentsToListeners();
        setGUIComponentsParams();
    }

    @Override
    public void setGUIParams() {

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setTitle("Zombie Killer");
        this.setBackground(Color.decode("#097301"));
        setBackground();

        modePanel.setLayout(new GridLayout(3,1,0,50));
        modePanel.setOpaque(false);

        this.add(modePanel);
    }

    @Override
    public void setGUIComponentsParams() {
        scallingController.setScallingParams(25,30,0,40,18,modePanel,this);
        startGameBT.setBackground(Color.decode("#fc7f03"));
        exitBT.setBackground(Color.decode("#fc7f03"));
        rulesBT.setBackground(Color.decode("#fc7f03"));
    }

    @Override
    public void addGUIComponents() {
        modePanel.add(startGameBT);
        modePanel.add(rulesBT);
        modePanel.add(exitBT);
    }

    @Override
    public void addGUIComponentsToListeners() {
        startGameBT.addActionListener(this);
        rulesBT.addActionListener(this);
        exitBT.addActionListener(this);
    }

    //loads backgroundImage and sets into background
    private void setBackground()
    {
        try {
            ImageIcon gameIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/game_icon.png")));
            this.setIconImage(gameIcon.getImage());

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            ImageIcon backgroundIcon = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/MenuGUI/Background/menu_background.png")));
            ImageIcon scaledBackground = scallingImageController.scale(backgroundIcon,(int)screenSize.getWidth(),(int)screenSize.getHeight());
            this.setContentPane(new JLabel(scaledBackground));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component component = (Component) e.getSource();

        if(component == startGameBT)
        {
            new GameGUI();
            World worldController = new World();
            worldController.createWorld();
        }

        if(component == rulesBT)
        {
            new RulesGUI();
        }

        if(component == exitBT) //closes app
        {
            MenuGUI.this.dispose();
            System.exit(0);
        }
    }
}
