package com.michaelyvars.guacamole.events.player;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class EventPlayerAdvancementDone implements Listener {

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        event.message(MiniMessage.miniMessage().deserialize("<player_name> <white>a obtenu l'avancement <green>[<advancement>]",
                MiniPlaceholders.getAudiencePlaceholders(event.getPlayer()),
                Placeholder.component("advancement", event.getAdvancement().displayName())));
    }
}