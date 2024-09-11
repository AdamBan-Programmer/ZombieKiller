package org.example.Utils;

import javax.swing.*;
import java.awt.*;

public class ScaleImage {

    public ScaleImage() {
    }

    public ImageIcon scale(ImageIcon icon,int width,int height)
    {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(width, height,  Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }
}
