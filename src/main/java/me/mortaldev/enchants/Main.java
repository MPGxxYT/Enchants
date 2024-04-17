package me.mortaldev.enchants;

import me.mortaldev.enchants.commands.CenchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static Main plugin;
    static String label = "Explosive";

    @Override
    public void onEnable() {

        plugin = this;

        // DATA FOLDER

        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        // COMMANDS

        new CenchCommand();

        getLogger().info(label + " Enabled");

    }

    @Override
    public void onDisable() {
        getLogger().info(label + " Disabled");
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static String getLabel() {
        return label;
    }
}