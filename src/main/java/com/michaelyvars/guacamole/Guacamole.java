package com.michaelyvars.guacamole;

import com.michaelyvars.guacamole.data.expansions.ExpansionGame;
import com.michaelyvars.guacamole.data.expansions.ExpansionPlayer;
import com.michaelyvars.guacamole.game.GameManager;
import com.michaelyvars.guacamole.player.PlayerManager;
import com.michaelyvars.guacamole.scoreboard.ScoreboardManager;
import com.michaelyvars.guacamole.utils.CustomLogger;
import com.michaelyvars.guacamole.world.WorldManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Guacamole extends JavaPlugin {

    private CustomLogger customLogger;
    private WorldManager worldManager;
    private GameManager gameManager;
    private PlayerManager playerManager;
    private ScoreboardManager scoreboardManager;

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
            this.scoreboardManager = new ScoreboardManager(this);

            new ExpansionGame(this).get().register();
            new ExpansionPlayer(this).get().register();
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
