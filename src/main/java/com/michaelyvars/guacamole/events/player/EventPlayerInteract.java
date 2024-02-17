package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.menus.MenuRules;
import com.michaelyvars.guacamole.data.menus.MenuTeam;
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
        if (plugin.getGameState() == GameState.WAITING && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() == null)
                return;

            if (event.getItem().equals(MenuTeam.getItem()))
                plugin.getOdalitaMenus().openMenu(new MenuTeam(plugin), event.getPlayer());
            else if (event.getItem().equals(MenuRules.getItem()))
                plugin.getOdalitaMenus().openMenu(new MenuRules(plugin), event.getPlayer());
        }
    }
}
