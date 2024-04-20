package me.mortaldev.enchants.listeners;

import me.mortaldev.enchants.modules.CustomEnchant;
import me.mortaldev.enchants.modules.enchants.Enchants;
import me.mortaldev.enchants.modules.enchants.actions.Explosive;
import me.mortaldev.enchants.modules.enchants.actions.Freeze;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BreakBlock implements Listener {

    /**
     * Handles the event when a block is broken by a player.
     *
     * @param event the BlockBreakEvent
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!Tag.ITEMS_PICKAXES.isTagged(item.getType())) {
            return;
        }
        List<Component> itemLoreList = item.lore();
        if (itemLoreList == null) {
            return;
        }

        // Reads the lore and applies the appropriate enchant effect.
        for (Component component : itemLoreList) {
            if (!(component instanceof TextComponent)) {
                return;
            }
            String loreContent = ((TextComponent) component).content();
            if (loreContent.contains(Enchants.EXPLOSIVE.getId())) {
                new Explosive(
                        event.getBlock(), CustomEnchant.getCurrentLevel(loreContent, Enchants.EXPLOSIVE.getId()))
                        .explode();
            } else if (loreContent.contains(Enchants.FREEZE.getId())) {
                new Freeze();
            }
        }
    }
}
