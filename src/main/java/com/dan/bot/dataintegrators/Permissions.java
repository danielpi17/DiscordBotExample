package com.dan.bot.dataintegrators;

import com.dan.bot.Main;
import com.dan.bot.NetworkHandler;
import com.dan.bot.ServerHandler;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.sql.ResultSet;

public class Permissions {
    public static void setPermissionLevel(String server, User userrole, long level) {
        if(!server.equals("NETWORK")) if(!new ServerHandler(server).inheritFrom.equals("None")) { server = new ServerHandler(server).inheritFrom; }
        DB.execute("INSERT INTO `permissions` (`details`, `level`) VALUES (?, ?) ON DUPLICATE KEY UPDATE level = ?", server + ":" + "USER" + ":" + userrole.getIdAsString(), level, level);
    }
    public static void setPermissionLevel(String server, Role userrole, long level) {
        if(!server.equals("NETWORK")) if(!new ServerHandler(server).inheritFrom.equals("None")) { server = new ServerHandler(server).inheritFrom; }
        DB.execute("INSERT INTO `permissions` (`details`, `level`) VALUES (?, ?) ON DUPLICATE KEY UPDATE level = ?", server + ":" + "ROLE" + ":" + userrole.getIdAsString(), level, level);
    }
    public static long getPermissionLevel(String server, User userrole) {
        if(!server.equals("NETWORK")) if(!new ServerHandler(server).inheritFrom.equals("None")) { server = new ServerHandler(server).inheritFrom; }
        ResultSet rs = DB.query("SELECT * FROM permissions WHERE details = ?", server + ":" + "USER" + ":" + userrole.getIdAsString());
        try { rs.next(); return rs.getLong("level"); } catch (Exception er) { return 0L; }
    }
    public static long getPermissionLevel(String server, Role userrole) {
        if(!server.equals("NETWORK")) if(!new ServerHandler(server).inheritFrom.equals("None")) { server = new ServerHandler(server).inheritFrom; }
        ResultSet rs = DB.query("SELECT * FROM permissions WHERE details = ?", server + ":" + "ROLE" + ":" + userrole.getIdAsString());
        try { rs.next(); return rs.getLong("level"); } catch (Exception er) { return 0L; }
    }
    public static boolean isAllowed(String server, User userrole, NetworkPermissions permissions) {
        if(!server.equals("NETWORK")) server = new ServerHandler(server).inheritFrom.equals("None") ? server : new ServerHandler(server).inheritFrom;
        if(permissions.i == 0) return true;
        return (getPermissionLevel(server, userrole) & permissions.i) != 0;
    }
    public static boolean isAllowed(String server, Role userrole, NetworkPermissions permissions) {
        if(!server.equals("NETWORK")) server = new ServerHandler(server).inheritFrom.equals("None") ? server : new ServerHandler(server).inheritFrom;
        if(permissions.i == 0) return true;
        return (getPermissionLevel(server, userrole) & permissions.i) != 0;
    }
    public static boolean isAllowed(String server, User userrole, ServerPermissions permissions) {
        if(!server.equals("NETWORK")) server = new ServerHandler(server).inheritFrom.equals("None") ? server : new ServerHandler(server).inheritFrom;
        if(permissions.i == 0) return true;
        return (getPermissionLevel(server, userrole) & permissions.i) != 0;
    }
    public static boolean isAllowed(String server, Role userrole, ServerPermissions permissions) {
        if(!server.equals("NETWORK")) server = new ServerHandler(server).inheritFrom.equals("None") ? server : new ServerHandler(server).inheritFrom;
        if(permissions.i == 0) return true;
        return (getPermissionLevel(server, userrole) & permissions.i) != 0;
    }
    public static boolean isUserAllowed(User user, NetworkPermissions permissions) {
        for(Role role : user.getRoles(NetworkHandler.mainServer)) if(isAllowed("NETWORK", role, permissions)) return true;
        if(isAllowed("NETWORK", user, permissions)) return true;
        return false;
    }
    public static boolean isUserAllowed(String server, User user, ServerPermissions permissions) {
        server = new ServerHandler(server).inheritFrom.equals("None") ? server : new ServerHandler(server).inheritFrom;
        for(Role role : user.getRoles(Main.api.getServerById(server).get())) if(isAllowed(server, role, permissions)) return true;
        return isAllowed(server, user, permissions);
    }
}
