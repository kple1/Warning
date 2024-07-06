//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.leeple.warning.DataBase;

import io.leeple.warning.Command.Command;
import io.leeple.warning.File.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class DbSetting {
    public static final String JDBC_URL;
    public static final String USER;
    public static final String PASSWORD;
    public static final String DB_NAME;
    public static final Connection connection;
    public static Statement statement;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            DB_NAME = Config.getString("mysql.dbName");
            JDBC_URL = "jdbc:mysql://" + Config.getString("mysql.address") + ":" + Config.getString("mysql.port") + "/";
            USER = Config.getString("mysql.user");
            PASSWORD = Config.getString("mysql.password");

            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException var1) {
            throw new RuntimeException(var1);
        }
    }

    public static void start() {
        try {
            create();
            createTable();
        } catch (SQLException var1) {
            Logger var10000 = Bukkit.getLogger();
            String var10001 = Command.warningPrefix;
            var10000.info(var10001 + var1.getMessage());
        }
    }

    private static void create() throws SQLException {
        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        statement.executeUpdate(createDatabaseQuery);
    }

    private static void createTable() throws SQLException {
        statement.execute("use " + DB_NAME);
        String createTable = "create table if not exists warning (player varchar(16),w_dis int,w_add int,reason varchar(100),result int,time TIMESTAMP) ";
        statement.executeUpdate(createTable);
    }
}
