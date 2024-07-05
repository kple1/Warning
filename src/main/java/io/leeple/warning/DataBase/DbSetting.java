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

    public DbSetting() {
    }

    public static void start() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (!databaseExists()) {
                createDatabase();
                createTable();
                Bukkit.getLogger().info(Command.warningPrefix + "DataBase Setting Success");
            } else {
                Bukkit.getLogger().info(Command.warningPrefix + "DataBase가 이미 생성되어 있습니다.");
            }
        } catch (ClassNotFoundException | SQLException var1) {
            Logger var10000 = Bukkit.getLogger();
            String var10001 = Command.warningPrefix;
            var10000.info(var10001 + var1.getMessage());
        }

    }

    private static void createDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        String createDatabaseQuery = "CREATE DATABASE " + DB_NAME;
        statement.executeUpdate(createDatabaseQuery);
        statement.close();
    }

    private static void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        String createTable = "create table warning (player varchar(16),w_dis int,w_add int,reason varchar(100),result int,time TIMESTAMP)";
        statement.executeUpdate(createTable);
    }

    public static boolean databaseExists() throws SQLException {
        Statement statement = connection.createStatement();
        String checkDatabaseQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + DB_NAME + "'";
        return statement.executeQuery(checkDatabaseQuery).next();
    }

    static {
        String var10000 = Config.getString("mysql.address");
        DB_NAME = Config.getString("mysql.dbName");
        JDBC_URL = "jdbc:mysql://" + var10000 + ":" + Config.getString("mysql.port") + "/" + DB_NAME;
        USER = Config.getString("mysql.user");
        PASSWORD = Config.getString("mysql.password");

        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException var1) {
            throw new RuntimeException(var1);
        }
    }
}
