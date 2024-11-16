package org.example.Entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.swing.*;

@Getter
@Setter
public abstract class Entity {

    private int speed;
    private int health;
    private float damage;
    private boolean isHurt;
    private boolean isAlive;
    private ImageIcon currentImage;

    public Entity(int speed, int health, float damage, boolean isHurt, boolean isAlive, ImageIcon currentImage) {
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.isHurt = isHurt;
        this.isAlive = isAlive;
        this.currentImage = currentImage;
    }
}
