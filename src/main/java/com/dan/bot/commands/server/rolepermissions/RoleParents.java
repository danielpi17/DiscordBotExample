package com.dan.bot.commands.server.rolepermissions;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.sql.ResultSet;

public class RoleParents extends DiscordSlashCmd {
    public RoleParents() {
        super("role-parents", "Role parents List", ServerPermissions.ROLE_FAMILY_MANAGEMENT,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true)
        );
    }

    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();

        ResultSet rs = DB.query("SELECT * FROM role_relations WHERE server = ? AND child = ?", i.getServer().get().getIdAsString(), role.getIdAsString());
        try {
            String roleList = "";

            while (rs.next()) {
                if(rs.getString("type").equals("USER")) {
                    roleList += "\n<@" + rs.getString("parent") + ">";
                } else {
                    roleList += "\n<@&" + rs.getString("parent") + ">";
                }
            }

            replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setTitle("Parents:").setDescription(roleList));
        } catch (Exception error) {
            error("An unhandled error has occured... Contact an administrator.");
        }
    }
}
