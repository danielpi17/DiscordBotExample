package com.dan.bot.commands.server.permission;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class GetUserPermissions extends DiscordSlashCmd {
    public GetUserPermissions() {
        super("get-user-permissions", "Get user permissions", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();

        replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setDescription(user.getMentionTag() + " has permission level " + Permissions.getPermissionLevel(i.getServer().get().getIdAsString(), user)));
    }
}
