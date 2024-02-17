package com.michaelyvars.guacamole.data.expansions;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.configuration.Configuration;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.TagsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;

public record ExpansionGame(Guacamole plugin) {

    public Expansion get() {
        Expansion.Builder builder = Expansion.builder("game");
        builder.globalPlaceholder("name", (argumentQueue, context) -> Tag.inserting(Component.text(plugin.getConfiguration().get(Configuration.GAME_NAME))));
        builder.globalPlaceholder("state", (argumentQueue, context) -> Tag.inserting(plugin.getGameState().getName()));

        builder.globalPlaceholder("prefix", (argumentQueue, context) -> {
            if (argumentQueue.hasNext()) {
                switch (argumentQueue.pop().value()) {
                    case "join" -> {
                        return Tag.inserting(plugin.prefix(Component.text("+", NamedTextColor.GREEN)));
                    }
                    case "quit" -> {
                        return Tag.inserting(plugin.prefix(Component.text("-", NamedTextColor.RED)));
                    }
                    case "dead" -> {
                        return Tag.inserting(plugin.prefix(Component.text("Spectateur", NamedTextColor.DARK_GRAY)));
                    }
                    default -> {
                        return TagsUtils.EMPTY_TAG;
                    }
                }
            } else {
                return Tag.inserting(plugin.prefix(Component.text("Guacamole", NamedTextColor.DARK_GREEN)));
            }
        });

        builder.globalPlaceholder("count", (argumentQueue, context) -> {
            if(argumentQueue.hasNext()) {
                switch (argumentQueue.pop().value()) {
                    case "players" -> {
                        return Tag.inserting(Component.text(plugin.getPlayerManager().getAlivePlayers().size()));
                    }
                    case "teams" -> {
                        return Tag.inserting(Component.text(plugin.getTeamManager().getAliveTeams().size()));
                    }
                    default -> {
                        return TagsUtils.EMPTY_TAG;
                    }
                }
            } else {
                return TagsUtils.EMPTY_TAG;
            }
        });

        return builder.build();
    }
}
