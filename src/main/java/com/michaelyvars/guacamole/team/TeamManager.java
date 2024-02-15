package com.michaelyvars.guacamole.team;

import com.michaelyvars.guacamole.Guacamole;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
public class TeamManager implements Listener {

    private final Guacamole plugin;
    private final List<TeamData> teams;

    public TeamManager(Guacamole plugin) {
        this.plugin = plugin;
        this.teams = Arrays.asList(
                new TeamData("Lime_1", NamedTextColor.GREEN, Material.LIME_WOOL),
                new TeamData("Yellow_1", NamedTextColor.YELLOW, Material.YELLOW_WOOL),
                new TeamData("Orange_1", NamedTextColor.GOLD, Material.ORANGE_WOOL),
                new TeamData("Pink_1", NamedTextColor.LIGHT_PURPLE, Material.PINK_WOOL),
                new TeamData("Magenta_1", NamedTextColor.DARK_PURPLE, Material.MAGENTA_WOOL),
                new TeamData("Light_Blue_1", NamedTextColor.AQUA, Material.LIGHT_BLUE_WOOL),
                new TeamData("Cyan_1", NamedTextColor.DARK_AQUA, Material.CYAN_WOOL),
                new TeamData("Light_Gray_1", NamedTextColor.GRAY, Material.LIGHT_GRAY_WOOL),
                new TeamData("White_1", NamedTextColor.WHITE, Material.WHITE_WOOL),
                new TeamData("Lime_2", NamedTextColor.GREEN, Material.LIME_WOOL),
                new TeamData("Yellow_2", NamedTextColor.YELLOW, Material.YELLOW_WOOL),
                new TeamData("Orange_2", NamedTextColor.GOLD, Material.ORANGE_WOOL),
                new TeamData("Pink_2", NamedTextColor.LIGHT_PURPLE, Material.PINK_WOOL),
                new TeamData("Magenta_2", NamedTextColor.DARK_PURPLE, Material.MAGENTA_WOOL),
                new TeamData("Light_Blue_2", NamedTextColor.AQUA, Material.LIGHT_BLUE_WOOL),
                new TeamData("Cyan_2", NamedTextColor.DARK_AQUA, Material.CYAN_WOOL),
                new TeamData("Light_Gray_2", NamedTextColor.GRAY, Material.LIGHT_GRAY_WOOL),
                new TeamData("White_2", NamedTextColor.WHITE, Material.WHITE_WOOL)
        );

        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);

        teams.forEach(teamData -> {
            Team bukkitTeam = scoreboard.registerNewTeam(teamData.getUniqueId().toString());
            bukkitTeam.color(teamData.getColor());
        });
    }

    public List<TeamData> getAliveTeams() {
        List<TeamData> result = new ArrayList<>();
        teams.forEach(customTeam -> customTeam.getPlayers().forEach(playerData -> {
            if (playerData.isAlive() && !result.contains(customTeam))
                result.add(customTeam);
        }));
        return result;
    }

    public Team getBukkitTeam(TeamData team) {
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        return scoreboard.getTeam(team.getUniqueId().toString());
    }

    private LinkedList<Location> generateSpawnPoints(World world, int numLocations, double distanceFromCenter) {
        LinkedList<Location> spawnPoints = new LinkedList<>();
        double angleIncrement = 360.0 / numLocations;

        double centerX = 0.5;
        double centerZ = 0.5;

        for (int i = 0; i < numLocations; i++) {
            double angle = Math.toRadians(i * angleIncrement);
            double xOffset = distanceFromCenter * Math.cos(angle);
            double zOffset = distanceFromCenter * Math.sin(angle);

            double x = centerX + xOffset;
            double y = world.getHighestBlockYAt((int) x, (int) zOffset);
            double z = centerZ + zOffset;

            Location location = new Location(world, x, y + 1.0, z);
            spawnPoints.add(location);

            for (int k = -2; i < 2; i++)
                for (int l = -2; l < 2; l++)
                    world.getBlockAt(k, (int) y, l).setType(Material.BEDROCK);
        }

        return spawnPoints;
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        if (event.getWorld().getName().equalsIgnoreCase("world")) {
            LinkedList<Location> spawnPoints = generateSpawnPoints(event.getWorld(), teams.size(), event.getWorld().getWorldBorder().getSize() - 250);
            teams.forEach(teamData -> teamData.setSpawn(spawnPoints.poll()));
        }
    }
}
