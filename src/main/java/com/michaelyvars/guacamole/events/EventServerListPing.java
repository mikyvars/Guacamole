package com.michaelyvars.guacamole.events;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class EventServerListPing implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.motd(MiniMessage.miniMessage().deserialize("<dark_green>Guacamole <dark_gray>» <gray><game_name><br><red><game_state>",
                MiniPlaceholders.getGlobalPlaceholders()));
    }
}
