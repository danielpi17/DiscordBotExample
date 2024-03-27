package com.dan.bot.commands.server.rolepermissions;

import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DB;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.sql.ResultSet;

public class UserChildren extends DiscordSlashCmd {
    public UserChildren() {
        super("user-children", "User children List", ServerPermissions.ROLE_FAMILY_MANAGEMENT,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "user", "User", true)
        );
    }

    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("role").get();

        ResultSet rs = DB.query("SELECT * FROM role_relations WHERE type = 'USER' AND server = ? AND parent = ?", i.getServer().get().getIdAsString(), user.getIdAsString());
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
