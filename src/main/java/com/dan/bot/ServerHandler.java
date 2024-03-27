package com.dan.bot;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.json.JSONObject;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerHandler {
    public String inheritFrom;
    public boolean fivem;
    public TextChannel edits;
    public TextChannel deletes;
    public TextChannel nicknames;
    public TextChannel joinsleaves;
    public TextChannel roles;
    public TextChannel punishments;
    public boolean fixScreenshareIssues;
    public String name;
    public String shortname;
    public Color success;
    public Color info;
    public Color error;
    public HashMap<String, Map.Entry<Role, Role>> tickets;
    public ServerHandler(String serverId) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(serverId + ".json")));

            JSONObject jsonObject = new JSONObject(content);
            Server server = Main.api.getServerById(serverId).get();

            this.inheritFrom = jsonObject.getString("inheritFrom");
            if(!inheritFrom.equals("None")) {
                ServerHandler h = new ServerHandler(inheritFrom);
                this.edits = h.edits;
                this.deletes = h.deletes;
                this.nicknames = h.nicknames;
                this.joinsleaves = h.joinsleaves;
                this.roles = h.roles;
                this.punishments = h.punishments;
            } else {
                this.edits = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("edits")).get();
                this.deletes = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("deletes")).get();
                this.nicknames = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("nicknames")).get();
                this.joinsleaves = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("joinsleaves")).get();
                this.roles = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("roles")).get();
                this.punishments = server.getTextChannelById(jsonObject.getJSONObject("logs").getString("punishments")).get();
            }
            this.fivem = jsonObject.getBoolean("fivem");
            this.fixScreenshareIssues = jsonObject.getBoolean("fixScreenshareIssues");
            this.name = jsonObject.getJSONObject("messages").getString("name");
            this.shortname = jsonObject.getJSONObject("messages").getString("shortname");
            this.success = Color.decode(jsonObject.getJSONObject("messages").getString("success"));
            this.info = Color.decode(jsonObject.getJSONObject("messages").getString("info"));
            this.error = Color.decode(jsonObject.getJSONObject("messages").getString("error"));
//            this.tickets = new HashMap<>();
//            for (Iterator<String> it = jsonObject.getJSONObject("tickets").keys(); it.hasNext(); ) {
//                String key = it.next();
//                JSONObject ticket = jsonObject.getJSONObject("tickets").getJSONObject(key);
//
//                this.tickets.put(key, Map.entry(server.getRoleById(ticket.getString("mainRole")).get(), server.getRoleById(ticket.getString("neverRemoveRole")).get()));
//            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
