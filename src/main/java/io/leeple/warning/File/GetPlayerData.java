//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.leeple.warning.File;

import java.io.File;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

public class GetPlayerData {
    public static YamlConfiguration config = new YamlConfiguration();
    public static File playerFile = new File("plugins/warning/playerData/");

    public static YamlConfiguration getPlayerConfig(OfflinePlayer player) {
        playerFile = new File("plugins/warning/playerData/" + player.getUniqueId() + ".yml");
        if (!playerFile.exists()) {
            config.options().copyDefaults(true);
            config.addDefault("prefix", 1);
            Config.saveYamlConfiguration();
        }

        config = YamlConfiguration.loadConfiguration(playerFile);
        return config;
    }
}
