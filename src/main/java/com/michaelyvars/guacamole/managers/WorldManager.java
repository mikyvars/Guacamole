package com.michaelyvars.guacamole.managers;

import com.michaelyvars.guacamole.Guacamole;
import lombok.Getter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.jetbrains.annotations.Nullable;

import java.lang.management.ManagementFactory;

@Getter
public class WorldManager {

    private final Guacamole plugin;

    @Nullable
    private World lobby;
    private World world;
    private World worldNether;

    public WorldManager(Guacamole plugin) {
        this.plugin = plugin;

        if(ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Dlobby"))
            plugin.getServer().createWorld(new WorldCreator("world_lobby"));
        else
            throw new RuntimeException("When using the -Dlobby argument, you must provider a world_lobby");

        plugin.getServer().getWorlds().forEach(targetWorld -> {
            switch (targetWorld.getName()) {
                case "world" -> {
                    world = targetWorld;
                    world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    world.setStorm(false);
                    world.setThundering(false);
                    world.setTime(6000);
                }
                case "world_lobby" -> {
                    lobby = targetWorld;
                    lobby.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    lobby.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    lobby.setGameRule(GameRule.DO_FIRE_TICK, false);
                    lobby.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
                    lobby.setStorm(false);
                    lobby.setThundering(false);
                    lobby.setTime(6000);
                }
                case "world_nether" -> worldNether = targetWorld;
            }

            plugin.getCustomLogger().logInfo("[" + targetWorld.getName() + "] has been successfully loaded.");
        });
    }
}
