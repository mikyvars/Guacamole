package com.michaelyvars.guacamole.data.expansions;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.PlayerData;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public record PlayerExpansion(Guacamole plugin) {

    public Expansion get() {
        Expansion.Builder builder = Expansion.builder("player");
        builder.filter(Player.class);

        builder.audiencePlaceholder("name", (audience, argumentQueue, context) -> {
            PlayerData playerData = playerData(audience);

            if(playerData.getTeam() == null)
                return Tag.inserting(playerData.getName());
            else
                return Tag.inserting(playerData.getName().color(playerData.getTeam().getColor()));
        });

        builder.audiencePlaceholder("team", (audience, argumentQueue, context) -> {
            PlayerData playerData = playerData(audience);

            if (playerData.getTeam() == null)
                return Tag.inserting(Component.text("Aucune", NamedTextColor.WHITE));
            else
                return Tag.inserting(playerData.getTeam().getName());
        });

        return builder.build();
    }

    private PlayerData playerData(Audience audience) {
        return plugin.getPlayerManager().getPlayers().get(audience.get(Identity.UUID).orElse(null));
    }
}
