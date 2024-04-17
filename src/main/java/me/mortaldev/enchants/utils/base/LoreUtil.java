package me.mortaldev.enchants.utils.base;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LoreUtil {

    /**
     * Applies the given list of lore components to the specified ItemStack. The method updates the lore of the item.
     *
     * @param loreList the list of lore components to be applied
     * @param itemStack the ItemStack to which the lore components will be applied
     */
    public static void applyLore(List<Component> loreList, ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(loreList);
        itemStack.setItemMeta(meta);
    }

    /**
     * Replaces a component in the provided list of lore components at the specified index.
     *
     * @param loreList the list of lore components
     * @param index the index at which the component should be replaced
     * @param replacement the replacement component
     */
    public static void replaceLore(List<Component> loreList, int index, Component replacement){
        loreList.remove(index);
        loreList.add(index, replacement);
    }

    /**
     * Replaces a component in the provided list of lore components.
     *
     * @param loreList the list of lore components
     * @param find the component to be replaced
     * @param replacement the replacement component
     */
    public static void replaceLore(List<Component> loreList, Component find, Component replacement){
        int index = loreList.indexOf(find);
        loreList.remove(find);
        loreList.add(replacement);
    }
}
