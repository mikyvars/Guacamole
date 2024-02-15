package com.michaelyvars.guacamole;

import com.michaelyvars.guacamole.commands.CommandTeamName;
import com.michaelyvars.guacamole.data.expansions.ExpansionGame;
import com.michaelyvars.guacamole.data.expansions.ExpansionPlayer;
import com.michaelyvars.guacamole.events.EventAsyncChat;
import com.michaelyvars.guacamole.events.EventFoodLevelChange;
import com.michaelyvars.guacamole.events.EventInventoryClick;
import com.michaelyvars.guacamole.events.EventServerListPing;
import com.michaelyvars.guacamole.events.player.*;
import com.michaelyvars.guacamole.game.GameManager;
import com.michaelyvars.guacamole.player.PlayerManager;
import com.michaelyvars.guacamole.scoreboard.ScoreboardManager;
import com.michaelyvars.guacamole.team.TeamManager;
import com.michaelyvars.guacamole.utils.CustomLogger;
import com.michaelyvars.guacamole.world.WorldManager;
import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.odalitadevelopments.menus.OdalitaMenus;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Guacamole extends JavaPlugin {

    private CustomLogger customLogger;
    private WorldManager worldManager;
    private GameManager gameManager;
    private PlayerManager playerManager;
    private TeamManager teamManager;
    private ScoreboardManager scoreboardManager;
    private OdalitaMenus odalitaMenus;

    @Override
    public void onEnable() {
        try {
            this.customLogger = new CustomLogger("Guacamole");
            getCustomLogger().logInfo(" _______           _______  _______  _______  _______  _______  _        _______      ");
            getCustomLogger().logInfo("(  ____ \\|\\     /|(  ___  )(  ____ \\(  ___  )(       )(  ___  )( \\      (  ____ \\");
            getCustomLogger().logInfo("| (    \\/| )   ( || (   ) || (    \\/| (   ) || () () || (   ) || (      | (    \\/  ");
            getCustomLogger().logInfo("| |      | |   | || (___) || |      | (___) || || || || |   | || |      | (__         ");
            getCustomLogger().logInfo("| | ____ | |   | ||  ___  || |      |  ___  || |(_)| || |   | || |      |  __)        ");
            getCustomLogger().logInfo("| | \\_  )| |   | || (   ) || |      | (   ) || |   | || |   | || |      | (          ");
            getCustomLogger().logInfo("| (___) || (___) || )   ( || (____/\\| )   ( || )   ( || (___) || (____/\\| (____/\\  ");
            getCustomLogger().logInfo("(_______)(_______)|/     \\|(_______/|/     \\||/     \\|(_______)(_______/(_______/  ");

            this.worldManager = new WorldManager(this);
            this.gameManager = new GameManager(this);
            this.playerManager = new PlayerManager(this);
            this.teamManager = new TeamManager(this);
            this.scoreboardManager = new ScoreboardManager(this);
            this.odalitaMenus = OdalitaMenus.createInstance(this);

            new ExpansionGame(this).get().register();
            new ExpansionPlayer(this).get().register();

            PluginManager pluginManager = getServer().getPluginManager();
            pluginManager.registerEvents(new EventPlayerAdvancementDone(), this);
            pluginManager.registerEvents(new EventPlayerDropItem(this), this);
            pluginManager.registerEvents(new EventPlayerInteract(this), this);
            pluginManager.registerEvents(new EventPlayerJoin(this), this);
            pluginManager.registerEvents(new EventPlayerQuit(this), this);
            pluginManager.registerEvents(new EventAsyncChat(this), this);
            pluginManager.registerEvents(new EventFoodLevelChange(this), this);
            pluginManager.registerEvents(new EventInventoryClick(this), this);
            pluginManager.registerEvents(new EventServerListPing(), this);

            CommandManager commandManager = new CommandManager(this);
            commandManager.register(new CommandTeamName(this));
            commandManager.getMessageHandler().register("cmd.no.permission", sender -> sender.sendMessage("§cTu n'as pas la permission d'utiliser cette commande."));
            commandManager.getMessageHandler().register("cmd.no.console", sender -> sender.sendMessage("§cTu ne peux pas utiliser cette commande depuis la console."));
            commandManager.getMessageHandler().register("cmd.no.exists", sender -> sender.sendMessage("§cCette commande n'existe pas."));
            commandManager.getMessageHandler().register("cmd.wrong.usage", sender -> {});
        } catch (RuntimeException e) {
            getCustomLogger().logError(e.getMessage());
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(Player::kick);
    }

    public Component prefix(Component prefix) {
        return MiniMessage.miniMessage().deserialize("<white>「<prefix>」</white>",
                Placeholder.component("prefix", prefix));
    }
}
