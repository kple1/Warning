//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.leeple.warning.Command;

import io.leeple.warning.Ban.BanPlayer;
import io.leeple.warning.DataBase.CRUD;
import io.leeple.warning.DataBase.DbSetting;
import io.leeple.warning.File.Config;
import io.leeple.warning.File.GetPlayerData;
import io.leeple.warning.Utils.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Command implements CommandExecutor, TabExecutor {
    public static String warningPrefix = Color.chat("[ &cWarning &f] ");
    public static String sqlErrorPrefix = Color.chat("&6SQL &cError&f: ");

    public Command() {
    }

    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            YamlConfiguration config = GetPlayerData.getPlayerConfig(player);
            String var10001;
            if (strings.length == 0) {
                if (Config.getString("saveData").equals("Config")) {
                    var10001 = warningPrefix;
                    String var10002 = player.getName();
                    int var10004 = config.getInt("prefix");
                    player.sendMessage(var10001 + var10002 + "님의 경고: " + config.getInt(var10004 - 1 + ".result") + "회");
                } else if (Config.getString("saveData").equals("DB")) {
                    var10001 = warningPrefix;
                    player.sendMessage(var10001 + player.getName() + "님의 경고: " + CRUD.getWarningCount(player) + "회");
                }

                return true;
            }

            Player getBanPlayer = Bukkit.getPlayer(strings[1]);
            if (getBanPlayer == null) {
                player.sendMessage(getBanPlayer.getName() + "이(가) 확인되지 않았습니다.");
                return true;
            }

            if (player.hasPermission(Config.getString("permission"))) {
                try {
                    if (strings[0].equals("추가")) {
                        if (Config.getString("saveData").equals("DB")) {
                            BanPlayer.banCount(getBanPlayer, strings[3]);
                            CRUD.insertWarning("w_add", strings[1], Integer.parseInt(strings[2]), strings[3], CRUD.getWarningCount(getBanPlayer) + Integer.parseInt(strings[2]));
                            player.sendMessage(warningPrefix + strings[2] + "만큼 경고를 추가했습니다.");
                        } else if (Config.getString("saveData").equals("Config")) {
                            BanPlayer.banCount(getBanPlayer, strings[3]);
                            Config.saveWarningData(strings[1], Integer.parseInt(strings[2]), strings[3], true);
                            player.sendMessage(warningPrefix + strings[2] + "만큼 경고를 추가했습니다.");
                        }
                    }

                    if (strings[0].equals("차감")) {
                        if (Config.getString("saveData").equals("DB")) {
                            CRUD.insertWarning("w_dis", strings[1], Integer.parseInt(strings[2]), strings[3], CRUD.getWarningCount(getBanPlayer) - Integer.parseInt(strings[2]));
                            player.sendMessage(warningPrefix + strings[2] + "만큼 경고를 삭감했습니다.");
                        } else if (Config.getString("saveData").equals("Config")) {
                            Config.saveWarningData(strings[1], Integer.parseInt(strings[2]), strings[3], false);
                            player.sendMessage(warningPrefix + strings[2] + "만큼 경고를 삭감했습니다.");
                        }
                    }

                    if (strings[0].equals("reloadDB")) {
                        DbSetting.start();
                    }
                } catch (SQLException var9) {
                    var10001 = warningPrefix;
                    player.sendMessage(var10001 + sqlErrorPrefix + var9.getMessage() + " From Command Class");
                }

                Config.saveYamlConfiguration();
            } else {
                player.sendMessage(warningPrefix + "해당 권한이 없습니다.");
            }
        }

        return false;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return Collections.emptyList();
        } else {
            ArrayList tabList = new ArrayList();
            String[] tabStrings = new String[]{"추가", "차감"};
            if (player.hasPermission(Config.getString("permission"))) {
                if (strings.length == 1) {
                    for(int i = 0; i < 2; ++i) {
                        tabList.add(i, tabStrings[i]);
                    }

                    return (List)StringUtil.copyPartialMatches(strings[0], tabList, new ArrayList());
                }

                if (strings.length == 2) {
                    tabList.add("[Player]");
                    return (List)StringUtil.copyPartialMatches(strings[1], tabList, new ArrayList());
                }

                if (strings.length == 3) {
                    tabList.add("[D/Count]");
                    return (List)StringUtil.copyPartialMatches(strings[2], tabList, new ArrayList());
                }

                if (strings.length == 4) {
                    tabList.add("[Reason]");
                }
            }

            return tabList;
        }
    }
}
