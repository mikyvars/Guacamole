package com.michaelyvars.guacamole;

import com.michaelyvars.guacamole.game.GameManager;
import com.michaelyvars.guacamole.utils.CustomLogger;
import com.michaelyvars.guacamole.world.WorldManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Guacamole extends JavaPlugin {

    private CustomLogger customLogger;
    private WorldManager worldManager;
    private GameManager gameManager;

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
        } catch (RuntimeException e) {
            getCustomLogger().logError(e.getMessage());
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Component prefix(Component prefix) {
        return MiniMessage.miniMessage().deserialize("<white>「<prefix>」</white>",
                Placeholder.component("prefix", prefix));
    }
}
