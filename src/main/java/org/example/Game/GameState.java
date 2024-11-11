package org.example.Game;

import lombok.Getter;
import lombok.Setter;
import org.example.Service.GameStateService;

@Getter
@Setter
public final class GameState {

    private static GameState instance;
    private GameStatus gameStatus;
    private int level;
    private int zombiesAlive;
    private GameState(GameStatus gameStatus, int level, int zombiesAlive) {
        this.gameStatus = gameStatus;
        this.level = level;
        this.zombiesAlive = zombiesAlive;
    }
    public static GameState getInstance() {
        if(instance == null)
        {
            instance = new GameState(GameStatus.PAUSED,1,5);
        }
        return instance;
    }

    @Override
    public String toString()
    {
        GameStateService gameStateService = new GameStateService();
        return "level: "+this.getLevel() + "    " + gameStateService.progress() + "%";
    }
}
