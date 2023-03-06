package be.maksxou.spigotmc.listeners;

import be.maksxou.spigotmc.EasyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EasyPluginListeners implements Listener {

    private final EasyPlugin easyPlugin;

    public EasyPluginListeners(EasyPlugin easyPlugin) {
        this.easyPlugin = easyPlugin;
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String command = event.getMessage();

        if(Boolean.parseBoolean(easyPlugin.getMessage("main.allow_perm_use_blocked_commands"))
                && player.hasPermission(easyPlugin.getMessage("permission.view_blocked_commands"))) return;

        for(int i = 0; i < easyPlugin.forbiddenCommands.size(); i++){
            if(command.startsWith("/") && command.replace("/", "").toLowerCase().equals(easyPlugin.forbiddenCommands.get(i).replace("/", ""))){
                player.sendMessage(easyPlugin.getMessage("message.use_forbidden_command"));
                event.setCancelled(true);
                break;
            }
        }
    }
}
