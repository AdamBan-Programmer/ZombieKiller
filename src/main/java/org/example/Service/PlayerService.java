package org.example.Service;

import org.example.Entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class PlayerService {

    private static ImageIcon[] playerImages = null;

    //changes current image
    public void updateImage(Player player)
    {
        if(playerImages == null)
        {
            loadImages();
        }

        if(player.isShooting())
        {
            player.setCurrentImage(playerImages[1]);  //player shooting
            player.setShooting(false);
        }
        else {
            player.setCurrentImage(playerImages[0]); // player not shooting
        }
    }

    //player got hit, decrease players health
    public static void registerDamage(Player player,float damage)
    {
        player.setHealth((int)(player.getHealth() - damage));
        if(player.getHealth() <= 0)
        {
            player.setAlive(false);
        }
    }

    //player killed zombie
    public static void updateStatsAfterKill(Player player)
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
}
