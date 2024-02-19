package com.michaelyvars.guacamole.data.menus;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.data.PlayerData;
import com.michaelyvars.guacamole.data.TeamData;
import com.michaelyvars.guacamole.utils.CustomItem;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import nl.odalitadevelopments.menus.annotations.Menu;
import nl.odalitadevelopments.menus.contents.MenuContents;
import nl.odalitadevelopments.menus.items.DisplayItem;
import nl.odalitadevelopments.menus.items.UpdatableItem;
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@Menu(title = "&2Choisir une équipe")
public final class TeamMenu implements PlayerMenuProvider {

    private final Guacamole plugin;

    public TeamMenu(Guacamole plugin) {
        this.plugin = plugin;
    }

    public static ItemStack getItem() {
        return new CustomItem(Material.WHITE_BANNER).withName(Component.text("Choisir une équipe", NamedTextColor.DARK_GREEN)).build();
    }

    @Override
    public void onLoad(@NotNull Player player, @NotNull MenuContents menuContents) {
        menuContents.fillRow(2, DisplayItem.of(new CustomItem(Material.GREEN_STAINED_GLASS_PANE).withName(Component.empty()).build()));

        plugin.getTeamManager().getTeams().forEach(teamData -> {
            if (menuContents.firstEmptySlot().isEmpty())
                return;

            menuContents.add(UpdatableItem.of(() -> getTeamItem(teamData), inventoryClickEvent -> {
                PlayerData playerData = plugin.getPlayerManager().getPlayers().get(player.getUniqueId());

                if (playerData == null)
                    return;

                if (playerData.getTeam() != null) {
                    playerData.getTeam().getPlayers().remove(playerData);
                    plugin.getTeamManager().getBukkitTeam(playerData.getTeam()).removeEntry(player.getName());
                }

                playerData.setTeam(teamData);
                playerData.getTeam().getPlayers().add(playerData);
                plugin.getTeamManager().getBukkitTeam(playerData.getTeam()).addEntry(player.getName());
                player.sendMessage(MiniMessage.miniMessage().deserialize("<game_prefix>Tu as rejoint l'équipe <player_team><white>.",
                        MiniPlaceholders.getGlobalPlaceholders(),
                        MiniPlaceholders.getAudienceGlobalPlaceholders(player)));
            }, 1));
        });
    }

    private ItemStack getTeamItem(TeamData teamData) {
        return new CustomItem(teamData.getIcon())
                .withName(plugin.prefix(teamData.getName()))
                .withLore(teamData.getPlayers().stream().map(playerData -> Component.text("│", NamedTextColor.GRAY).appendSpace().append(playerData.getName())
                        .decoration(TextDecoration.ITALIC, false)).collect(Collectors.toList()))
                .build();
    }
}
