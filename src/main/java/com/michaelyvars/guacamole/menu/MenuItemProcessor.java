package com.michaelyvars.guacamole.menu;

import com.michaelyvars.guacamole.utils.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.odalitadevelopments.menus.menu.providers.MenuProvider;
import nl.odalitadevelopments.menus.pagination.IPagination;
import nl.odalitadevelopments.menus.providers.providers.DefaultItemProvider;
import nl.odalitadevelopments.menus.scrollable.Scrollable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuItemProcessor implements DefaultItemProvider {

    @Override
    public @NotNull ItemStack closeItem() {
        return new CustomItem(Material.BARRIER).withName(Component.text("Fermer", NamedTextColor.RED)).build();
    }

    @Override
    public @NotNull ItemStack backItem(@NotNull MenuProvider menuProvider) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack nextPageItem(@NotNull IPagination<?, ?> iPagination) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack previousPageItem(@NotNull IPagination<?, ?> iPagination) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack scrollUpItem(@NotNull Scrollable scrollable) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack scrollDownItem(@NotNull Scrollable scrollable) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack scrollLeftItem(@NotNull Scrollable scrollable) {
        return new CustomItem(Material.AIR).build();
    }

    @Override
    public @NotNull ItemStack scrollRightItem(@NotNull Scrollable scrollable) {
        return new CustomItem(Material.AIR).build();
    }
}
