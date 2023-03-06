package be.maksxou.spigotmc;

import be.maksxou.spigotmc.commands.EasyPluginCommand;
import be.maksxou.spigotmc.listeners.EasyPluginListeners;
import be.maksxou.spigotmc.utils.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import java.util.*;

public class EasyPlugin extends JavaPlugin {

    private FileConfiguration fileConfiguration;
    public List<String> forbiddenCommands = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("--------------------------------------------");
        Bukkit.getLogger().info("EasyPlugin by MasqueOù ");
        Bukkit.getLogger().info("Hi ! EasyPlugin is free and opensource.");
        Bukkit.getLogger().info("Thanks for using my plugin.");
        Bukkit.getLogger().info("--------------------------------------------");

        initStats();

        getConfig().options().copyDefaults(true);
        saveConfig();

        rlConfig();

        Objects.requireNonNull(getCommand("easyplugin")).setExecutor(new EasyPluginCommand(this));

        loadForbiddenCommands();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EasyPluginListeners(this), this);

        super.onEnable();
    }

    private void initStats() {
        int pluginId = 17856;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
    }

    public void rlConfig() {
        reloadConfig();
        forbiddenCommands = null;
        fileConfiguration = null;

        fileConfiguration = getConfig();
        loadForbiddenCommands();
    }

    public String getMessage(String s) {
        if(fileConfiguration.get(s) != null) {
            return Objects.requireNonNull(fileConfiguration.get(s)).toString().replace("&", "§");
        } else {
            Bukkit.getLogger().info("---------------------------------------------------------------------------------------------------------------------------");
            Bukkit.getLogger().info("If you have several errors at this time, it probably comes from the \"config.yml\" file of EasyPlugin. Check or contact me.");
            Bukkit.getLogger().info("---------------------------------------------------------------------------------------------------------------------------");
        }
        return "Oh no! An error! Check your logs quickly!";
    }

    public void loadForbiddenCommands() {
        forbiddenCommands = fileConfiguration.getStringList("blocked_commands");
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
