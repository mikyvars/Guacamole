package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.PlayerData;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitEvent implements Listener {

    private final Guacamole plugin;

    public PlayerQuitEvent(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerManager().getPlayers().get(player.getUniqueId());

        if (playerData == null) {
            plugin.getCustomLogger().logDebug("PlayerData of [" + player.getName() + "] was null at disconnection.");
            return;
        }

        if (plugin.getGameState() == GameState.WAITING) {

            if (playerData.getTeam() != null) {
                playerData.getTeam().getPlayers().remove(playerData);
                plugin.getTeamManager().getBukkitTeam(playerData.getTeam()).removeEntry(player.getName());
            }

            event.quitMessage(MiniMessage.miniMessage().deserialize("<game_prefix><gold><player_name> <white>a quitté la partie.",
                    MiniPlaceholders.getGlobalPlaceholders(),
                    MiniPlaceholders.getAudiencePlaceholders(player)));

            plugin.getPlayerManager().getPlayers().remove(player.getUniqueId());
        } else {
            event.quitMessage(MiniMessage.miniMessage().deserialize("<game_prefix-quit><player_name>",
                    MiniPlaceholders.getGlobalPlaceholders()));
        }

        plugin.getScoreboardManager().getScoreboards().remove(player.getUniqueId());
    }
}
