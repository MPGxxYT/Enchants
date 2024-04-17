package me.mortaldev.enchants.modules;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getIDList(){
        List<String> list = new ArrayList<>();
        for (Enchants value : Enchants.values()) {
            list.add(value.id);
        }
        return list;
    }

    public static Enchants getEnchantByID(String string){
        for (Enchants value : Enchants.values()) {
            if (value.id.equalsIgnoreCase(string)) {
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
