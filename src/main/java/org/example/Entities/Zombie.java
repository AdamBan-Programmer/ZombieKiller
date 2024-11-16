package org.example.Entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Zombie extends Entity{
    public Zombie(int speed, int health, float damage, boolean isHurt, boolean isAlive, ImageIcon currentImage) {
        super(speed, health, damage, isHurt, isAlive, currentImage);
    }
}
