package com.dan.bot.commands.network.permission;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class GetUserPermissionsG extends DiscordSlashCmd {
    public GetUserPermissionsG() {
        super("get-user-permissions-g", "Get network user permissions", NetworkPermissions.NETWORK_PERMISSIONS,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();

        replyMemberOnly("", EmbedTools.info("NETWORK").setDescription(user.getMentionTag() + " has permission level " + Permissions.getPermissionLevel("NETWORK", user)));
    }
}
