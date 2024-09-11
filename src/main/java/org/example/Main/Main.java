package org.example.Main;
import org.example.GUI.MenuGUI;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MenuGUI();
            }
        });
    }
}