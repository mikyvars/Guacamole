package com.michaelyvars.guacamole.player;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.game.GameState;
import io.github.miniplaceholders.api.MiniPlaceholders;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerManager implements Listener {

    private final Guacamole plugin;
    private final HashMap<UUID, PlayerData> players;

    public PlayerManager(Guacamole plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public List<PlayerData> getAlivePlayers() {
        return players.values().stream().filter(PlayerData::isAlive).toList();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = players.get(player.getUniqueId());

        if(playerData == null) {
            playerData = new PlayerData(player.getUniqueId(), player.name());
            players.put(player.getUniqueId(), playerData);
        }

        if(plugin.getGameManager().getGameState() == GameState.WAITING) {

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);

            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.teleport(plugin.getWorldManager().getLobby().getSpawnLocation().add(0.5, 0.5, 0.5));

            event.joinMessage(MiniMessage.miniMessage().deserialize("<game_prefix><gold><player_name> <white>a rejoint la partie.",
                    MiniPlaceholders.getGlobalPlaceholders(),
                    MiniPlaceholders.getAudiencePlaceholders(player)));
        } else {
            event.joinMessage(MiniMessage.miniMessage().deserialize("<game_prefix-join><player_name>",
                    MiniPlaceholders.getGlobalPlaceholders()));
        }

        plugin.getScoreboardManager().registerScoreboard(player);
        plugin.getCustomLogger().logDebug("[PlayerJoinEvent] " + playerData);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = players.get(player.getUniqueId());

        if(playerData == null) {
            plugin.getCustomLogger().logDebug("PlayerData of [" + player.getName() + "] was null at disconnection.");
            return;
        }

        if(plugin.getGameManager().getGameState() == GameState.WAITING) {
            event.quitMessage(MiniMessage.miniMessage().deserialize("<game_prefix><gold><player_name> <white>a quitté la partie.",
                    MiniPlaceholders.getGlobalPlaceholders(),
                    MiniPlaceholders.getAudiencePlaceholders(player)));
        } else {
            event.quitMessage(MiniMessage.miniMessage().deserialize("<game_prefix-quit><player_name>",
                    MiniPlaceholders.getGlobalPlaceholders()));
        }

        plugin.getScoreboardManager().getScoreboards().remove(player.getUniqueId());
        plugin.getCustomLogger().logDebug("[PlayerQuitEvent] " + playerData);
    }
}
