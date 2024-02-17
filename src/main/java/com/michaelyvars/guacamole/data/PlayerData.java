package com.michaelyvars.guacamole.data;

import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class PlayerData {

    private final UUID uniqueId;
    private final Component name;
    private TeamData team;
    private boolean alive = true;
    private boolean teleported = true;

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }
}
