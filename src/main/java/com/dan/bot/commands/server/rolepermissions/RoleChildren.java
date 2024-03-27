package com.dan.bot.commands.server.rolepermissions;

import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.sql.ResultSet;

public class RoleChildren extends DiscordSlashCmd {
    public RoleChildren() {
        super("role-children", "Role children List", ServerPermissions.ROLE_FAMILY_MANAGEMENT,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true)
        );
    }

    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();

        ResultSet rs = DB.query("SELECT * FROM role_relations WHERE type = 'ROLE' AND server = ? AND parent = ?", i.getServer().get().getIdAsString(), role.getIdAsString());
        try {
            String roleList = "";

            while (rs.next()) {
                roleList += "\n<@&" + rs.getString("child") + ">";
            }

            replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setTitle("Children:").setDescription(roleList));
        } catch (Exception error) {
            error("An unhandled error has occured... Contact an administrator.");
        }
    }
}
