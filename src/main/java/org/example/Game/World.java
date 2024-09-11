package org.example.Game;

import org.example.Entity.Player;
import org.example.GUI.GameGUI;
import org.example.Game.GameState;
import org.example.Game.Status;

public class World {

    public World() {
    }

    //start new game
    public void createWorld() {
        GameState.getInstance().setGameStatus(Status.RUNNING);
        startWorldUpdater();
    }

    //game updater
    private void startWorldUpdater() {
        Thread worldThread = new Thread() {
            public void run() {
                while (GameState.getInstance().getGameStatus() != Status.FINISHED) {
                    try {
                        if (GameState.getInstance().getGameStatus() == Status.RUNNING) {
                            Player player = Player.getInstance();
                            if (player.isAlive() && GameState.allZombiesEliminated()) { //set next level
                                GameState.prepareNextLevel();
                                Thread.sleep(5000);
                            } else if (!player.isAlive()) {
                                GameState.getInstance().setGameStatus(Status.FINISHED); //player died, finish game
                            }
                            GameGUI.update();
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                GameState.restartGame();
            }
        };
        worldThread.start();
    }
}
