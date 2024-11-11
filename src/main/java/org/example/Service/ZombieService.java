package org.example.Service;

import org.example.Entities.Player;
import org.example.Entities.Zombie;
import org.example.Game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class ZombieService {

    private static ImageIcon[] zombieImages = null;

    //changes zombie current image
    public void updateImage(Zombie zombie)
    {
        if(zombieImages == null)
        {
            loadImages();
        }
        for(int i =0;i<zombieImages.length;i++) {

            if(zombie.getHealth() <=0 && zombie.isHurt())
            {
                zombie.setCurrentImage(zombieImages[3]); //zombie died
                zombie.setHurt(false);
                return;
            }
            if (zombie.isHurt()) {
                zombie.setCurrentImage(zombieImages[2]); //zombie damaged
                zombie.setHurt(false);
                return;
            }
            if (zombie.getCurrentImage() == null || i == zombieImages.length - 3) {
                zombie.setCurrentImage(zombieImages[0]);//zombie stay
                return;
            }
            if (zombie.getCurrentImage() == zombieImages[i]) {
                zombie.setCurrentImage(zombieImages[i + 1]); //zombie walk animation
                return;
            }
        }
    }

    //checks that zombie is near to player
    public boolean canAttack(JPanel zombiePanel,JPanel playerPanel)
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

    //upgrades skills
    public void calculateSkills(Zombie zombie)
    {
        int level = GameState.getInstance().getLevel();
        zombie.setSpeed(10 + level);
        zombie.setHealth(5 + (2*level));
        zombie.setDamage((float) (0.1 + (0.001*level)));
    }

    //zombie got hit
    public void registerDamage(Zombie zombie)
    {
        if(zombie.isAlive()) {
            zombie.setHurt(true);
            Player player = Player.getInstance();
            player.setShooting(true);
            float damage = player.getDamage();
            int zombieHp = zombie.getHealth();

            zombie.setHealth((int)(zombieHp - damage));
            if(zombie.getHealth() <= 0)
            {
                zombie.setAlive(false);
            }
        }
    }

    //zombie died
    public void registerDeath() {
        int zombieCount = GameState.getInstance().getZombiesAlive();
        GameState.getInstance().setZombiesAlive(zombieCount - 1);
    }

    //loads images
    private void loadImages()
    {
        try {
            zombieImages = new ImageIcon[4];
            zombieImages[0] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Zombie_animation/zombie_stay.png")));
            zombieImages[1] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Zombie_animation/zombie_walk1.png")));
            zombieImages[2] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Zombie_animation/zombie_damaged.png")));
            zombieImages[3] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Zombie_animation/zombie_dead.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
