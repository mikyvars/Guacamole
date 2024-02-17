package com.michaelyvars.guacamole.data.threads;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.configuration.Configuration;
import com.michaelyvars.guacamole.data.TeamData;
import io.github.miniplaceholders.api.MiniPlaceholders;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class GameThread extends BukkitRunnable {

    private final Guacamole plugin;
    private int time;
    private int episode;
    @Setter private boolean paused;

    public GameThread(Guacamole plugin) {
        this.plugin = plugin;
        this.time = 0;
        this.episode = 0;
        this.paused = false;
    }

    @Override
    public void run() {

        if (paused) {
            plugin.getServer().showTitle(Title.title(Component.text("Partie en pause"), Component.empty()));
        } else {

            if ((time % plugin.getConfiguration().get(Configuration.TIMER_EPISODE)) == 0 || time == 0) {
                episode++;

                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    if (player != null)
                        player.showTitle(Title.title(Component.text("Début de l'épisode"), Component.text(String.valueOf(episode), NamedTextColor.GREEN)));
                });

                plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<game_prefix>Début de l'épisode <green><game_time-episode><white>.",
                        MiniPlaceholders.getGlobalPlaceholders()));
            }

            if (time == plugin.getConfiguration().get(Configuration.TIMER_INVINCIBILITY)) {
                plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<game_prefix>Les dégâts sont désormais actifs.",
                        MiniPlaceholders.getGlobalPlaceholders()));
            }

            if (time == plugin.getConfiguration().get(Configuration.TIMER_PVP)) {
                plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<game_prefix>Le PVP est désormais actif.",
                        MiniPlaceholders.getGlobalPlaceholders()));
            }

            if(time == plugin.getConfiguration().get(Configuration.TIMER_MOLES)) {
                //TODO announce moles
            }

            if(plugin.getTeamManager().getAliveTeams().size() == 1) {
                TeamData teamData = plugin.getTeamManager().getAliveTeams().get(0);
                //TODO game win
                cancel();
            }

            time++;
        }
    }
}
