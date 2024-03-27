package com.dan.bot;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class NetworkHandler {
    public static String token;
    public static Server mainServer;
    public static Server fivemServer;
    public static String databaseHost;
    public static String databaseUsername;
    public static String databasePassword;
    public static TextChannel networkPunishments;
    public static TextChannel fivemPunishments;
    public static TextChannel trustSystem;
    public static int tempRefresh;
    public static void registerNetwork() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("network.json")));

            JSONObject jsonObject = new JSONObject(content);
            System.out.println(content);

            token = jsonObject.getString("token");
            mainServer = Main.api.getServerById(jsonObject.getString("mainServer")).get();
            fivemServer = Main.api.getServerById(jsonObject.getString("fivemServer")).get();
            databaseHost = jsonObject.getJSONObject("database").getString("host");
            databaseUsername = jsonObject.getJSONObject("database").getString("username");
            databasePassword = jsonObject.getJSONObject("database").getString("password");
            networkPunishments = mainServer.getTextChannelById(jsonObject.getJSONObject("networkLogs").getString("networkPunishments")).get();
            fivemPunishments = mainServer.getTextChannelById(jsonObject.getJSONObject("networkLogs").getString("fivemPunishments")).get();
            trustSystem = mainServer.getTextChannelById(jsonObject.getJSONObject("networkLogs").getString("trustSystem")).get();
            tempRefresh = jsonObject.getInt("tempRefreshSeconds");
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
    public static String getToken() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("network.json")));

            JSONObject jsonObject = new JSONObject(content);

            return jsonObject.getString("token");
        } catch (Exception error) {
            error.printStackTrace();
        }
        return "";
    }
}
