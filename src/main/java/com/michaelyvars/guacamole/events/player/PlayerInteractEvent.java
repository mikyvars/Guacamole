package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.menus.RulesMenu;
import com.michaelyvars.guacamole.data.menus.TeamMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractEvent implements Listener {

    private final Guacamole plugin;

    public PlayerInteractEvent(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        if (plugin.getGameState() == GameState.WAITING && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() == null)
                return;

            if (event.getItem().equals(TeamMenu.getItem()))
                plugin.getOdalitaMenus().openMenu(new TeamMenu(plugin), event.getPlayer());
            else if (event.getItem().equals(RulesMenu.getItem()))
                plugin.getOdalitaMenus().openMenu(new RulesMenu(plugin), event.getPlayer());
        }
    }
}
