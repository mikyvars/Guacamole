package com.michaelyvars.guacamole.data.threads;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import io.github.miniplaceholders.api.MiniPlaceholders;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class GamePreStartThread extends BukkitRunnable {

    private final Guacamole plugin;
    private int timer;

    public GamePreStartThread(Guacamole plugin) {
        this.plugin = plugin;
        resetTimer();
    }

    @Override
    public void run() {

        if (timer == 0) {
            GameThread gameThread = new GameThread(plugin);
            gameThread.runTaskTimer(plugin, 0L, 20L);

            plugin.setGameState(GameState.IN_GAME);
            plugin.setGameThread(gameThread);

            plugin.getPlayerManager().getAlivePlayers().forEach(playerData -> {
                Player player = playerData.getPlayer();

                if (player == null)
                    return;

                if (playerData.getTeam() == null) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Component.text("Tu es désormais un spectateur!"));
                    playerData.setAlive(false);
                } else {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(playerData.getTeam().getSpawn());
                    playerData.setTeleported(true);
                }
            });

            cancel();
        } else {
            plugin.getServer().broadcast(MiniMessage.miniMessage().deserialize("<game_prefix>Lancement dans <game_time:starting> seconde(s)",
                    MiniPlaceholders.getGlobalPlaceholders()));
        }

        timer--;
    }

    public void resetTimer() {
        timer = 10;
    }
}
