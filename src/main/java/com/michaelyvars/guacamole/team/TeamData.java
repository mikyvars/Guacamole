package com.michaelyvars.guacamole.team;

import com.michaelyvars.guacamole.player.PlayerData;
import lombok.Data;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class TeamData {

    private final UUID uniqueId;
    private Component name;
    private final NamedTextColor color;
    private final Material icon;
    private Location spawn;
    private final List<PlayerData> players;

    public TeamData(String name, NamedTextColor color, Material icon) {
        this.uniqueId = UUID.randomUUID();
        this.name = Component.text(name, color);
        this.color = color;
        this.icon = icon;
        this.players = new ArrayList<>();
    }
}