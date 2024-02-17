package com.michaelyvars.guacamole.managers;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.managers.fastboard.FastBoard;
import io.github.miniplaceholders.api.MiniPlaceholders;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class ScoreboardManager {

    private final Guacamole plugin;
    private final HashMap<UUID, FastBoard> scoreboards;

    public ScoreboardManager(Guacamole plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();

        plugin.getServer().getScheduler().runTask(plugin, () -> {
            Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
            scoreboard.getObjectives().forEach(Objective::unregister);
            scoreboard.registerNewObjective("health", Criteria.HEALTH, Component.text("Vie"), RenderType.HEARTS).setDisplaySlot(DisplaySlot.PLAYER_LIST);
        });
    }

    public void registerScoreboard(Player player) {
        FastBoard fastBoard = scoreboards.get(player.getUniqueId());

        if (fastBoard != null)
            fastBoard.delete();

        fastBoard = new FastBoard(player);
        fastBoard.updateTitle(MiniMessage.miniMessage().deserialize("<gray>» <dark_green><bold>Guacamole</bold> <gray>«"));
        scoreboards.put(player.getUniqueId(), fastBoard);

        player.getScheduler().runAtFixedRate(plugin, scheduledTask -> {
            Component[] lines = plugin.getGameState().getLines().clone();

            for (int i = 0; i < lines.length; i++) {
                lines[i] = MiniMessage.miniMessage().deserialize(((TextComponent) lines[i]).content(),
                        MiniPlaceholders.getGlobalPlaceholders(),
                        MiniPlaceholders.getAudienceGlobalPlaceholders(player));
            }

            scoreboards.get(player.getUniqueId()).updateLines(lines);
        }, fastBoard::delete, 5L, 20L);
    }
}
