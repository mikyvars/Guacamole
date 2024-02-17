package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.game.GameState;
import com.michaelyvars.guacamole.menu.types.MenuRules;
import com.michaelyvars.guacamole.menu.types.MenuTeam;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerInteract implements Listener {

    private final Guacamole plugin;

    public EventPlayerInteract(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (plugin.getGameManager().getGameState() == GameState.WAITING && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() == null)
                return;

            if (event.getItem().equals(MenuTeam.getItem()))
                plugin.getOdalitaMenus().openMenu(new MenuTeam(plugin), event.getPlayer());
            else if (event.getItem().equals(MenuRules.getItem()))
                plugin.getOdalitaMenus().openMenu(new MenuRules(plugin), event.getPlayer());
        }
    }
}
