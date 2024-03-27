package com.dan.bot.utils;

import com.dan.bot.NetworkHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    public static Connection connection = null;
    public static void createTables() {
        String c = "CREATE TABLE IF NOT EXISTS ";
        execute(c + "`command_logs` (`timestamp` VARCHAR(25), `user` VARCHAR(20), `command` TEXT)");
        execute(c + "`punishments` (`timestamp` VARCHAR(25), `id` VARCHAR(16), `user` VARCHAR(20), `executor` VARCHAR(20), `type` VARCHAR(30), `reason` TEXT, `duration` VARCHAR(25), `evidence` TEXT, `server` VARCHAR(20), `iswaiting` VARCHAR(1), PRIMARY KEY (id))");
        execute(c + "`role_relations` (`server` VARCHAR(20), `type` VARCHAR(5), `parent` VARCHAR(20), `child` VARCHAR(20))");
        execute(c + "`permissions` (`details` VARCHAR(300), `level` VARCHAR(100), PRIMARY KEY (`details`))");
        execute(c + "`responses` (`details` VARCHAR(300), `json` TEXT, PRIMARY KEY (`details`))");
        execute(c + "`trust` (`spawncode` VARCHAR(255), `type` VARCHAR(6), `user` VARCHAR(20))");
        execute(c + "`temporary` (`type` VARCHAR(5), `user` VARCHAR(20), `expiry` VARCHAR(30), `server` VARCHAR(30), `role` VARCHAR(20))");
        execute(c + "`easylink` (`type` VARCHAR(10), `id` VARCHAR(50))");
    }
    public static void updateConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(NetworkHandler.databaseHost, NetworkHandler.databaseUsername, NetworkHandler.databasePassword);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
    public static Connection connection() {
        return connection;
    }
    public static boolean execute(String string, Object... objs) {
        try {
            Class.forName("org.sqlite.JDBC");
            PreparedStatement ps = connection.prepareStatement(string, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int index = 1;
            for (Object o : objs) {
                if (o instanceof Integer) {
                    ps.setInt(index, (Integer) o);
                } else if (o instanceof String) {
                    ps.setString(index, (String) o);
                } else if (o instanceof Float) {
                    ps.setFloat(index, (Float) o);
                } else if (o instanceof Long) {
                    ps.setLong(index, (Long) o);
                } else {
                    System.out.println(o.getClass());
                    continue;
                }
                index++;
            }
            return ps.execute();
        } catch (Exception error) {
            error.printStackTrace();
        }
        return false;
    }
    public static ResultSet query(String string, Object... objs) {
        try {
            Class.forName("org.sqlite.JDBC");
            PreparedStatement ps = connection.prepareStatement(string, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int index = 1;
            for(Object o : objs) {
                if (o instanceof Integer) {
                    ps.setInt(index, (Integer) o);
                } else if (o instanceof String) {
                    ps.setString(index, (String) o);
                } else if (o instanceof Float) {
                    ps.setFloat(index, (Float) o);
                } else if (o instanceof Long) {
                    ps.setLong(index, (Long) o);
                } else {
                    System.out.println(o.getClass());
                    continue;
                }
                index++;
            }
            return ps.executeQuery();
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }
}
