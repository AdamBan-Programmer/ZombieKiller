package org.example.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GameState {

    Status gameStatus;
    int level;
    int zombiesToKill;

    private static GameState currentGameState;

    private GameState(Status gameStatus, int level, int zombiesToKill) {
        this.gameStatus = gameStatus;
        this.level = level;
        this.zombiesToKill = zombiesToKill;
    }

    //changes for next level
    public static void prepareNextLevel()
    {
        int level = currentGameState.getLevel();
        currentGameState = new GameState(Status.RUNNING,level+1,5+level);
    }

    //sets 1lvl params
    public static void restartGame()
    {
        currentGameState = new GameState(Status.PAUSED,1,5);
    }

    //are any targets?
    public static boolean allZombiesEliminated()
    {
        return currentGameState.getZombiesToKill() == 0;
    }

    public static GameState getInstance() {
        if(currentGameState == null)
        {
            currentGameState = new GameState(Status.PAUSED,1,5);
        }
        return currentGameState;
    }
}
