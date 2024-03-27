package com.dan.bot.utils;

import com.dan.bot.NetworkHandler;
import com.dan.bot.ServerHandler;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class EmbedTools {
    public static EmbedBuilder success(String server) {
        String serverName = "";
        if(server.equals("NETWORK") || server.equals("FIVEM")) {
            server = server.equals("NETWORK") ? NetworkHandler.mainServer.getIdAsString() : NetworkHandler.fivemServer.getIdAsString();
            serverName = server.equals("FIVEM") ? "FiveM" : "Network";
        } else {
            serverName = new ServerHandler(server).name;
        }

        return new EmbedBuilder().setTitle("Success").setFooter(serverName).setTimestampToNow().setColor(new ServerHandler(server).success);
    }
    public static EmbedBuilder error(String server) {
        String serverName = "";
        if(server.equals("NETWORK") || server.equals("FIVEM")) {
            server = server.equals("NETWORK") ? NetworkHandler.mainServer.getIdAsString() : NetworkHandler.fivemServer.getIdAsString();
            serverName = server.equals("FIVEM") ? "FiveM" : "Network";
        } else {
            serverName = new ServerHandler(server).name;
        }

        return new EmbedBuilder().setTitle("Error").setFooter(serverName).setTimestampToNow().setColor(new ServerHandler(server).error);
    }
    public static EmbedBuilder info(String server) {
        String serverName = "";
        if(server.equals("NETWORK") || server.equals("FIVEM")) {
            server = server.equals("NETWORK") ? NetworkHandler.mainServer.getIdAsString() : NetworkHandler.fivemServer.getIdAsString();
            serverName = server.equals("FIVEM") ? "FiveM" : "Network";
        } else {
            serverName = new ServerHandler(server).name;
        }

        return new EmbedBuilder().setTitle("Info").setFooter(serverName).setTimestampToNow().setColor(new ServerHandler(server).info);
    }
}
