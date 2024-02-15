package com.michaelyvars.guacamole;

import com.michaelyvars.guacamole.data.expansions.ExpansionGame;
import com.michaelyvars.guacamole.data.expansions.ExpansionPlayer;
import com.michaelyvars.guacamole.events.EventAsyncChat;
import com.michaelyvars.guacamole.events.player.EventPlayerDropItem;
import com.michaelyvars.guacamole.events.player.EventPlayerInteract;
import com.michaelyvars.guacamole.events.player.EventPlayerJoin;
import com.michaelyvars.guacamole.events.player.EventPlayerQuit;
import com.michaelyvars.guacamole.game.GameManager;
import com.michaelyvars.guacamole.player.PlayerManager;
import com.michaelyvars.guacamole.scoreboard.ScoreboardManager;
import com.michaelyvars.guacamole.team.TeamManager;
import com.michaelyvars.guacamole.utils.CustomLogger;
import com.michaelyvars.guacamole.world.WorldManager;
import lombok.Getter;
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
            pluginManager.registerEvents(new EventPlayerDropItem(this), this);
            pluginManager.registerEvents(new EventPlayerInteract(this), this);
            pluginManager.registerEvents(new EventPlayerJoin(this), this);
            pluginManager.registerEvents(new EventPlayerQuit(this), this);
            pluginManager.registerEvents(new EventAsyncChat(this), this);
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
