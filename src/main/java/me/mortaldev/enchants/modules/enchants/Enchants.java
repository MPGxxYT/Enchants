package me.mortaldev.enchants.modules.enchants;

import java.util.ArrayList;
import java.util.List;


/**
 * The Enchants class represents different types of enchantments.
 */
public enum Enchants {
    EXPLOSIVE("Explosive", "&cExplosive", -1,"Explodes all blocks around the broken block."),
    FREEZE("Freeze", "&3Freeze", 5,"Will freeze the blocks around the broken blocks.");

    final String id;
    final String display;
    final int maxLevel;
    final String description;

    Enchants(String id, String display, int maxLevel, String description) {
        this.id = id;
        this.display = display;
        this.maxLevel = maxLevel;
        this.description = description;
    }

    /**
     * Returns a list of all IDs of the Enchants.
     *
     * @return a list of all IDs of the Enchants
     */
    public static List<String> getIDList(){
        List<String> list = new ArrayList<>();
        for (Enchants value : Enchants.values()) {
            list.add(value.id);
        }
        return list;
    }


    /**
     * Retrieves the Enchants enum value corresponding to the given ID.
     *
     * @param id the ID of the enchantment
     * @return the Enchants enum value with the given ID, or null if no matching enchantment is found
     */
    public static Enchants getEnchantByID(String id){
        for (Enchants value : Enchants.values()) {
            if (value.id.equalsIgnoreCase(id)) {
                return value;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getDescription() {
        return description;
    }
}
