package com.dan.bot.commands.server.permission;

import com.dan.bot.dataintegrators.Permissions;
import com.dan.bot.dataintegrators.enums.ServerPermissions;
import com.dan.bot.utils.DiscordSlashCmd;
import com.dan.bot.utils.EmbedTools;
import com.dan.bot.utils.SlashUtil;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;

public class GetRolePermissions extends DiscordSlashCmd {
    public GetRolePermissions() {
        super("get-role-permissions", "Get role permissions", ServerPermissions.ADMINISTRATOR,
                SlashUtil.newArg(SlashCommandOptionType.ROLE, "role", "Role", true)
        );
    }
    @Override
    public void execute(SlashCommandCreateEvent e, SlashCommandInteraction i) {
        Role role = i.getArgumentRoleValueByName("role").get();

        replyMemberOnly("", EmbedTools.info(i.getServer().get().getIdAsString()).setDescription(role.getMentionTag() + " has permission level " + Permissions.getPermissionLevel(i.getServer().get().getIdAsString(), role)));
    }
}
