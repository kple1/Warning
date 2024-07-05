//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.leeple.warning.Ban;

import io.leeple.warning.DataBase.CRUD;
import io.leeple.warning.File.Config;
import io.leeple.warning.File.GetPlayerData;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BanPlayer {
    public BanPlayer() {
    }

    public static void banPlayerForDuration(Player player, String reason, long days, long hours, long minutes, long seconds) {
        long banEndTime = System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L + hours * 60L * 60L * 1000L + minutes * 60L * 1000L + seconds * 1000L;
        Bukkit.getBanList(Type.NAME).addBan(player.getName(), reason, new Date(banEndTime), (String)null);
        player.kickPlayer(reason);
    }

    public static void banCount(Player getBanPlayer, String reason) {
        YamlConfiguration config = GetPlayerData.getPlayerConfig(getBanPlayer);
        int var10000;
        if (Config.getString("saveData").equals("DB")) {
            var10000 = CRUD.getWarningCount(getBanPlayer);
        } else {
            int var10001 = config.getInt("prefix");
            var10000 = config.getInt(var10001 - 1 + ".result");
        }

        int warningCount = var10000;

        for(int i = 1; i <= 4; ++i) {
            int banCount = Config.getInt("ban" + i + ".count");
            if (warningCount == banCount - 1) {
                int day = Config.getInt("ban" + i + ".day");
                int hour = Config.getInt("ban" + i + ".hour");
                int min = Config.getInt("ban" + i + ".min");
                int sec = Config.getInt("ban" + i + ".sec");
                banPlayerForDuration(getBanPlayer, reason, (long)day, (long)hour, (long)min, (long)sec);
                break;
            }
        }

    }
}
