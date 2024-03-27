package com.dan.bot.dataintegrators;

import com.dan.bot.utils.DB;

import java.sql.ResultSet;

public class Responses {
    public static void registerResponse(String server, String command, String json) {
        DB.execute("INSERT INTO `responses` (`details`, `json`) VALUES (?, ?) ON DUPLICATE KEY UPDATE json = ?", server + ":" + command, json, json);
    }
    public static void deleteResponse(String server, String command) {
        DB.execute("DELETE FROM `responses` WHERE details = ?", server + ":" + command);
    }
    public static String getResponse(String server, String command) {
        ResultSet rs = DB.query("SELECT * FROM `responses` WHERE details = ?", server + ":" + command);
        try { rs.next(); return rs.getString("json"); } catch (Exception error) { return ""; }
    }
}
