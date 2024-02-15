package com.michaelyvars.guacamole.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class CustomItem implements Listener {

    private final ItemStack item;

    public CustomItem(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public CustomItem(Material material) {
        this(material, 1);
    }

    public CustomItem withName(Component name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withLore(Component lore) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(List.of(lore));
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withLore(List<Component> loreList) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(loreList);
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withDyeColor(DyeColor dyeColor) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(dyeColor.getColor());
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withSkullOwner(UUID uuid) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withItemFlag(ItemFlag value) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(value);
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem setUnbreakable(boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
