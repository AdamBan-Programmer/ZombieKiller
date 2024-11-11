package org.example.Service;

import org.example.Game.GameState;
import org.example.Game.GameStatus;

public class GameStateService {

    //changes for next level
    public void prepareNextLevel(GameState gameState)
    {
        int level = gameState.getLevel();
        gameState.setGameStatus(GameStatus.RUNNING);
        gameState.setLevel(level+1);
        gameState.setZombiesAlive(level+5);
    }

    //are any targets?
    public boolean allZombiesEliminated(GameState gameState)
    {
        return gameState.getZombiesAlive() == 0;
    }

    public int progress()
    {
        int level = GameState.getInstance().getLevel();
        int zombiesToKill = GameState.getInstance().getZombiesAlive();
        return 100-(int)((double)zombiesToKill/(5+level-1)*100);
    }
}
