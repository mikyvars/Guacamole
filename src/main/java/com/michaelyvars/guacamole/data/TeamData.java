package com.michaelyvars.guacamole.data;

import lombok.Data;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        this.name = Component.text(name);
        this.color = color;
        this.icon = icon;
        this.players = new ArrayList<>();
    }

    public Component getName() {
        return name.color(color);
    }
}
