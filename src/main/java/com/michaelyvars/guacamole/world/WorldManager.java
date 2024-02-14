package com.michaelyvars.guacamole.world;

import com.michaelyvars.guacamole.Guacamole;
import lombok.Getter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;

@Getter
public class WorldManager {

    private final Guacamole plugin;
    private World lobby;
    private World world;
    private World worldNether;

    public WorldManager(Guacamole plugin) {
        this.plugin = plugin;

        plugin.getServer().createWorld(new WorldCreator("lobby"));
        plugin.getServer().getWorlds().forEach(target -> {
            switch (target.getName()) {
                case "lobby" -> {
                    lobby = target;
                    lobby.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    lobby.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    lobby.setGameRule(GameRule.DO_FIRE_TICK, false);
                    lobby.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
                    lobby.setStorm(false);
                    lobby.setThundering(false);
                    lobby.setTime(6000);
                }
                case "world" -> {
                    world = target;
                    world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    world.setStorm(false);
                    world.setThundering(false);
                    world.setTime(6000);
                }
                case "world_nether" -> worldNether = target;
            }

            plugin.getCustomLogger().logInfo("[" + target.getName() + "] has been successfully loaded.");
        });
    }
}
