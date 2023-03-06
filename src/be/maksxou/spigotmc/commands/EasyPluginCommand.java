package be.maksxou.spigotmc.commands;

import be.maksxou.spigotmc.EasyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EasyPluginCommand implements CommandExecutor {

    private final EasyPlugin easyPlugin;

    public EasyPluginCommand(EasyPlugin easyPlugin) {
        this.easyPlugin = easyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(easyPlugin.getMessage("message.sender_isnt_player"));
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.sendMessage(easyPlugin.getMessage("main.prefix") + " " + ChatColor.RED + "EasyPlugin by maksxou is here! ");
            player.sendMessage(easyPlugin.getMessage("main.prefix") + " " + ChatColor.YELLOW + "/easyplugin reload " + ChatColor.RED + "> Reload the configuration file");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission(easyPlugin.getMessage("permission.reload_plugin"))) {
                player.sendMessage(easyPlugin.getMessage("main.prefix") + " " + easyPlugin.getMessage("message.not_permission"));
                return false;
            }
            player.sendMessage(easyPlugin.getMessage("main.prefix") + " " + easyPlugin.getMessage("message.success_reloading_config"));
            easyPlugin.rlConfig();
            return false;
        } else {
            player.sendMessage(easyPlugin.getMessage("main.prefix") + " " + easyPlugin.getMessage("message.bad_usage"));
            return false;
        }

        return false;
    }
}
