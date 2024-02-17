package com.michaelyvars.guacamole.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomItem implements Listener {

    private final ItemStack item;

    public CustomItem(Material material) {
        this.item = new ItemStack(material);
    }

    public CustomItem withAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public CustomItem withName(Component name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        return this;
    }

    public CustomItem withLore(List<Component> loreList) {
        List<Component> result = new ArrayList<>();
        loreList.forEach(component -> result.add(component.color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        ItemMeta meta = item.getItemMeta();
        meta.lore(result);
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

    public CustomItem withSkullModel(String model) {
        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());
        playerProfile.getProperties().add(new ProfileProperty("textures", model));
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(playerProfile);
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
