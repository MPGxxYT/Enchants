package me.mortaldev.enchants.modules;

import me.mortaldev.enchants.utils.base.LoreUtil;
import me.mortaldev.enchants.utils.base.TextFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchant {

    private static final int DEFAULT_ENCHANT_INDEX = 1;

    public static void applyEnchant(Player player, ItemStack item, Enchants enchant, Integer level) {
        if (level == null || level <= 0) {
            player.sendMessage(TextFormat.format("&cGiven level is invalid."));
            return;
        }
        if (!Tag.ITEMS_PICKAXES.isTagged(item.getType())) {
            player.sendMessage(TextFormat.format("&cThis enchant can only be applied to pickaxes."));
            return;
        }

        List<Component> itemLoreList = item.lore();
        if (itemLoreList == null || itemLoreList.isEmpty()) {
            itemLoreList = new ArrayList<>();
            for (int i = 0; i < DEFAULT_ENCHANT_INDEX; i++) {
                itemLoreList.add(TextFormat.format("&7-"));
            }
            itemLoreList.add(TextFormat.format(enchant.display + " " + level));
            addEnchantToItem(player, item, itemLoreList, enchant, level);
            return;
        }

        List<String> enchantsIDList = Enchants.getIDList();
        boolean lastWasEnchant = false;

        for (int i = DEFAULT_ENCHANT_INDEX; i < itemLoreList.size(); i++) {
            Component component = itemLoreList.get(i);

            if (component instanceof TextComponent textComponent) {
                String content = textComponent.content();
                String enchantID = enchant.getId();
                if (content.contains(enchantID)) {
                    int currentLevel = Integer.parseInt(content.substring(enchantID.length() + 1)); // gets the level of the current enchant
                    if (currentLevel >= level) {
                        player.sendMessage(TextFormat.format("&cThis enchant is already applied to the item."));
                    } else {
                        LoreUtil.replaceLore(itemLoreList, i, TextFormat.format(enchant.display + " " + level));
                        addEnchantToItem(player, item, itemLoreList, enchant, level);
                    }
                    return;
                } else {
                    String contentEnchantID = content.replaceAll("( \\d+)", "");
                    if (enchantsIDList.contains(contentEnchantID)) {
                        if (i + 1 >= itemLoreList.size()) {
                            itemLoreList.add(TextFormat.format(enchant.display + " " + level));
                            addEnchantToItem(player, item, itemLoreList, enchant, level);
                            return;
                        }
                        lastWasEnchant = true;
                    } else if (lastWasEnchant) {
                        itemLoreList.add(i, TextFormat.format(enchant.display + " " + level));
                        addEnchantToItem(player, item, itemLoreList, enchant, level);
                        return;
                    }
                }
            }
        }
    }

    private static void addEnchantToItem(Player player, ItemStack item, List<Component> itemLoreList, Enchants enchant, Integer level) {
        if (enchant.getMaxLevel() < 0 || level <= enchant.getMaxLevel()) {
            LoreUtil.applyLore(itemLoreList, item);
            player.sendMessage(TextFormat.format("&aEnchant applied successfully."));
        } else {
            player.sendMessage(TextFormat.format("&cFailed to enchant. (At or above max level)"));
        }
    }
}

