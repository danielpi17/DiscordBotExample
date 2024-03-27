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

public class SetUserPermissionsG extends DiscordSlashCmd {
    public SetUserPermissionsG() {
        super("set-user-permissions-g", "Set network user permissions", NetworkPermissions.NETWORK_PERMISSIONS,
                SlashUtil.newArg(SlashCommandOptionType.USER, "user", "User", true),
                SlashUtil.newArg(SlashCommandOptionType.LONG, "level", "Level", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        User user = i.getArgumentUserValueByName("user").get();
        Long level = i.getArgumentLongValueByName("level").get();

        Permissions.setPermissionLevel("NETWORK", user, level);

        replyMemberOnly("", EmbedTools.success("NETWORK").setDescription("Successfully set " + user.getMentionTag() + " to permission " + level));
    }
}
