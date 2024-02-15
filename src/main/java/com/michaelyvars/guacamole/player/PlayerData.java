package com.michaelyvars.guacamole.player;

import com.michaelyvars.guacamole.team.TeamData;
import lombok.Data;
import lombok.ToString;
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
