package org.example.Game;

import org.example.Entities.Player;
import org.example.GUI.GameGUI;
import org.example.Service.GameStateService;

public class World {

    GameStateService gameStateService = new GameStateService();
    public World() {
    }

    //start new game
    public void createWorld() {
        GameState.getInstance().setGameStatus(GameStatus.RUNNING);
        startWorldUpdater();
    }

    //game updater
    private void startWorldUpdater() {
        Thread worldThread = new Thread() {
            public void run() {
                GameState gameState = GameState.getInstance();
                while (gameState.getGameStatus() != GameStatus.FINISHED) {
                    try {
                        //waiting for next level
                        if(gameState.getGameStatus() == GameStatus.BREAK)
                        {
                            gameStateService.prepareNextLevel(gameState);
                            Thread.sleep(5000);
                            gameState.setGameStatus(GameStatus.RUNNING);
                        }
                        //game running
                        if (gameState.getGameStatus() == GameStatus.RUNNING) {
                            Player player = Player.getInstance();
                            if (player.isAlive() && gameStateService.allZombiesEliminated(gameState)) { //set next level
                                gameState.setGameStatus(GameStatus.BREAK);
                            } else if (!player.isAlive()) {
                                gameState.setGameStatus(GameStatus.FINISHED); //player died, finish game
                            }
                            GameGUI.update();
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                GameState.reset();
            }
        };
        worldThread.start();
    }
}
