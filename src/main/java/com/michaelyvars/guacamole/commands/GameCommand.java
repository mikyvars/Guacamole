package com.michaelyvars.guacamole.commands;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.menus.RulesMenu;
import com.michaelyvars.guacamole.data.menus.TeamMenu;
import com.michaelyvars.guacamole.data.threads.GamePreStartThread;
import io.github.miniplaceholders.api.MiniPlaceholders;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@Command("game")
@SuppressWarnings("unused")
public class GameCommand extends CommandBase {

    private final Guacamole plugin;

    public GameCommand(Guacamole plugin) {
        this.plugin = plugin;
    }

    @Default
    @Permission("minecraft.command.op")
    public void defaultCommand(CommandSender commandSender) {
        commandSender.sendMessage(Component.empty());
        commandSender.sendMessage(miniMessage().deserialize("<gray>┌ <game_prefix>", MiniPlaceholders.getGlobalPlaceholders()));
        commandSender.sendMessage(miniMessage().deserialize("<gray>│ <gold>/<yellow>game start <dark_gray>» <green>Lancer la partie."));
        commandSender.sendMessage(miniMessage().deserialize("<gray>│ <gold>/<yellow>game cancel <dark_gray>» <green>Annuler le lancement."));
        commandSender.sendMessage(miniMessage().deserialize("<gray>│ <gold>/<yellow>game pause <dark_gray>» <green>Mettre la partie en pause."));
        commandSender.sendMessage(miniMessage().deserialize("<gray>│ <gold>/<yellow>game resume <dark_gray>» <green>Reprendre la partie."));
        commandSender.sendMessage(miniMessage().deserialize("<gray>│ <gold>/<yellow>game reload <dark_gray>» <green>Recharger la configuration."));
        commandSender.sendMessage(Component.empty());
    }

    @SubCommand("start")
    @Permission("minecraft.command.op")
    public void start(CommandSender commandSender) {

        if (plugin.getGameState() == GameState.WAITING) {

            if(plugin.getTeamManager().getAliveTeams().size() < 2) {
                commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>Il doit y avoir au moins 2 équipes.", MiniPlaceholders.getGlobalPlaceholders()));
                return;
            }

            GamePreStartThread gamePreStartThread = new GamePreStartThread(plugin);
            gamePreStartThread.runTaskTimer(plugin, 0L, 20L);

            plugin.setGameState(GameState.STARTING);
            plugin.setGamePreStartThread(gamePreStartThread);
            plugin.getPlayerManager().getAlivePlayers().forEach(playerData -> {
                Player player = plugin.getServer().getPlayer(playerData.getUniqueId());

                if (player == null)
                    return;

                player.closeInventory();
                player.getInventory().clear();
            });

            return;
        }

        if (plugin.getGameState() == GameState.STARTING) {
            commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie est déjà en cours de lancement.", MiniPlaceholders.getGlobalPlaceholders()));
            return;
        }

        commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie est déjà en cours.", MiniPlaceholders.getGlobalPlaceholders()));
    }

    @SubCommand("cancel")
    @Permission("minecraft.command.op")
    public void cancel(CommandSender commandSender) {

        if (plugin.getGameState() == GameState.STARTING) {

            plugin.setGameState(GameState.WAITING);
            plugin.getGamePreStartThread().cancel();
            plugin.setGamePreStartThread(new GamePreStartThread(plugin));
            plugin.getPlayerManager().getAlivePlayers().forEach(playerData -> {
                Player player = plugin.getServer().getPlayer(playerData.getUniqueId());

                if (player == null)
                    return;

                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().setHeldItemSlot(4);
                player.getInventory().setItem(3, TeamMenu.getItem());
                player.getInventory().setItem(5, RulesMenu.getItem());
            });

            return;
        }

        if (plugin.getGameState() == GameState.WAITING) {
            commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie n'est pas en cours de lancement.", MiniPlaceholders.getGlobalPlaceholders()));
            return;
        }

        commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie est déjà en cours.", MiniPlaceholders.getGlobalPlaceholders()));
    }

    //TODO add freeze entities and invincibility + 3s on pause
    // fix title clignote
    // fix stats scoreboard
    @SubCommand("pause")
    @Permission("minecraft.command.op")
    public void pause(CommandSender commandSender) {

        if (plugin.getGameState() == GameState.IN_GAME) {

            if (plugin.getGameThread().isPaused()) {
                commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie est déjà en pause.", MiniPlaceholders.getGlobalPlaceholders()));
                return;
            }

            plugin.getGameThread().setPaused(true);
            return;
        }

        commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie n'est pas en cours.", MiniPlaceholders.getGlobalPlaceholders()));
    }

    @SubCommand("resume")
    @Permission("minecraft.command.op")
    public void resume(CommandSender commandSender) {

        if (plugin.getGameState() == GameState.IN_GAME) {

            if (plugin.getGameThread().isPaused()) {
                plugin.getGameThread().setPaused(false);
                return;
            }

            commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie n'est pas en pause.", MiniPlaceholders.getGlobalPlaceholders()));
            return;
        }

        commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La partie n'est pas en cours.", MiniPlaceholders.getGlobalPlaceholders()));
    }

    @SubCommand("reload")
    @Permission("minecraft.command.op")
    public void reload(CommandSender commandSender) {

        if (plugin.getGameState() == GameState.WAITING) {
            plugin.getConfiguration().reloadConfiguration();
            commandSender.sendMessage(miniMessage().deserialize("<game_prefix>La configuration a été rechargée.", MiniPlaceholders.getGlobalPlaceholders()));
            return;
        }

        commandSender.sendMessage(miniMessage().deserialize("<game_prefix:error>La configuration ne peut plus être rechargée.", MiniPlaceholders.getGlobalPlaceholders()));
    }

    @CompleteFor("game")
    public List<String> commandCompletion(List<String> completion, CommandSender commandSender) {
        return Arrays.asList("start", "cancel", "pause", "resume", "reload");
    }
}
