package com.michaelyvars.guacamole.game;

import com.michaelyvars.guacamole.Guacamole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager {

    private final Guacamole plugin;
    private GameState gameState;

    public GameManager(Guacamole plugin) {
        this.plugin = plugin;
        this.gameState = GameState.WAITING;
    }
}
