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

    private int speed;
    private int health;
    private float damage;
    private boolean isHurt;
    private boolean isAlive;
    private ImageIcon currentImage;
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
}
