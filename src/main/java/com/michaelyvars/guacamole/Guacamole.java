package com.michaelyvars.guacamole;

import com.michaelyvars.guacamole.commands.CommandGame;
import com.michaelyvars.guacamole.commands.CommandTeam;
import com.michaelyvars.guacamole.configuration.Configuration;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.expansions.ExpansionGame;
import com.michaelyvars.guacamole.data.expansions.ExpansionPlayer;
import com.michaelyvars.guacamole.data.threads.GamePreStartThread;
import com.michaelyvars.guacamole.data.threads.GameThread;
import com.michaelyvars.guacamole.events.EventAsyncChat;
import com.michaelyvars.guacamole.events.EventFoodLevelChange;
import com.michaelyvars.guacamole.events.EventInventoryClick;
import com.michaelyvars.guacamole.events.EventServerListPing;
import com.michaelyvars.guacamole.events.player.*;
import com.michaelyvars.guacamole.managers.PlayerManager;
import com.michaelyvars.guacamole.managers.ScoreboardManager;
import com.michaelyvars.guacamole.managers.TeamManager;
import com.michaelyvars.guacamole.managers.WorldManager;
import com.michaelyvars.guacamole.utils.CustomLogger;
import lombok.Getter;
import lombok.Setter;
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
    private Configuration configuration;
    private WorldManager worldManager;
    private PlayerManager playerManager;
    private TeamManager teamManager;
    private ScoreboardManager scoreboardManager;
    private OdalitaMenus odalitaMenus;
    @Setter private GameState gameState = GameState.WAITING;
    @Setter private GamePreStartThread gamePreStartThread;
    @Setter private GameThread gameThread;

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

            this.configuration = new Configuration(this, getDataFolder().getPath());
            this.worldManager = new WorldManager(this);
            this.playerManager = new PlayerManager(this);
            this.teamManager = new TeamManager(this);
            this.scoreboardManager = new ScoreboardManager(this);
            this.odalitaMenus = OdalitaMenus.createInstance(this);
            this.gamePreStartThread = new GamePreStartThread(this);
            this.gameThread = new GameThread(this);

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
            commandManager.register(new CommandTeam(this), new CommandGame(this));
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
