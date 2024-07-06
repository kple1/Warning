//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.leeple.warning.File;

import io.leeple.warning.Main;
import java.io.IOException;

public class Config {
    public Config() {
    }
    public static void saveYamlConfiguration() {
        try {
            GetPlayerData.config.save(GetPlayerData.playerFile);
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }

    public static void saveWarningData(String player, int count, String reason, boolean cd) {
        int prefix = GetPlayerData.config.getInt("prefix");
        GetPlayerData.config.set("" + prefix + ".player", player);
        GetPlayerData.config.set("" + prefix + ".w_add", cd ? count : 0);
        GetPlayerData.config.set("" + prefix + ".w_dis", cd ? 0 : count);
        GetPlayerData.config.set("" + prefix + ".reason", reason);
        GetPlayerData.config.set("" + prefix + ".result", cd ? GetPlayerData.config.getInt(prefix - 1 + ".result") + count : GetPlayerData.config.getInt(prefix - 1 + ".result") - count);
        GetPlayerData.config.set("prefix", prefix + 1);
        saveYamlConfiguration();
    }

    public static String getString(String path) {
        return Main.plugin.getConfig().getString(path);
    }

    public static int getInt(String path) {
        return Main.plugin.getConfig().getInt(path);
    }
}
