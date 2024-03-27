package com.dan.bot.dataintegrators;

import com.dan.bot.utils.DB;
import com.dan.bot.utils.Duration;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

public class Temporary {
    public static void createBan(User user, String duration, String server) {
        DB.execute("INSERT INTO temporary (`type`, `user`, `expiry`, `server`, `role`) VALUES (?, ?, ?, ?, ?)", "BAN", user.getIdAsString(), Duration.durationLeft(duration).toEpochMilli(), server, "");
    }
    public static void createRole(User user, String duration, Role role) {
        DB.execute("INSERT INTO temporary (`type`, `user`, `expiry`, `server`, `role`) VALUES (?, ?, ?, ?)", "ROLE", user.getIdAsString(), Duration.durationLeft(duration).toEpochMilli(), role.getServer().getIdAsString(), role.getIdAsString());
    }
    public static void removeTempBan(User user, String server) {
        DB.execute("DELETE FROM temporary WHERE type = 'BAN' AND user = ? AND server = ?", user.getIdAsString(), server);
    }
    public static void removeTempRole(User user, Role role) {
        DB.execute("DELETE FROM temporary WHERE type = 'ROLE' AND user = ? AND role = ? AND server = ?", user.getIdAsString(), role.getIdAsString(), role.getServer().getIdAsString());
    }
}
