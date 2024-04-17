package me.mortaldev.explosive;

import me.mortaldev.explosive.commands.CenchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static Main plugin;
    static String label = "Explosive";

    @Override
    public void onEnable() {

        // DEPENDENCIES

//        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null){
//            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
//            Bukkit.getPluginManager().disablePlugin(this);
//            return;
//        }

        plugin = this;

        // DATA FOLDER

        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        // CONFIGS
//        WildConfig.loadConfig(true);
//        MainConfig.loadConfig(true);


        // Events
//        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
//        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);


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