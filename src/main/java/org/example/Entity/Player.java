package org.example.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

@Getter
@Setter
public final class Player extends Zombie {

    private static Player player;

    private static ImageIcon[] playerImages = null;

    int money;
    int kills;
    boolean isShooting;

    private Player(int speed, int health, float damage, boolean isHurt, boolean isAlive, ImageIcon currentImage, int money, int kills, boolean isShooting) {
        super(speed, health, damage, isHurt, isAlive, currentImage);
        this.money = money;
        this.kills = kills;
        this.isShooting = isShooting;
    }

    @Override
    public void updateImage()   //changes current image
    {
        if(playerImages == null)
        {
            loadImages();
        }

        if(this.isShooting)
        {
            this.setCurrentImage(playerImages[1]);  //player shooting
            this.setShooting(false);
        }
        else {
            this.setCurrentImage(playerImages[0]); // player not shooting
        }
    }

    //player got hit, decrease players health
    public static void registerDamage(float damage)
    {
       player.setHealth((int)(player.getHealth() - damage));
       if(player.getHealth() <= 0)
       {
           player.setAlive(false);
       }
    }

    //player killed zombie
    public static void updateStatsAfterKill()
    {
        int kills = player.getKills();
        int money = player.getMoney();

        player.setKills(kills + 1);
        player.setMoney(money+2);
    }

    //loads images
    private void loadImages()
    {
        try {
            playerImages = new ImageIcon[2];
            playerImages[0] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Player_animation/player_image.png")));
            playerImages[1] = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/GameGUI/Player_animation/player_shooting.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player getInstance()
    {
        if(player == null)
        {
            player = new Player(0,100,1,false,true,null,0,0,false);
        }
        return player;
    }
}
