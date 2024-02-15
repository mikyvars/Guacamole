package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerDropItem implements Listener {

    private final Guacamole plugin;

    public EventPlayerDropItem(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (plugin.getGameManager().getGameState() != GameState.IN_GAME)
            event.setCancelled(true);
    }
}
