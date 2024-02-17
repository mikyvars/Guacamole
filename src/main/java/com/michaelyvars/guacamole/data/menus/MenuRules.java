package com.michaelyvars.guacamole.data.menus;

import com.michaelyvars.guacamole.Guacamole;
import com.michaelyvars.guacamole.configuration.Configuration;
import com.michaelyvars.guacamole.utils.CustomItem;
import com.michaelyvars.guacamole.utils.TimeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.odalitadevelopments.menus.annotations.Menu;
import nl.odalitadevelopments.menus.contents.MenuContents;
import nl.odalitadevelopments.menus.items.DisplayItem;
import nl.odalitadevelopments.menus.menu.providers.GlobalMenuProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@Menu(title = "&2Règles")
public class MenuRules implements GlobalMenuProvider {

    private final Guacamole plugin;

    public MenuRules(Guacamole plugin) {
        this.plugin = plugin;
    }

    public static ItemStack getItem() {
        return new CustomItem(Material.BOOK).withName(Component.text("Règles", NamedTextColor.DARK_GREEN)).build();
    }

    @Override
    public void onLoad(@NotNull MenuContents menuContents) {
        Configuration configuration = plugin.getConfiguration();
        menuContents.fill(DisplayItem.of(new CustomItem(Material.GREEN_STAINED_GLASS_PANE).withName(Component.empty()).build()));
        menuContents.set(11, DisplayItem.of(new CustomItem(Material.CLOCK).withName(plugin.prefix(Component.text("Timers", NamedTextColor.AQUA))).withLore(List.of(
                miniMessage().deserialize("│ <green>Épisode <dark_gray>» <yellow><value>", Placeholder.parsed("value", TimeUtils.getFormattedTime(configuration.get(Configuration.TIMER_EPISODE)))),
                miniMessage().deserialize("│ <green>Invincibilité <dark_gray>» <yellow><value>", Placeholder.parsed("value", TimeUtils.getFormattedTime(configuration.get(Configuration.TIMER_INVINCIBILITY)))),
                miniMessage().deserialize("│ <green>PVP <dark_gray>» <yellow><value>", Placeholder.parsed("value", TimeUtils.getFormattedTime(configuration.get(Configuration.TIMER_PVP)))),
                miniMessage().deserialize("│ <green>Taupes <dark_gray>» <yellow><value>", Placeholder.parsed("value", TimeUtils.getFormattedTime(configuration.get(Configuration.TIMER_MOLES)))),
                miniMessage().deserialize("│ <green>Bordure <dark_gray>» <yellow><value>", Placeholder.parsed("value", TimeUtils.getFormattedTime(configuration.get(Configuration.TIMER_BORDER))))
        )).build()));
        menuContents.set(12, DisplayItem.of(new CustomItem(Material.BARRIER).withName(plugin.prefix(Component.text("Bordure", NamedTextColor.AQUA))).withLore(List.of(
                miniMessage().deserialize("│ <green>Début <dark_gray>» <yellow>+<value>/-<value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.BORDER_START)))),
                miniMessage().deserialize("│ <green>Fin <dark_gray>» <yellow>+<value>/-<value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.BORDER_END)))),
                miniMessage().deserialize("│ <green>Vitesse <dark_gray>» <yellow><value> b/s", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.BORDER_SPEED))))
        )).build()));
        menuContents.set(13, DisplayItem.of(new CustomItem(Material.ENCHANTING_TABLE).withName(plugin.prefix(Component.text("Enchantements", NamedTextColor.AQUA))).withLore(List.of(
                miniMessage().deserialize("│ <green>Protection <dark_gray>» <yellow>Niveau <value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.ENCHANTMENT_PROTECTION)))),
                miniMessage().deserialize("│ <green>Tranchant <dark_gray>» <yellow>Niveau <value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.ENCHANTMENT_SHARPNESS)))),
                miniMessage().deserialize("│ <green>Recul <dark_gray>» <yellow>Niveau <value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.ENCHANTMENT_KNOCKBACK)))),
                miniMessage().deserialize("│ <green>Puissance <dark_gray>» <yellow>Niveau <value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.ENCHANTMENT_POWER)))),
                miniMessage().deserialize("│ <green>Frappe <dark_gray>» <yellow>Niveau <value>", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.ENCHANTMENT_PUNCH))))
        )).build()));
        menuContents.set(14, DisplayItem.of(new CustomItem(Material.APPLE).withName(plugin.prefix(Component.text("Drops", NamedTextColor.AQUA))).withLore(List.of(
                miniMessage().deserialize("│ <green>Pommes <dark_gray>» <yellow><value>%", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.RATE_APPLE)))),
                miniMessage().deserialize("│ <green>Silex <dark_gray>» <yellow><value>%", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.RATE_FLINT)))),
                miniMessage().deserialize("│ <green>Perles de l'ender <dark_gray>» <yellow><value>%", Placeholder.parsed("value", String.valueOf(configuration.get(Configuration.RATE_ENDER_PEARL))))
        )).build()));
    }
}
