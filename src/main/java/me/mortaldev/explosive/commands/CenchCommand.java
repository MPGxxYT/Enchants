package me.mortaldev.explosive.commands;

import me.mortaldev.explosive.modules.CustomEnchant;
import me.mortaldev.explosive.modules.Enchants;
import me.mortaldev.explosive.utils.base.CommandHandler;
import me.mortaldev.explosive.utils.base.TextFormat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CenchCommand {
    public CenchCommand() {
        new CommandHandler("cench", -1, true) {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
                Player player = (Player) sender;
                switch (args.length) {
                    case 0 -> {
                        player.sendMessage(TextFormat.format("&7/cench &c<enchant>&7 <level>"));
                        return true;
                    }
                    case 1 -> {
                        player.sendMessage(TextFormat.format("&7/cench <enchant>&c <level>"));
                        return true;
                    }
                    case 2 -> {
                        Enchants enchantFromArg = Enchants.getEnchantByID(args[0]);
                        if (enchantFromArg != null){
                            ItemStack item = player.getInventory().getItemInMainHand();
                            int level;
                            try {
                                level = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                player.sendMessage(TextFormat.format("&cGiven level is invalid."));
                                return true;
                            }
                            CustomEnchant.applyEnchant(player, item, enchantFromArg, level);
                        } else {
                            player.sendMessage(TextFormat.format("&cThat enchant does not exist."));
                        }
                        return true;
                    }
                }
                return true;
            }

            @Override
            public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
                ArrayList<String> list = new ArrayList<>();
                if (args.length == 1) {
                    for (Enchants value : Enchants.values()) {
                        list.add(value.getId());
                    }
                }
                return list;
            }

            @Override
            public @NotNull String getUsage() {
                return "/cench <enchant> <level>";
            }

            @Override
            public String getPermission() {
                return "explosive.cench";
            }

            @Override
            public @NotNull String getDescription() {
                return "Adds a custom enchant with level to held item.";
            }

            @Override
            public @NotNull List<String> getAliases() {
                return new ArrayList<>();
            }
        };


    }


}

