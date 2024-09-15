package org.example.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GameState {

    private static GameState instance;
    private Status gameStatus;
    private int level;
    private int zombiesToKill;
    private GameState(Status gameStatus, int level, int zombiesToKill) {
        this.gameStatus = gameStatus;
        this.level = level;
        this.zombiesToKill = zombiesToKill;
    }

    //changes for next level
    public static void prepareNextLevel()
    {
        int level = instance.getLevel();
        instance = new GameState(Status.RUNNING,level+1,5+level);
    }

    //sets 1lvl params
    public static void restartGame()
    {
        instance = new GameState(Status.PAUSED,1,5);
    }

    //are any targets?
    public static boolean allZombiesEliminated()
    {
        return instance.getZombiesToKill() == 0;
    }

    public static GameState getInstance() {
        if(instance == null)
        {
            instance = new GameState(Status.PAUSED,1,5);
        }
        return instance;
    }
}
