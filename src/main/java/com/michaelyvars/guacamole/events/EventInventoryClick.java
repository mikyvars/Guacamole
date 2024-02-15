package com.michaelyvars.guacamole.events;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class EventInventoryClick implements Listener {

    private final Guacamole plugin;

    public EventInventoryClick(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (plugin.getGameManager().getGameState() != GameState.IN_GAME)
            event.setCancelled(true);
    }
}
