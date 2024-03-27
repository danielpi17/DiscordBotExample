package com.dan.bot.dataintegrators;

import com.dan.bot.Main;
import com.dan.bot.NetworkHandler;
import com.dan.bot.ServerHandler;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.EmbedTools;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class PunishmentIntegration {
    public enum Type {
        BAN,
        KICK,
        TIMEOUT,
        BAN_REMOVE,
        TIMEOUT_REMOVE,
        NETWORK_BAN,
        NETWORK_KICK,
        NETWORK_BAN_REMOVE,
        FIVEM_BAN,
        FIVEM_KICK,
        FIVEM_WARN,
        FIVEM_COMMS,
        FIVEM_UNBAN
    }
    public static String issuePunishment(String user, String executor, Type type, String reason, String duration, String server, boolean execute) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        String time = "" + Instant.now().toEpochMilli();
        DB.execute("INSERT INTO punishments (`timestamp`, `id`, `user`, `executor`, `type`, `reason`, `duration`, `evidence`, `server`, `iswaiting`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                time, id, user, executor, type.toString(), reason, duration, "", server, "0"
        );

        boolean network = type.equals(Type.NETWORK_BAN) || type.equals(Type.NETWORK_KICK) || type.equals(Type.NETWORK_BAN_REMOVE);
        boolean fivem = type.equals(Type.FIVEM_BAN) || type.equals(Type.FIVEM_KICK) || type.equals(Type.FIVEM_WARN) || type.equals(Type.FIVEM_COMMS) || type.equals(Type.FIVEM_UNBAN);

        // generate an embed
        EmbedBuilder embed = EmbedTools.info(server)
                .setTitle("Punishment Logs")
                .addInlineField("Timestamp", "<t:" + time + ":F>")
                .addInlineField("ID", id)
                .addInlineField("User", "<@" + user + ">")
                .addInlineField("Executor", "<@" + executor + ">")
                .addInlineField("Type", type.name())
                .addInlineField("Duration", duration)
                .addField("Reason", "```" + reason + "```");
        // generate an embed
        EmbedBuilder embed2 = EmbedTools.info(server)
                .setTitle("Punishment Logs")
                .addInlineField("Timestamp", "<t:" + time + ":F>")
                .addInlineField("ID", id)
                .addInlineField("User", "<@" + user + ">")
                .addInlineField("Type", type.name())
                .addInlineField("Duration", duration)
                .addField("Reason", "```" + reason + "```");

        if(!network && !fivem) {
            new ServerHandler(server).punishments.sendMessage(embed);
        } else if (fivem) {
            NetworkHandler.fivemPunishments.sendMessage(embed);
        } else {
            NetworkHandler.networkPunishments.sendMessage(embed);
        }

        Server serverObject = Main.api.getServerById(server).isPresent() ? Main.api.getServerById(server).get() : (fivem ? NetworkHandler.fivemServer : NetworkHandler.mainServer);
        User userObject = null;
        try { userObject = Main.api.getUserById(user).get(); } catch (Exception ignored) {}
        userObject.sendMessage(embed2);

        if(execute) {
            switch (type) {
                case FIVEM_BAN:
                case FIVEM_KICK:
                case FIVEM_WARN:
                case FIVEM_COMMS:
                case FIVEM_UNBAN:
                    break;
                case BAN:
                    Temporary.removeTempBan(userObject, server);
                    if(duration.equals("Permanent")) {
                        serverObject.banUser(userObject, Duration.ZERO, id);
                    } else {
                        Temporary.createBan(userObject, duration, server);
                        serverObject.banUser(userObject, Duration.ZERO, id);
                    }
                    break;
                case KICK:
                    serverObject.kickUser(userObject, id);
                    break;
                case TIMEOUT:
                    serverObject.timeoutUser(userObject, com.dan.bot.utils.Duration.durationLeft(duration), id);
                    break;
                case BAN_REMOVE:
                    Temporary.removeTempBan(userObject, server);
                    serverObject.unbanUser(userObject, id);
                    break;
                case TIMEOUT_REMOVE:
                    serverObject.removeUserTimeout(userObject, id);
                    break;
                case NETWORK_BAN:
                    for (Server s : Main.api.getServers()) {
                        Temporary.removeTempBan(userObject, s.getIdAsString());
                        s.banUser(userObject, Duration.ZERO, id);
                    }
                    break;
                case NETWORK_KICK:
                    for (Server s : Main.api.getServers()) {
                        s.kickUser(userObject, id);
                    }
                    break;
                case NETWORK_BAN_REMOVE:
                    for (Server s : Main.api.getServers()) {
                        Temporary.removeTempBan(userObject, s.getIdAsString());
                        s.unbanUser(userObject, id);
                    }
                    break;
            }
        }

        return id;
    }
}
