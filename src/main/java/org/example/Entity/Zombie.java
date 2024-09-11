package org.example.Entity;

import lombok.Getter;
import lombok.Setter;
import org.example.Game.GameState;
import org.example.Game.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

@Getter
@Setter
public class Zombie {

    int speed;
    int health;
    float damage;
    boolean isHurt;
    boolean isAlive;
    ImageIcon currentImage;

    private static ImageIcon[] zombieImages = null;

    public Zombie(int speed, int health, float damage, boolean isHurt, boolean isAlive, ImageIcon currentImage) {
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.isHurt = isHurt;
        this.isAlive = isAlive;
        this.currentImage = currentImage;
    }

    public Zombie(boolean isHurt, boolean isAlive, ImageIcon currentImage) {
        this.isHurt = isHurt;
        this.isAlive = isAlive;
        this.currentImage = currentImage;
    }

    //upgrades skills
    public void calculateSkills()
    {
        int level = GameState.getInstance().getLevel();
        this.speed = 10 + level;
        this.health = 5 + (2*level);
        this.damage = (float) (0.1 + (0.001*level));
    }

    //zombie got hit
    public void registerDamage()
    {
        if(this.isAlive) {
            this.setHurt(true);
            Player player = Player.getInstance();
            player.setShooting(true);
            float damage = player.getDamage();
            int zombieHp = this.getHealth();

            this.setHealth((int)(zombieHp - damage));
            if(this.getHealth() <= 0)
            {
                this.setAlive(false);
            }
        }
    }

    //zombie died
    public void registerDeath() {
        int zombieCount = GameState.getInstance().getZombiesToKill();
        GameState.getInstance().setZombiesToKill(zombieCount - 1);
        Player.updateStatsAfterKill();
    }

    public void updateImage()   //changes zombie current image
    {
        if(zombieImages == null)
        {
            loadImages();
        }
        for(int i =0;i<zombieImages.length;i++) {

            if(this.getHealth() <=0 && this.isHurt)
            {
                this.currentImage = zombieImages[3]; //zombie died
                this.setHurt(false);
                return;
            }
            if (this.isHurt) {
                this.currentImage = zombieImages[2]; //zombie damaged
                this.setHurt(false);
                return;
            }
            if (this.currentImage == null || i == zombieImages.length - 3) {
                this.currentImage = zombieImages[0]; //zombie stay
                return;
            }
            if (this.currentImage == zombieImages[i]) {
                this.currentImage = zombieImages[i + 1]; //zombie walk animation
                return;
            }
        }
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
