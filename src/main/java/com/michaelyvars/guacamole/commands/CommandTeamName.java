package com.michaelyvars.guacamole.commands;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.GameState;
import com.michaelyvars.guacamole.data.PlayerData;
import io.github.miniplaceholders.api.MiniPlaceholders;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.WrongUsage;
import me.mattstudios.mf.base.CommandBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command("teamname")
@SuppressWarnings("unused")
public class CommandTeamName extends CommandBase {

    private final Guacamole plugin;

    public CommandTeamName(@NotNull Guacamole plugin) {
        this.plugin = plugin;
    }

    @Default
    @WrongUsage("&cUtilisation correcte: /teamname <nom>")
    public void defaultCommand(Player player, String name) {
        if (plugin.getGameState() == GameState.WAITING) {
            PlayerData playerData = plugin.getPlayerManager().getPlayers().get(player.getUniqueId());

            if (playerData == null)
                return;

            if (playerData.getTeam() == null) {
                player.sendMessage(plugin.prefix(Component.text("Erreur", NamedTextColor.RED))
                        .append(Component.text("Tu ne fait partie d'aucune équipe.")));
                return;
            }

            if (name.length() > 16) {
                player.sendMessage(plugin.prefix(Component.text("Erreur", NamedTextColor.RED))
                        .append(Component.text("Le nom de ton équipe ne peut excéder 16 caractères.")));
            } else {
                playerData.getTeam().setName(Component.text(name));
                player.sendMessage(MiniMessage.miniMessage().deserialize("<team_prefix>Ton équipe s'appelle désormais <player_team>",
                        Placeholder.component("team_prefix", plugin.prefix(Component.text("Équipe", playerData.getTeam().getColor()))),
                        MiniPlaceholders.getAudiencePlaceholders(player)));
            }

        } else {
            player.sendMessage(plugin.prefix(Component.text("Erreur", NamedTextColor.RED))
                    .append(Component.text("Cette commande ne peut être executée que dans le lobby.")));
        }
    }
}
