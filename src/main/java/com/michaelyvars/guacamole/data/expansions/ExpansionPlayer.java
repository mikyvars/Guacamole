package com.michaelyvars.guacamole.data.expansions;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.player.PlayerData;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public record ExpansionPlayer(Guacamole plugin) {

    public Expansion get() {
        Expansion.Builder builder = Expansion.builder("player");
        builder.filter(Player.class);

        builder.audiencePlaceholder("name", (audience, argumentQueue, context) -> {
            PlayerData playerData = playerData(audience);
            return Tag.inserting(playerData.getName());
        });

        return builder.build();
    }

    private PlayerData playerData(Audience audience) {
        return plugin.getPlayerManager().getPlayers().get(audience.get(Identity.UUID).orElse(null));
    }
}
