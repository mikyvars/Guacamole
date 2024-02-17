package com.michaelyvars.guacamole.events.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.game.GameState;
import com.michaelyvars.guacamole.menu.types.MenuRules;
import com.michaelyvars.guacamole.menu.types.MenuTeam;
import com.michaelyvars.guacamole.player.PlayerData;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerJoin implements Listener {

    private final Guacamole plugin;

    public EventPlayerJoin(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerManager().getPlayers().get(player.getUniqueId());

        if (playerData == null) {
            playerData = new PlayerData(player.getUniqueId(), player.name());
            plugin.getPlayerManager().getPlayers().put(player.getUniqueId(), playerData);
        }

        if (plugin.getGameManager().getGameState() == GameState.WAITING) {

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);

            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.getInventory().setItem(3, MenuTeam.getItem());
            player.getInventory().setItem(5, MenuRules.getItem());

            if (plugin.getWorldManager().getLobby() == null)
                return;
            else
                player.teleport(plugin.getWorldManager().getLobby().getSpawnLocation().add(0.5, 0.5, 0.5));

            event.joinMessage(MiniMessage.miniMessage().deserialize("<game_prefix><gold><player_name> <white>a rejoint la partie.",
                    MiniPlaceholders.getGlobalPlaceholders(),
                    MiniPlaceholders.getAudiencePlaceholders(player)));
        } else {
            event.joinMessage(MiniMessage.miniMessage().deserialize("<game_prefix-join><player_name>",
                    MiniPlaceholders.getGlobalPlaceholders()));
        }

        plugin.getScoreboardManager().registerScoreboard(player);
    }
}
