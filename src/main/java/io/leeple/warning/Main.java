package io.leeple.warning;

import io.leeple.warning.Command.Command;
import io.leeple.warning.DataBase.DbSetting;
import io.leeple.warning.File.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    public Main() {
    }

    public void onEnable() {
        plugin = this;
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
        Bukkit.getPluginCommand("warning").setExecutor(new Command());
        if (Config.getString("saveData").equals("DB")) {
            DbSetting.start();
        }
    }
}
