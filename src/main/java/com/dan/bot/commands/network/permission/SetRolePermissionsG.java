package com.dan.bot.commands.network.permission;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.NetworkPermissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SetRolePermissionsG extends DiscordSlashCmd {
    public SetRolePermissionsG() {
        super("set-role-permissions-g", "Set network role permissions", NetworkPermissions.NETWORK_PERMISSIONS,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true),
                SlashUtil.newArg(SlashCommandOptionType.LONG, "level", "Level", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();
        Long level = i.getArgumentLongValueByName("level").get();

        Permissions.setPermissionLevel("NETWORK", role, level);

        replyMemberOnly("", EmbedTools.success("NETWORK").setDescription("Successfully set " + role.getMentionTag() + " to permission " + level));
    }
}
