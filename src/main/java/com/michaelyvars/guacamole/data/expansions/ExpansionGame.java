package com.michaelyvars.guacamole.data.expansions;

import com.michaelyvars.guacamole.Guacamole;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.TagsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;

public record ExpansionGame(Guacamole plugin) {

    public Expansion get() {
        Expansion.Builder builder = Expansion.builder("game");

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

        return builder.build();
    }
}
