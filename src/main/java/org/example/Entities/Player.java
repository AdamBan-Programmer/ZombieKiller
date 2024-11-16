package org.example.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public final class Player extends Entity{

    private static Player instance;
    private int money;
    private int kills;
    private boolean isShooting;

    public Player(int speed, int health, float damage, boolean isHurt, boolean isAlive, ImageIcon currentImage, int money, int kills, boolean isShooting) {
        super(speed, health, damage, isHurt, isAlive, currentImage);
        this.money = money;
        this.kills = kills;
        this.isShooting = isShooting;
    }

    public static void resetPlayer()
    {
        instance = null;
    }

    public static Player getInstance()
    {
        if(instance == null)
        {
            instance = new Player(0,100,1,false,true,null,0,0,false);
        }
        return instance;
    }

    @Override
    public String toString()
    {
        return "HP: " + this.getHealth() + "    DAMAGE: " + (int)this.getDamage() +  "    $" + this.getMoney() + "     KILLS: " + this.getKills();
    }
}
