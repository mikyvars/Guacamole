package com.michaelyvars.guacamole.events;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.PlayerData;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class AsyncChatEvent implements Listener {

    private final Guacamole plugin;

    public AsyncChatEvent(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncChat(io.papermc.paper.event.player.AsyncChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerManager().getPlayers().get(player.getUniqueId());
        TextComponent message = (TextComponent) event.message();

        if (plugin.getGameState() == GameState.WAITING) {
            plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<player_name> <dark_gray>» <white><message>",
                    MiniPlaceholders.getAudiencePlaceholders(player),
                    Placeholder.component("message", message)));
        } else {
            final TextComponent finalMessage = message;

            if (playerData.isAlive()) {
                if (message.content().startsWith("!")) {
                    plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<player_name> <dark_gray>» <white><message>",
                            MiniPlaceholders.getAudiencePlaceholders(player),
                            Placeholder.component("message", message.content(message.content().replaceFirst("!", "")))));
                } else {
                    plugin.getTeamManager().getBukkitTeam(playerData.getTeam()).sendMessage(MiniMessage.miniMessage().deserialize("<prefix> <player_name> <dark_gray>» <white><message>",
                            MiniPlaceholders.getAudiencePlaceholders(player),
                            Placeholder.component("prefix", plugin.prefix(Component.text("Équipe", playerData.getTeam().getColor()))),
                            Placeholder.component("message", message)));
                }
            } else {
                plugin.getPlayerManager().getDeadPlayers().forEach(targetData -> targetData.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<game_prefix-dead> <dark_gray><player_name> » <message>",
                        MiniPlaceholders.getGlobalPlaceholders(),
                        MiniPlaceholders.getAudiencePlaceholders(player),
                        Placeholder.component("message", finalMessage))));
            }
        }

        event.setCancelled(true);
    }
}
