package com.michaelyvars.guacamole.player;

import lombok.Data;
import lombok.ToString;
import net.kyori.adventure.text.Component;

import java.util.UUID;

@Data
@ToString(exclude = "name")
public class PlayerData {

    private final UUID uniqueId;
    private final Component name;
    private boolean alive = true;
    private boolean teleported = true;
}
