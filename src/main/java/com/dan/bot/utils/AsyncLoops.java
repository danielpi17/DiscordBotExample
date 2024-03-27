package com.dan.bot.utils;

import com.dan.bot.Main;
import org.javacord.api.entity.server.Server;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class AsyncLoops {
    public static void startLoops(int seconds) {
        new Thread(() -> {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        ResultSet rs = DB.query("SELECT * FROM temporary WHERE expiry <= ?", Instant.now().toEpochMilli());
                        while (rs.next()) {
                            Server server = Main.api.getServerById(rs.getString("server")).get();
                            if(rs.getString("type").equals("BAN")) {
                                server.unbanUser(Main.api.getUserById(rs.getString("user")).get(), "Temporary ban expired.");
                            } else {
                                server.removeRoleFromUser(Main.api.getUserById(rs.getString("user")).get(), server.getRoleById(rs.getString("role")).get(), "Temporary role expired.");
                            }
                            rs.deleteRow();
                        }
                    } catch (Exception ignored) {}
                }
            }, 0, seconds * 1000L);
        }).start();
    }
}
