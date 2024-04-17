package me.mortaldev.enchants.modules;

import me.mortaldev.enchants.utils.base.LoreUtil;
import me.mortaldev.enchants.utils.base.TextFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchant {

    // Index to start the enchants at in the lore.
    private static final int DEFAULT_ENCHANT_INDEX = 1;

    // Error Messages
    private static final String INVALID_LEVEL_ERROR = "&cGiven level is invalid.";
    private static final String PICKAXE_ONLY_ERROR = "&cThis enchant can only be applied to pickaxes.";
    private static final String ALREADY_APPLIED_ERROR = "&cThis enchant is already applied to the item.";
    private static final String GENERAL_ERROR = "&cAn error has occurred.";
    private static final String AT_MAX_LEVEL_ERROR = "&cFailed to enchant. (At or above max level)";
    private static final String SUCCESS_MSG = "&aEnchant applied successfully.";

    /**
     * Applies the given enchant and level to the specified item for the player.
     * This method assumes that both the item and enchant are not null.
     *
     * @param player the player who will receive the enchanted item
     * @param item the item to be enchanted, assumed to be not null
     * @param enchant the enchant to be applied, assumed to be not null
     * @param level the level of the enchant to be applied
     */
    public static void applyEnchant(Player player, ItemStack item, Enchants enchant, Integer level) {
        if (level == null || level < 1) {
            player.sendMessage(TextFormat.format(INVALID_LEVEL_ERROR));
        } else if (item == null) {
            Bukkit.getLogger().warning("Failed to enchant: Item is null");
            player.sendMessage(TextFormat.format(GENERAL_ERROR));
        } else if (!Tag.ITEMS_PICKAXES.isTagged(item.getType())) {
            player.sendMessage(TextFormat.format(PICKAXE_ONLY_ERROR));
        } else {
            List<Component> itemLoreList = getItemListWithEnchant(item, enchant, level, player);
            if (itemLoreList != null) {
                addEnchantToItem(player, item, itemLoreList, enchant, level);
            }
        }
    }

    /**
     * Checks the lore of the given item and either adds the new enchant
     * or updates the existing one with a higher level if possible.
     * Returns null if the enchanting process encounters an error.
     *
     * @param item the item to be enchanted
     * @param enchant the enchant to be applied
     * @param level the level of the enchant to be applied
     * @param player the player receiving the error messages
     * @return a list of lore components after applying the enchant, or null if an error occurred
     */
    private static List<Component> getItemListWithEnchant(ItemStack item, Enchants enchant, Integer level, Player player) {
        List<Component> itemLoreList = item.lore();

        if (itemLoreList == null || itemLoreList.isEmpty()) {
            itemLoreList = new ArrayList<>();
            for (int i = 0; i < DEFAULT_ENCHANT_INDEX; i++) {
                itemLoreList.add(TextFormat.format("&7-"));
            }
            // Add the new enchant at the end of the lore list
            itemLoreList.add(TextFormat.format(enchant.display + " " + level));

        } else {

            List<String> enchantsIDList = Enchants.getIDList();
            boolean lastWasEnchant = false;

            for (int i = DEFAULT_ENCHANT_INDEX; i < itemLoreList.size(); i++) {
                Component component = itemLoreList.get(i);

                if (!(component instanceof TextComponent)) {
                    Bukkit.getLogger().warning("Failed to enchant: Invalid lore format found on pickaxe.");
                    player.sendMessage(TextFormat.format(GENERAL_ERROR));
                    return null; // Invalid lore format error
                }

                String content = ((TextComponent) component).content();
                String enchantID = enchant.getId();
                // If the current enchant is found in the lore
                if (content.contains(enchantID)) {
                    int currentLevel = getCurrentLevel(content, enchantID, player);

                    if (currentLevel == -1) {
                        return null; // Parsing the enchant level failed
                    } else if (currentLevel >= level) {
                        player.sendMessage(TextFormat.format(ALREADY_APPLIED_ERROR));
                        return null; // Enchant already exists at this or higher level
                    } else {
                        // Replace the current enchant level with the new higher level
                        LoreUtil.replaceLore(itemLoreList, i, TextFormat.format(enchant.display + " " + level));
                        break;
                    }
                } else {
                    String contentEnchantID = content.replaceAll("( \\d+)", "");
                    // If the current line contains an enchant
                    if (enchantsIDList.contains(contentEnchantID)) {
                        // If there is no next line, add new enchant at the end
                        if (i + 1 >= itemLoreList.size()) {
                            itemLoreList.add(TextFormat.format(enchant.display + " " + level));
                            break;
                        }
                        lastWasEnchant = true;
                    } else if (lastWasEnchant) {
                        // Add new enchant at the end of the enchant list
                        itemLoreList.add(i, TextFormat.format(enchant.display + " " + level));
                        break;
                    }
                }
            }
        }
        return itemLoreList;
    }



    private static void addEnchantToItem(Player player, ItemStack item, List<Component> itemLoreList, Enchants enchant, Integer level) {
        if (enchant.getMaxLevel() < 0 || level <= enchant.getMaxLevel()) {
            LoreUtil.applyLore(itemLoreList, item);
            player.sendMessage(TextFormat.format(SUCCESS_MSG));
        } else {
            player.sendMessage(TextFormat.format(AT_MAX_LEVEL_ERROR));
        }
    }

    private static int getCurrentLevel(String content, String enchantID, Player player) {
        int currentLevel;
        try {
            currentLevel = Integer.parseInt(content.substring(enchantID.length() + 1));
        } catch (NumberFormatException e) {
            Bukkit.getLogger().warning("Failed to enchant: Invalid enchant level found on pickaxe.");
            player.sendMessage(TextFormat.format(GENERAL_ERROR));
            currentLevel = -1;
        }
        return currentLevel;
    }
}

