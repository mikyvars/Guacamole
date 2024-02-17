package com.michaelyvars.guacamole.managers;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.PlayerData;
import lombok.Getter;
import org.bukkit.event.Listener;

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

    public List<PlayerData> getDeadPlayers() {
        return players.values().stream().filter(playerData -> !playerData.isAlive()).toList();
    }
}
