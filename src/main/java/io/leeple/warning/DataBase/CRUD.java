package io.leeple.warning.DataBase;

import io.leeple.warning.Command.Command;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import org.bukkit.entity.Player;

import static io.leeple.warning.DataBase.DbSetting.DB_NAME;
import static io.leeple.warning.DataBase.DbSetting.statement;

public class  CRUD {

    public static void insertWarning(String column, String player, int count, String reason, int result) throws SQLException {
        statement.execute("use " + DB_NAME);
        String query = "INSERT INTO warning (player, " + column + ", reason, result, time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = DbSetting.connection.prepareStatement(query);
        pstmt.setString(1, player);
        pstmt.setInt(2, count);
        pstmt.setString(3, reason);
        pstmt.setInt(4, result);
        pstmt.setObject(5, LocalDateTime.now());
        pstmt.executeUpdate();
    }

    public static int getWarningCount(Player player) {
        int warningCount = 0;

        try {
            statement.execute("use " + DB_NAME);
            Statement statement = DbSetting.connection.createStatement();
            String query = "SELECT result FROM warning WHERE player='" + player.getName() + "' ORDER BY time DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                warningCount = resultSet.getInt("result");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var6) {
            String var10001 = Command.warningPrefix;
            player.sendMessage(var10001 + Command.sqlErrorPrefix + var6.getMessage() + " FROM CRUD Class");
        }

        return warningCount;
    }
}
