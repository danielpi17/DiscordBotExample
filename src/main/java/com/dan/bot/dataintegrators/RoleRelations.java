package com.dan.bot.dataintegrators;

import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import org.javacord.api.entity.Permissionable;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRelations {
    public static void setRoleRelation(User parent, Role child, boolean boo) {
        if(boo) {
            if(!isParent(parent, child)) DB.execute("INSERT INTO `role_relations` (`server`, `type`, `parent`, `child`) VALUES (?, 'USER', ?, ?)", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
        } else {
            if(isParent(parent, child)) DB.execute("DELETE FROM `role_relations` WHERE server = ? AND type = 'USER' AND parent = ? AND child = ?", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
        }
    }
    public static void setRoleRelation(Role parent, Role child, boolean boo) {
        if(boo) {
            if(!isParent(parent, child)) DB.execute("INSERT INTO `role_relations` (`server`, `type`, `parent`, `child`) VALUES (?, 'ROLE', ?, ?)", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
        } else {
            if(isParent(parent, child)) DB.execute("DELETE FROM `role_relations` WHERE server = ? AND type = 'ROLE' AND parent = ? AND child = ?", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
        }
    }
    public static boolean isParent(User parent, Role child) {
        try {
            ResultSet rs = DB.query("SELECT * FROM `role_relations` WHERE server = ? AND type = 'USER' AND parent = ? AND child = ?", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
            return rs.next();
        } catch (Exception error) {
            return false;
        }
    }
    public static boolean isParent(Role parent, Role child) {
        try {
            ResultSet rs = DB.query("SELECT * FROM `role_relations` WHERE server = ? AND type = 'ROLE' AND parent = ? AND child = ?", child.getServer().getIdAsString(), parent.getIdAsString(), child.getIdAsString());
            return rs.next();
        } catch (Exception error) {
            return false;
        }
    }
    public static boolean isUserAllowedToUse(User user, Role child) {
        if (Permissions.isUserAllowed(user, NetworkPermissions.ADMINISTRATOR) || Permissions.isUserAllowed(child.getServer().getIdAsString(), user, ServerPermissions.ADMINISTRATOR)) {
            return true;
        }
        if (isParent(user, child)) {
            return true;
        }
        for (Role role : user.getRoles(child.getServer())) {
            if (isParent(role, child)) {
                return true;
            }
        }
        return false;
    }
}
