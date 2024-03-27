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

public class GetRolePermissionsG extends DiscordSlashCmd {
    public GetRolePermissionsG() {
        super("get-role-permissions-g", "Get network role permissions", NetworkPermissions.NETWORK_PERMISSIONS,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();

        replyMemberOnly("", EmbedTools.info("NETWORK").setDescription(role.getMentionTag() + " has permission level " + Permissions.getPermissionLevel("NETWORK", role)));
    }
}
